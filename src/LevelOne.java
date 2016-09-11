import java.util.ArrayList;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.ParallelTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

public class LevelOne extends Level {
	private Group levelOneRoot;
	private ImageView levelOneBackground1;
	private ImageView levelOneBackground2;
	private PlayersFish player;
	private int stepCounter, GOAL_NUMBER_COINS = 10, numCoinsCollected;
	private ArrayList<Enemy> enemiesList = new ArrayList<Enemy>();
	private ArrayList<Coin> coinList = new ArrayList<Coin>();
	private ParallelTransition parallelTransition;
	private boolean passedLevelOne;
	public static final int SIZE = 650;
	public static final int FRAMES_PER_SECOND = 60;
	private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
	private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
	private Button playAgain, exitOut, continueToLevelTwo;
	private Text score;
	private ArrayList<Coin> coinsCollidedToDelete = new ArrayList<Coin>();

	public void levelOneInit(Group root) {
		levelOneRoot = root;
		Image image = new Image(getClass().getClassLoader().getResourceAsStream("SeaFloorBackground.png"));
		Image image2 = new Image(getClass().getClassLoader().getResourceAsStream("SeaFloorBackground.png"));
		levelOneBackground1 = new ImageView(image);
		levelOneBackground2 = new ImageView(image2);

		levelOneRoot.getChildren().addAll(levelOneBackground1, levelOneBackground2);
		levelOneBackground2.setX(1089);
		makeBackgroundScroll(SIZE);
		player = new PlayersFish();
		levelOneRoot.getChildren().add(player.getPlayer());
		setUpScoreBoard();
		gameAnimationStarter();
		root.getScene().setOnKeyPressed(e -> handleKeyInput(e.getCode()));
	}

	public void setUpScoreBoard() {
		score = new Text("SCORE: " + numCoinsCollected);
		score.setLayoutX(SIZE - 150);
		score.setLayoutY(50);
		score.setFont(Font.font("Impact", FontWeight.BOLD, 40));
		score.setFill(Color.WHITE);
		levelOneRoot.getChildren().add(score);
	}

	public void step(double elapsedTime, int width, int height) {
		if (!gameOver && !passedLevelOne) {
			stepCounter++;
			if (stepCounter % 50 == 0 && !disableEnemies) {
				Enemy enemy = new Enemy();
				enemiesList.add(enemy);
				levelOneRoot.getChildren().add(enemy.getRect());
			}
			if (stepCounter % 80 == 0) {
				Coin coin = new Coin();
				coinList.add(coin);
				levelOneRoot.getChildren().add(coin.getCircle());
			}

			for (Enemy e : enemiesList) {
				if (e.collision((Shape) player.getPlayer())) {

					setScreenToGameOver(levelOneRoot, player);
				}
				e.update(elapsedTime);
			}
			for (Coin c : coinList) {
				if (c.collision((Shape) player.getPlayer())) {
					coinsCollidedToDelete.add(c);
					levelOneRoot.getChildren().remove(c.getCircle());
					numCoinsCollected++;
					score.setText("SCORE: " + numCoinsCollected);
					if (numCoinsCollected == GOAL_NUMBER_COINS) {
						passedLevelOne = true;
						setScreenToLevelTwo();
					}
				}
				c.update(elapsedTime);

			}
			coinList.removeAll(coinsCollidedToDelete);

		}
		player.update(elapsedTime);
	}

	public void setScreenToLevelTwo() {
		levelOneRoot.getChildren().remove(player.getPlayer());
		displayLevelTwoText();
		displayLevelTwoButtons();

		continueToLevelTwo.setOnAction((event) -> {
			Main.getMyGame().changeScreen(2);
		});

	}

	public void displayLevelTwoText() {
		Text levelTwoText = new Text(SIZE / 8, SIZE / 3, "CONGRATULATIONS! \n YOU PASSED \n LEVEL ONE");
		levelTwoText.setFont(Font.font("Impact", FontWeight.BOLD, 60));
		levelTwoText.setFill(Color.WHITE);
		levelTwoText.setTextAlignment(TextAlignment.CENTER);
		levelOneRoot.getChildren().add(levelTwoText);
	}

	public void displayLevelTwoButtons() {
		continueToLevelTwo = new Button("Proceed to Level 2");
		continueToLevelTwo.setLayoutX(SIZE / 4);
		continueToLevelTwo.setLayoutY(SIZE * 3 / 4);
		continueToLevelTwo.setPrefSize(SIZE / 2, 40);
		levelOneRoot.getChildren().add(continueToLevelTwo);

	}

	public void makeBackgroundScroll(final int width) {
		TranslateTransition translateTransition = new TranslateTransition(Duration.millis(10000), levelOneBackground1);
		translateTransition.setFromX(0);
		translateTransition.setToX(-1 * levelOneBackground1.getLayoutBounds().getWidth());
		translateTransition.setInterpolator(Interpolator.LINEAR);
		
		TranslateTransition translateTransition2 = new TranslateTransition(Duration.millis(10000), levelOneBackground2);
		translateTransition2.setFromX(0);
		translateTransition2.setToX(-1089);
		translateTransition2.setInterpolator(Interpolator.LINEAR);

		parallelTransition = new ParallelTransition(translateTransition2, translateTransition);
		parallelTransition.setCycleCount(Animation.INDEFINITE);
		parallelTransition.play();

	}

	private void handleKeyInput(KeyCode code) {
		switch (code) {

		case UP:
			if (player.getPlayerYPos() > -300) {
				player.setYv(player.getYv() - 3);
			}
			break;

		case DOWN:
			if (player.getPlayerYPos() < 300) {
				player.setYv(player.getYv() + 3);
			}
			break;

		case LEFT:
			if (player.getPlayerXPos() > -300) {
				player.setXv(player.getXv() - 3);
			}
			break;

		case RIGHT:
			if (player.getPlayerXPos() < 300) {
				player.setXv(player.getXv() + 3);
			}
			break;

		case E:
			disableEnemies = true;
			for (Enemy e : enemiesList) {
				levelOneRoot.getChildren().remove(e.getRect());
			}
			break;

		case T:
			numCoinsCollected = GOAL_NUMBER_COINS;
			passedLevelOne = true;
			setScreenToLevelTwo();
			break;

		default: //nothing
		}
	}
	
	public void gameAnimationStarter() {
		KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> this.step(SECOND_DELAY, SIZE, SIZE));
		Timeline animation = new Timeline();
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.getKeyFrames().add(frame);
		animation.play();
	}
}

