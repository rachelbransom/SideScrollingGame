//This entire file is part of my masterpiece.
//Rachel Bransom
//rnb11

//This is the manager for the first level - it initilizes everything and updates the characters in the game
// It assumes all the objects are working
// This class is dependent on PlayersFish and the game class - in order to change screens onto level 2/game over
//The methods in this class are called by the game class

import java.util.ArrayList;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.ParallelTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

public class LevelOne extends Level {
	private Group levelOneRoot;
	private ImageView levelOneBackground1, levelOneBackground2;
	private PlayersFish player;
	private int stepCounter, GOAL_NUMBER_COINS = 10, numCoinsCollected;
	private ArrayList<Enemy> enemiesList = new ArrayList<Enemy>();
	private ArrayList<Coin> coinList = new ArrayList<Coin>();
	private ArrayList<Coin> coinsCollidedToDelete = new ArrayList<Coin>();
	private ParallelTransition parallelTransition;
	private boolean passedLevelOne;
	private Text score;
	private Button continueToLevelTwo;
	private Image image;
	private Image image2;

	public static final int SIZE = Main.sizeOfScreen();
	public static final int FRAMES_PER_SECOND = 60;
	private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
	private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
	private static final int MILLISECONDS_PER_ENEMY = 50;
	private static final int MILLISECONDS_PER_COIN = 80;
	private static final int LEVEL2_CONGRATS_TEXT_SIZE = 60;
	private static final int LEVEL2_SCORE_TEXT_SIZE = 40;
	private static final int TRANSITION_MILLISECOND_DURATION = 10000;
	private static final int PLAYER_SPEED = 3;
	private static final int SCOREBOARD_XPOS = SIZE - 150;
	private static final int SCOREBOARD_YPOS = 50;
	
