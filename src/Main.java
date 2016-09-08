import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;



/**
 *
 * @author Rachel Bransom rnb11
 */
public class Main extends Application {
    public static final int SIZE = 650;
    public static final int FRAMES_PER_SECOND = 60;
    private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;

    private static Game myGame;
    private LevelOne levelOne;
    private SplashScreen splashScreen;

    /**
     * Set things up at the beginning.
     */
    @Override
    public void start (Stage stage) {
        // create your own game here
        myGame = new Game();
        //splashScreen = new SplashScreen();
        //levelOne = new LevelOne();
        stage.setTitle(myGame.getTitle());
        //stage.setResizable(false);

        // attach game to the stage and display it
        Scene scene = myGame.init(SIZE, SIZE);
        stage.setScene(scene);
        stage.show();
        
        // sets the game's loop
        
    }
    
    public static Game getMyGame(){
    	return myGame;
    }
    
    
    

    /**
     * Start the program.
     */
    public static void main (String[] args) {
        launch(args);
    }
    
 public void resetRoot(){
    	
    }

}
    
    

