import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class SplashScreen {
	private Group splashRoot = new Group();
	private Scene splashScene;
	private ImageView splashImage;
	private Button playButton;
	private Button instructionsButton, levelTwoButton;
	private LevelOne levelOne;
	public static final int SIZE = 650;

	public void splashInit(Group root) {
		splashRoot = root;
		Image image = new Image(getClass().getClassLoader().getResourceAsStream("SplashScreenImage.jpg"));
		splashImage = new ImageView(image);
		splashRoot.getChildren().add(splashImage);

		splashText();
		displaySplashButtons();

		playButton.setOnAction((event) -> {
			startGame(1);
		});

		// return splashScene;
	}

	public void splashText() {
		Text displayText = new Text(SIZE / 8, SIZE / 4, "Sink or \n  Swim");
		displayText.setFont(Font.font("Impact", FontWeight.BOLD, 160));
		displayText.setFill(Color.TEAL);
		splashRoot.getChildren().add(displayText);
	}

	public void displaySplashButtons() {
		playButton = new Button("Play Level 1");
		playButton.setLayoutX(SIZE / 4);
		playButton.setLayoutY(SIZE * 3 / 5);
		playButton.setPrefSize(SIZE / 2, 40);

		splashRoot.getChildren().add(playButton);

		levelTwoButton = new Button("Play Level 2");
		levelTwoButton.setLayoutX(SIZE / 4);
		levelTwoButton.setLayoutY(SIZE * 7 / 10);
		levelTwoButton.setPrefSize(SIZE / 2, 40);
		splashRoot.getChildren().add(levelTwoButton);

		instructionsButton = new Button("Instructions");
		instructionsButton.setLayoutX(SIZE / 4);
		instructionsButton.setLayoutY(SIZE * 4 / 5);
		instructionsButton.setPrefSize(SIZE / 2, 40);
		splashRoot.getChildren().add(instructionsButton);

	}

	public void startGame(int level) {
		Main.getMyGame().changeScreen(level);

		// System.out.print("hi");
		// splashScene.

	}

}
