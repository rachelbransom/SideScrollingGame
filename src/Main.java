import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author Rachel Bransom rnb11
 */
public class Main extends Application {
	public static int SIZE = 650;
	private static Game myGame;
	
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
	
	public static int sizeOfScreen(){
		return SIZE;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
