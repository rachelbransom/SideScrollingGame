import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 * @author Rachel Bransom
 */

class Game {
	public static final String TITLE = "Sink or Swim";
	public static final int KEY_INPUT_SPEED = 1000;
	public static final int FRAMES_PER_SECOND = 60;
	private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
	private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
	public static final int SIZE = Main.sizeOfScreen();
	private Scene scene;
	private LevelOne levelOne;
	private LevelTwo levelTwo;
	private int gameLevel;
	private SplashScreen splashScreen;
	protected Group gameRoot = new Group();

	public String getTitle() {
		return TITLE;
	}

	public void changeScreen(int level) {
		gameRoot.getChildren().clear();
		if (level == 1) {
			gameLevel = 1;
			levelOne = new LevelOne();
			levelOne.levelOneInit(gameRoot);
		}
		if (level == 2) {
			gameLevel = 2;
			levelTwo = new LevelTwo();
			levelTwo.levelTwoInit(gameRoot);
		}	
	}

	public Scene init(int width, int height) {
		scene = new Scene(gameRoot, width, height, Color.TRANSPARENT);
		splashScreen = new SplashScreen();
		splashScreen.splashInit(gameRoot);
		return scene;
	}

	public int getGameLevel() {
		return gameLevel;
	}


}
