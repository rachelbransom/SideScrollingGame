
import javafx.scene.Group;
import javafx.scene.control.Button;
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
	
	
	
	public void setScreenToGameOver(Group root, PlayersFish player) {
		tempRoot = root;
		gameOver = true;
		tempRoot.getChildren().remove(player.getPlayer());
		displayGameOverText();
		displayGameOverButtons();

		playAgain.setOnAction((event) -> {
			Main.getMyGame().changeScreen(1);
		});

		exitOut.setOnAction((event) -> {
			System.exit(0);
		});

	}
	
	public void displayGameOverText() {
		gameOverText = new Text(SIZE / 5, SIZE / 3, "GAME\n OVER");
		gameOverText.setFont(Font.font("Impact", FontWeight.BOLD, 160));
		gameOverText.setFill(Color.WHITE);
		gameOverText.setTextAlignment(TextAlignment.CENTER);
		tempRoot.getChildren().add(gameOverText);
	}
	
	
	public void displayGameOverButtons() {
		playAgain = new Button("Play Again");
		playAgain.setLayoutX(SIZE / 4);
		playAgain.setLayoutY(SIZE * 3 / 4);
		playAgain.setPrefSize(SIZE / 2, 40);
		tempRoot.getChildren().add(playAgain);

		exitOutButtonInit();
	}
	
	public Button exitOutButtonInit() {
		exitOut = new Button("Exit Out");
		exitOut.setLayoutX(SIZE / 4);
		exitOut.setLayoutY(SIZE * 9 / 10);
		exitOut.setPrefSize(SIZE / 2, 40);
		return exitOut;
	}
}
