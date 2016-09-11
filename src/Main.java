import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author Rachel Bransom rnb11
 */
public class Main extends Application {
	public static final int SIZE = 650;
	public static final int FRAMES_PER_SECOND = 60;
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
