//Rachel Bransom
// This starts the splash screen. It initializes the background,text, and buttons
// this class is dependent on only the game class to start up the game. It calls the change screens method in it
//Methods in this class are called automatically from the game class

import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class SplashScreen {
	private Group splashRoot = new Group();
	private ImageView splashImage;
	private Button playButton;
	private Button instructionsButton, levelTwoButton;
	private Text displayText, instructionsText;
	public static final int SIZE = Main.sizeOfScreen();

	// public because game manages levels
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
		levelTwoButton.setOnAction((event) -> {
			startGame(2);
		});
		instructionsButton.setOnAction((event) -> {
			displayInstructions();
		});
	}
	//removes the original splash screen text and buttons and displays instructions on screen
	private void displayInstructions(){
		splashRoot.getChildren().removeAll(playButton, levelTwoButton, 
											instructionsButton, displayText);
		Text instructionsHeader = new Text(SIZE/6, SIZE/6, "INSTRUCTIONS");
		instructionsHeader.setFont(Font.font("Impact", FontWeight.BOLD, 80));;
		instructionsHeader.setFill(Color.TEAL);
		
		instructionsText = new Text(SIZE/8, SIZE/4, "For level 1, you are a diver (red square) "
				+ "swimming through the ocean. Avoid all the multicolored square fish "
				+ "but collect all the coins!\n"
				+ "Once you have collected 10 coins, you pass onto the next level! \n "
				+ "To move, use the four arrow key buttons. You cannot go off of the screen \n"
				+ "\n"
				+ "For level 2, you are walking on the sea floor. You can move "
				+ "forwards and backwards, using the left and right arrow keys "
				+ "and to jump you press space bar. You'll need this to avoid "
				+ "the venemous crabs walking on the sea floor - if you make "
				+ "contact with one you'll die! \n"
				+ "\n"
				+ "GOOD LUCK!");
		instructionsText.setWrappingWidth(SIZE-100);
		instructionsText.setFont(Font.font("Impact", FontWeight.NORMAL, 20));;
		instructionsText.setFill(Color.WHITE);
		
		playButton = new Button("START");
		
		splashRoot.getChildren().addAll(instructionsText, instructionsHeader, 
				setButtonLayout(playButton,8));
		
		playButton.setOnAction((event) -> {
			startGame(1);
		});
	}
	// displays bold sink or swim letters
	private void splashText() {
		displayText = new Text(SIZE / 8, SIZE / 4, "Sink or \n  Swim");
		displayText.setFont(Font.font("Impact", FontWeight.BOLD, 160));
		displayText.setFill(Color.TEAL);
		splashRoot.getChildren().add(displayText);
	}
	//initalizes buttons and displays them to start level1, level2, or instructions
	private void displaySplashButtons() {
		playButton = new Button("Play Level 1");
		splashRoot.getChildren().add(setButtonLayout(playButton, 6));

		levelTwoButton = new Button("Play Level 2");
		splashRoot.getChildren().add(setButtonLayout(levelTwoButton, 7));

		instructionsButton = new Button("Instructions");		
		splashRoot.getChildren().add(setButtonLayout(instructionsButton, 8));
	}
	//sets the positions of the buttons
	private Button setButtonLayout(Button button, double layoutY){
		button.setLayoutX(SIZE/4);
		button.setLayoutY(SIZE*(layoutY/10));
		button.setPrefSize(SIZE/2, 40);
		return button;
	}
	//starts game by calling the screen change method in game
	private void startGame(int level) {
		Main.getMyGame().changeScreen(level);
	}

}
