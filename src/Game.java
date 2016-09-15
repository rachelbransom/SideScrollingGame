//Rachel Bransom
//This is my controller for the game. It initiliazes the scene and starts the game up after main is called
//This also changes the screen every time a level is passed, or lost
// This class is dependent on main, because it calls it. It is also dependent on SplashRoot, LevelOne, and LevelTwo
// It could be extended to make the game smoother to transition through e.g. exit button on the levels to go back to main menu

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
	//initializes scene for the entire game and call splash screen to start up
	public Scene init(int width, int height) {
		scene = new Scene(gameRoot, width, height, Color.TRANSPARENT);
		splashScreen = new SplashScreen();
		splashScreen.splashInit(gameRoot);
		return scene;
	}

	public int getGameLevel() {
		return gameLevel;
	}
	//changes screen when a level is over/when the use wants
	//to leave the main screen
	//This method calls all other screens
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
