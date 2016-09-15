//Rachel Bransom

//this is the level manager that controls the game over screen
//there was a lot of duplciate code that i consolidated here
//it assumes the collisions are being tracked, so that the level can be over
// It has no dependencies throughout
//it is called when there is a collision of the main character with an enemy

import javafx.animation.ParallelTransition;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class Level {
	protected boolean gameOver;
	private static final int SIZE = Main.sizeOfScreen();
	private Button playAgain, exitOut;
	private Group tempRoot;
	private Text gameOverText;
	protected boolean disableEnemies;
	protected ImageView levelTwoBackground1, levelTwoBackground2;
	protected ParallelTransition parallelTransition;
	//calls other methods to set up buttons and text
	public void setScreenToGameOver(Group root, PlayersFish player) {
		tempRoot = root;
		gameOver = true;
		tempRoot.getChildren().remove(player.getPlayer());
		displayGameOverText();
		displayGameOverButtons();
	}
	//displays text 'game over' on screen
	public void displayGameOverText() {
		gameOverText = new Text(SIZE / 5, SIZE / 3, "GAME\n OVER");
		gameOverText.setFont(Font.font("Impact", FontWeight.BOLD, 160));
		gameOverText.setFill(Color.WHITE);
		gameOverText.setTextAlignment(TextAlignment.CENTER);
		tempRoot.getChildren().add(gameOverText);
	}
	//initializes and displays play again button on screen 
	public void displayGameOverButtons() {
		playAgain = new Button("Play Again");
		playAgain.setLayoutX(SIZE / 4);
		playAgain.setLayoutY(SIZE * 3 / 4);
		playAgain.setPrefSize(SIZE / 2, 40);
		tempRoot.getChildren().add(playAgain);

		playAgain.setOnAction((event) -> {
			Main.getMyGame().changeScreen(1);
		});

		exitOutButtonInit();
	}
	//initializes and displays exit out button on game over screen
	public Button exitOutButtonInit() {
		exitOut = new Button("Exit Out");
		exitOut.setLayoutX(SIZE / 4);
		exitOut.setLayoutY(SIZE * 9 / 10);
		exitOut.setPrefSize(SIZE / 2, 40);

		exitOut.setOnAction((event) -> {
			System.exit(0);
		});

		return exitOut;
	}

}
