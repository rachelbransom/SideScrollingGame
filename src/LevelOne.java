import java.util.ArrayList;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.ParallelTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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

public class LevelOne {
	private Group levelOneRoot;
	private Scene myLevel1Scene;
	private ImageView levelOneBackground1;
	private ImageView levelOneBackground2;
	private PlayersFish player;
	private int stepCounter;
	private ArrayList<Enemy> enemiesList = new ArrayList<Enemy>();
	private ArrayList<Coin> coinList = new ArrayList<Coin>();
	private ParallelTransition parallelTransition;
	private boolean gameOver = false, passedLevelOne = false;
	public static final int SIZE = 650;
	public static final int FRAMES_PER_SECOND = 60;
	private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
	private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
	private Button playAgain;
	private Button exitOut;
	private Integer numCoinsCollected = 0;
	private Text score;
	private boolean disableEnemies=false;
	private ArrayList<Coin> coinsCollidedToDelete = new ArrayList<Coin>();
	private int GOAL_NUMBER_COINS = 10;
	private Button continueToLevelTwo;

	public void levelOneInit(Group root) {
		levelOneRoot = root;
		// myLevel1Scene = new Scene(levelOneRoot, width, height,
		// Color.TRANSPARENT);
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
		// return myLevel1Scene;
	}

	public void setUpScoreBoard() {
		score = new Text("SCORE: " + numCoinsCollected);
		score.setLayoutX(SIZE - 150);
		score.setLayoutY(50);
		// score.textProperty().bind(Bindings.createStringBinding(() -> ("SCORE:
		// " + numCoinsCollected.get(), numCoinsCollected));
		// Text score = new Text(SIZE-50,40,"SCORE: "+ numCoinsCollected);
		// score.setText("SCORE: " + numCoinsCollected);
		score.setFont(Font.font("Impact", FontWeight.BOLD, 40));
		score.setFill(Color.WHITE);
		levelOneRoot.getChildren().add(score);
	}

	public void step(double elapsedTime, int width, int height) {
		if (!gameOver && !passedLevelOne) {
			stepCounter++;
			if (stepCounter % 50 == 0 && disableEnemies == false) {
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
					setScreenToGameOver();
				}
				e.update(elapsedTime);
			}
			for (Coin c : coinList) {
				if (c.collision((Shape) player.getPlayer())) {
					coinsCollidedToDelete.add(c);
					levelOneRoot.getChildren().remove(c.getCircle());
					
					numCoinsCollected++;
					score.setText("SCORE: " + numCoinsCollected);
					if (numCoinsCollected == GOAL_NUMBER_COINS){
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

	public void setScreenToGameOver() {
		gameOver = true;
		levelOneRoot.getChildren().remove(player.getPlayer());
		displayGameOverText();
		// Main.getMyGame().changeScreen(3);
		displayGameOverButtons();

		playAgain.setOnAction((event) -> {
			Main.getMyGame().changeScreen(1);
		});

		exitOut.setOnAction((event) -> {
			System.exit(0);
		});

	}
	
	public void setScreenToLevelTwo() {
		levelOneRoot.getChildren().remove(player.getPlayer());
		displayLevelTwoText();
		displayLevelTwoButtons();

		continueToLevelTwo.setOnAction((event) -> {
			Main.getMyGame().changeScreen(2);
		});

		exitOut.setOnAction((event) -> {
			System.exit(0);
		});

	}

	public void displayGameOverText() {
		Text gameOverText = new Text(SIZE / 5, SIZE / 3, "GAME\n OVER");
		gameOverText.setFont(Font.font("Impact", FontWeight.BOLD, 160));
		gameOverText.setFill(Color.WHITE);
		gameOverText.setTextAlignment(TextAlignment.CENTER);
		levelOneRoot.getChildren().add(gameOverText);
	}

	public void displayGameOverButtons() {
		playAgain = new Button("Play Again");
		playAgain.setLayoutX(SIZE / 4);
		playAgain.setLayoutY(SIZE * 3 / 4);
		playAgain.setPrefSize(SIZE / 2, 40);
		levelOneRoot.getChildren().add(playAgain);

		exitOutButtonInit();
	}
	
	public void displayLevelTwoText() {
		Text levelTwoText = new Text(SIZE / 2, SIZE / 3, "CONGRATULATIONS! \n YOU PASSED \n LEVEL ONE");
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
		exitOutButtonInit();
	}
	
	public void exitOutButtonInit(){
		exitOut = new Button("Exit Out");
		exitOut.setLayoutX(SIZE / 4);
		exitOut.setLayoutY(SIZE * 9 / 10);
		exitOut.setPrefSize(SIZE / 2, 40);
		levelOneRoot.getChildren().add(exitOut);
	}

	public void gameAnimationStarter() {
		KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> this.step(SECOND_DELAY, SIZE, SIZE));
		Timeline animation = new Timeline();
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.getKeyFrames().add(frame);
		animation.play();
	}

	public void makeBackgroundScroll(final int width) {
		// System.out.println(levelOneBackground.getBoundsInLocal());
		TranslateTransition translateTransition = new TranslateTransition(Duration.millis(10000), levelOneBackground1);
		translateTransition.setFromX(0);
		translateTransition.setToX(-1 * levelOneBackground1.getLayoutBounds().getWidth());
		translateTransition.setInterpolator(Interpolator.LINEAR);
		// translateTransition.setCycleCount(Animation.INDEFINITE);

		// System.out.println(levelOneBackground1.getLayoutX());
		TranslateTransition translateTransition2 = new TranslateTransition(Duration.millis(10000), levelOneBackground2);
		translateTransition2.setFromX(0);
		translateTransition2.setToX(-1089);
		translateTransition2.setInterpolator(Interpolator.LINEAR);

		// translateTransition.play();
		// translateTransition2.play();
		parallelTransition = new ParallelTransition(translateTransition2, translateTransition);
		parallelTransition.setCycleCount(Animation.INDEFINITE);

		// parallelTransition.setRate(BACKGROUND_SPEED);
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
		
		case E:
			disableEnemies = true;
			for (Enemy e:enemiesList){
				levelOneRoot.getChildren().remove(e.getRect());
			}
			break;
		
		case T:
			numCoinsCollected = GOAL_NUMBER_COINS;
			passedLevelOne = true;
			setScreenToLevelTwo();
			break;

		default:
			// do nothing
		}
	}
}
