import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;

/**
 * @author Rachel Bransom rnb11
 */

class Game {
	private final String TITLE = "Sink or Swim";
	private Scene scene;
	private LevelOne levelOne;
	private LevelTwo levelTwo;
	private int gameLevel;
	private SplashScreen splashScreen;
	protected Group gameRoot = new Group();

	public String getTitle() {
		return TITLE;
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
}
