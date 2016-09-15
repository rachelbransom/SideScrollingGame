//Rachel Bransom
// This class starts the program when it is run
//This assumes everything compiles correctly
//It is dependent on the Game class, because an instance of game is initialized and a method from game is called
// This is used simply by running the file

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author Rachel Bransom rnb11
 */
public class Main extends Application {
	public static int SIZE = 650;
	private static Game myGame;
	//starts the program by setting up the scene and stage and calling the first initializer
	@Override
	public void start(Stage stage) {
		myGame = new Game();
		stage.setTitle(myGame.getTitle());
		Scene scene = myGame.init(SIZE, SIZE);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
	}

	//myGame has been made static because I call getMyGame every time
	//I need to change screens, using the method in Game. This was the only
	//way I knew to connect the parts of the game
	public static Game getMyGame() {
		return myGame;
	}
	//getter for the size of the screen
	public static int sizeOfScreen(){
		return SIZE;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