	//initializes level one to add all the revelant variables into the root
	public void levelOneInit(Group root) {
		levelOneRoot = root;
		backgroundInit();
		makeBackgroundScroll(SIZE);
		player = new PlayersFish();
		levelOneRoot.getChildren().add(player.getPlayer());
		setUpScoreBoard();
		gameAnimationStarter();
		
		root.getScene().setOnKeyPressed(e -> handleKeyInput(e.getCode()));
	}
	//intitalizes the images that go into the background
	private void backgroundInit(){
		image = new Image(getClass().getClassLoader().getResourceAsStream("SeaFloorBackground.png"));
		image2 = new Image(getClass().getClassLoader().getResourceAsStream("SeaFloorBackground.png"));
		levelOneBackground1 = new ImageView(image);
		levelOneBackground2 = new ImageView(image2);
		levelOneRoot.getChildren().addAll(levelOneBackground1, levelOneBackground2);
		levelOneBackground2.setX(image.getWidth());		
	}
	//displays score in the corner 
	private void setUpScoreBoard() {
		score = new Text("SCORE: " + numCoinsCollected);
		score.setLayoutX(SCOREBOARD_XPOS);
		score.setLayoutY(SCOREBOARD_YPOS);
		score.setFont(Font.font("Impact", FontWeight.BOLD, LEVEL2_SCORE_TEXT_SIZE));
		score.setFill(Color.WHITE);
		levelOneRoot.getChildren().add(score);
	}
	//starts the animation of the game
	private void gameAnimationStarter() {
		KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> this.step(SECOND_DELAY, SIZE, SIZE));
		Timeline animation = new Timeline();
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.getKeyFrames().add(frame);
		animation.play();
	}
	//called every frame to update the characters, add enemies, add coins, and check for collisions
	private void step(double elapsedTime, int width, int height) {
		if (!passedLevelOne && !gameOver) {
			stepCounter++;
			if (stepCounter % MILLISECONDS_PER_ENEMY == 0 && !disableEnemies) {
				addEnemy();
			}
			if (stepCounter % MILLISECONDS_PER_COIN == 0) {
				addCoin();
			}
			for (Enemy e : enemiesList) {
				if (e.collision((Shape) player.getPlayer())) {
					setScreenToGameOver(levelOneRoot, player);
				}
				e.update(elapsedTime);
			}
			for (Coin c : coinList) {
				if (c.collision((Shape) player.getPlayer())) {
					coinCollision(c);
					if (numCoinsCollected == GOAL_NUMBER_COINS) {
						passedLevelOne = true;
						setScreenToLevelTwo();
					}
				}
				c.update(elapsedTime);
			}
			coinList.removeAll(coinsCollidedToDelete);
		}
		player.update(elapsedTime);
	}
	
	private void addEnemy(){
		Enemy enemy = new Enemy();
		enemiesList.add(enemy);
		levelOneRoot.getChildren().add(enemy.getRect());
	}
	
	private void addCoin(){
		Coin coin = new Coin();
		coinList.add(coin);
		levelOneRoot.getChildren().add(coin.getCircle());
	}
	
	private void coinCollision(Coin c){
		coinsCollidedToDelete.add(c);
		levelOneRoot.getChildren().remove(c.getCircle());
		numCoinsCollected++;
		score.setText("SCORE: " + numCoinsCollected);
	}
 
	private void setScreenToLevelTwo() {
		levelOneRoot.getChildren().remove(player.getPlayer());
		displayLevelTwoText();
		displayLevelTwoButtons();
	}

	private void displayLevelTwoText() {
		Text levelTwoText = new Text(SIZE / 8, SIZE / 3, "CONGRATULATIONS!\n YOU PASSED \n LEVEL ONE");
		levelTwoText.setFont(Font.font("Impact", FontWeight.BOLD, LEVEL2_CONGRATS_TEXT_SIZE));
		levelTwoText.setFill(Color.WHITE);
		levelTwoText.setTextAlignment(TextAlignment.CENTER);
		levelOneRoot.getChildren().add(levelTwoText);
	}

	private void displayLevelTwoButtons() {
		continueToLevelTwo = new Button("Proceed to Level 2");
		continueToLevelTwo.setLayoutX(SIZE / 4);
		continueToLevelTwo.setLayoutY(SIZE * 3 / 4);
		continueToLevelTwo.setPrefSize(SIZE / 2, 40);
		levelOneRoot.getChildren().add(continueToLevelTwo);
		continueToLevelTwo.setOnAction((event) -> {
			Main.getMyGame().changeScreen(2);
		});
	}

	private void makeBackgroundScroll(final int width) {
		TranslateTransition translateTransition = new TranslateTransition(Duration.millis(TRANSITION_MILLISECOND_DURATION), levelOneBackground1);
		TranslateTransition translateTransition2 = new TranslateTransition(Duration.millis(TRANSITION_MILLISECOND_DURATION), levelOneBackground2);
		
		translateTransitionInit(translateTransition);
		translateTransitionInit(translateTransition2);

		parallelTransition = new ParallelTransition(translateTransition2, translateTransition);
		parallelTransition.setCycleCount(Animation.INDEFINITE);
		parallelTransition.play();
	}
	
	private void translateTransitionInit(TranslateTransition translateTransition){
		translateTransition.setFromX(0);
		translateTransition.setToX(-1 * levelOneBackground1.getLayoutBounds().getWidth());
		translateTransition.setInterpolator(Interpolator.LINEAR);
	}

	private void handleKeyInput(KeyCode code) {
		int outOfBounds = SIZE/2 - player.getPlayerWidth();
		switch (code) {

		case UP:
			if (player.getPlayerYPos() > -outOfBounds) {
				player.setYv(player.getYv() - PLAYER_SPEED);
			}
			break;

		case DOWN:
			if (player.getPlayerYPos() < outOfBounds) {
				player.setYv(player.getYv() + PLAYER_SPEED);
			}
			break;

		case LEFT:
			if (player.getPlayerXPos() > -outOfBounds) {
				player.setXv(player.getXv() - PLAYER_SPEED);
			}
			break;

		case RIGHT:
			if (player.getPlayerXPos() < outOfBounds) {
				player.setXv(player.getXv() + PLAYER_SPEED);
			}
			break;

		case E:
			disableEnemies = true;
			for (Enemy e : enemiesList) {
				levelOneRoot.getChildren().remove(e.getRect());
			}
			break;

		case T:
			numCoinsCollected = GOAL_NUMBER_COINS;
			passedLevelOne = true;
			setScreenToLevelTwo();
			break;

		default: // nothing
		}
	}

}
