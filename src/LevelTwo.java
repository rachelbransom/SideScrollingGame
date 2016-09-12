import java.util.ArrayList;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.ParallelTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.Group;
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

public class LevelTwo extends Level {
	private Group levelTwoRoot;
	private PlayersFish player;
	private ImageView levelTwoBackground1, levelTwoBackground2;
	private ParallelTransition parallelTransition;
	public static final int FRAMES_PER_SECOND = 60;
	private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
	private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
	private boolean gameOver, disableEnemies, winningStarInView;
	private boolean passedLevelTwo;
	private int stepCounter;
	private EndOfLevelTwoPoint winningStar;
	private ArrayList<Enemy> enemiesList = new ArrayList<Enemy>();
	private ArrayList<Enemy> enemiesOffScreen = new ArrayList<Enemy>();

	//public so I can call from game, which manages the levels
	public void levelTwoInit(Group root) {
		levelTwoRoot = root;
		Image image = new Image(getClass().getClassLoader().getResourceAsStream("SeaFloorLevelTwo.png"));
		Image image2 = new Image(getClass().getClassLoader().getResourceAsStream("SeaFloorLevelTwo.png"));
		levelTwoBackground1 = new ImageView(image);
		levelTwoBackground2 = new ImageView(image2);
		levelTwoBackground2.setX(1089);
		levelTwoRoot.getChildren().addAll(levelTwoBackground1, levelTwoBackground2);
		
		player = new PlayersFish();
		player.setLevelTwoStartingPosition();
		makeBackgroundScroll(Main.sizeOfScreen());
		levelTwoRoot.getChildren().add(player.getPlayer());
		gameAnimationStarter();
		
		root.getScene().setOnKeyPressed(e -> handleKeyInput(e.getCode()));

	}

	private void step(double elapsedTime, int width, int height) {
		if (!gameOver && !passedLevelTwo) {
			stepCounter++;
			if (stepCounter % 200 == 0 && !disableEnemies) {
				Enemy enemy = new Enemy();
				enemiesList.add(enemy);
				levelTwoRoot.getChildren().add(enemy.getRect());
			}
			if (stepCounter % 10000 == 0) {
				starToBeInView();
			}
			for (Enemy e : enemiesList) {
				if (e.collision((Shape) player.getPlayer())) {
					setScreenToGameOver(levelTwoRoot, player);
				}
				if (e.isOffScreen()) {
					levelTwoRoot.getChildren().remove(e);
					enemiesOffScreen.add(e);
				}
				e.update(elapsedTime);
			}
			if (winningStarInView) {
				winningStar.update(elapsedTime);
				if (winningStar.collision((Shape) player.getPlayer())) {
					setScreenToWin();
					passedLevelTwo = true;
				}
			}
		}
		enemiesList.removeAll(enemiesOffScreen);
		player.update(elapsedTime);

	}
	
	private void starToBeInView(){
		winningStarInView = true;
		winningStar = new EndOfLevelTwoPoint();
		levelTwoRoot.getChildren().add(winningStar.getStar());
	}

	private void setScreenToWin() {
		levelTwoRoot.getChildren().remove(player.getPlayer());
		levelTwoRoot.getChildren().addAll(exitOutButtonInit());
		displayWinningText();
	}

	private void displayWinningText() {
		Text winningText = new Text(Main.sizeOfScreen() / 5, Main.sizeOfScreen() / 3, "CONGRATULATIONS! \n YOU WON!");
		winningText.setFont(Font.font("Impact", FontWeight.BOLD, 60));
		winningText.setFill(Color.WHITE);
		winningText.setTextAlignment(TextAlignment.CENTER);
		levelTwoRoot.getChildren().add(winningText);
	}

	private void makeBackgroundScroll(final int width) {

		TranslateTransition translateTransition = new TranslateTransition(Duration.millis(10000), levelTwoBackground1);
		translateTransition.setFromX(0);
		translateTransition.setToX(-1 * levelTwoBackground1.getLayoutBounds().getWidth());
		translateTransition.setInterpolator(Interpolator.LINEAR);

		TranslateTransition translateTransition2 = new TranslateTransition(Duration.millis(10000), levelTwoBackground2);
		translateTransition2.setFromX(0);
		translateTransition2.setToX(-1 * levelTwoBackground1.getLayoutBounds().getWidth());
		translateTransition2.setInterpolator(Interpolator.LINEAR);

		parallelTransition = new ParallelTransition(translateTransition2, translateTransition);
		parallelTransition.setCycleCount(Animation.INDEFINITE);
		parallelTransition.play();
	}
	
	private void handleKeyInput(KeyCode code) {
		switch (code) {
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
		case SPACE:
			player.jump();
			break;

		case E:
			disableEnemies = true;
			for (Enemy e : enemiesList) {
				levelTwoRoot.getChildren().remove(e.getRect());
			}
			break;
		case T:
			starToBeInView();

		default:
			// do nothing
		}
	}

	private void gameAnimationStarter() {
		KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY),
				e -> this.step(SECOND_DELAY, Main.sizeOfScreen(), Main.sizeOfScreen()));
		Timeline animation = new Timeline();
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.getKeyFrames().add(frame);
		animation.play();
	}

}
