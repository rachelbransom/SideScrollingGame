import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ParallelTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.util.ArrayList;
import java.util.Random;

//import BackgroundScroller.Sliding;

/**
 * Separate the game code from some of the boilerplate code.
 * 
 * @author Rachel Bransom
 */
class Game {
	public static final String TITLE = "Sink or Swim";
	public static final int KEY_INPUT_SPEED = 1000;
	public static final int FRAMES_PER_SECOND = 60;
	private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
	private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
	public static final int SIZE = 650;
	private Scene scene;
	private ImageView levelOneBackground1;
	private ImageView levelOneBackground2;
	private PlayersFish player;
	private Rectangle myBottomBlock;
	private int stepCounter;
	private ArrayList<Enemy> enemiesList = new ArrayList<Enemy>();
	private ArrayList<Coin> coinList = new ArrayList<Coin>();
	protected Group gameRoot = new Group();
	private ParallelTransition parallelTransition;
	private LevelOne levelOne;
	private LevelTwo levelTwo;
	private int gameLevel;
	private SplashScreen splashScreen;

	/**
	 * Returns name of the game.
	 */
	public String getTitle() {
		return TITLE;
	}

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

	public void gameAnimationStarter() {
		KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> 
					this.step(SECOND_DELAY, Main.sizeOfScreen(), Main.sizeOfScreen()));
		Timeline animation = new Timeline();
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.getKeyFrames().add(frame);
		animation.play();
	}

	public Scene init(int width, int height) {
		scene = new Scene(gameRoot, width, height, Color.TRANSPARENT);
		splashScreen = new SplashScreen();
		splashScreen.splashInit(gameRoot);
		return scene;
	}

	public void step(double elapsedTime, int width, int height) {
		stepCounter++;
		if (stepCounter % 50 == 0) {
			Enemy enemy = new Enemy();
			enemiesList.add(enemy);
		}
		if (stepCounter % 80 == 0) {
			Coin coin = new Coin();
			coinList.add(coin);
		}
		player.update(elapsedTime);
		for (Enemy e : enemiesList) {
			if (e.collision((Shape) player.getPlayer())) {
				System.out.print("end");
			};
			e.update(elapsedTime);
		}
		for (Coin c : coinList) {
			if (c.collision((Shape) player.getPlayer())) {

			}
		}

	}

	public void makeBackgroundScroll(final int width) {
		// System.out.println(levelOneBackground.getBoundsInLocal());
		TranslateTransition translateTransition = new TranslateTransition(Duration.millis(10000), levelOneBackground1);
		translateTransition.setFromX(0);
		translateTransition.setToX(-1 * levelOneBackground1.getLayoutBounds().getWidth());
		translateTransition.setInterpolator(Interpolator.LINEAR);
		// translateTransition.setCycleCount(Animation.INDEFINITE);

		// System.out.println(levelOneBackground1.getLayoutX());
		TranslateTransition translateTransition2 = new TranslateTransition(Duration.millis(10000), levelOneBackground2);
		translateTransition2.setFromX(0);
		translateTransition2.setToX(-1089);
		translateTransition2.setInterpolator(Interpolator.LINEAR);

		// translateTransition.play();
		// translateTransition2.play();
		parallelTransition = new ParallelTransition(translateTransition2, translateTransition);
		parallelTransition.setCycleCount(Animation.INDEFINITE);

		// parallelTransition.setRate(BACKGROUND_SPEED);
		parallelTransition.play();

		//
		// Sets the label of the Button based on the animation state
		//
		// parallelTransition.statusProperty().addListener((obs, oldValue,
		// newValue) -> {
		// if( newValue == Animation.Status.RUNNING ) {
		// btnControl.setText( "||" );
		// } else {
		// btnControl.setText( ">" );
		// }
		// });
		// }
		//
		// Timeline animation = new Timeline();
		// animation.setCycleCount(Timeline.INDEFINITE);
		// EventHandler<ActionEvent> onFinished = new
		// EventHandler<ActionEvent>() {
		// public void handle(ActionEvent t){
		// //System.out.println(levelOneBackground.getTranslateX());
		// levelOneBackground.setTranslateX(levelOneBackground.getTranslateX()-1.0);
		//
		// System.out.println(levelOneBackground.getBoundsInLocal().getWidth());
		// System.out.println(levelOneBackground.getLayoutX());
		// System.out.println(levelOneBackground.getLayoutX()+levelOneBackground.getTranslateX()+levelOneBackground.getBoundsInLocal().getWidth());
		// if
		// (levelOneBackground.getLayoutX()+levelOneBackground.getTranslateX()+levelOneBackground.getBoundsInLocal().getWidth()<=0){
		// levelOneBackground.setTranslateX(width-levelOneBackground.getLayoutX());
		// }
		// }
		//
		//
		// };
		//
		// KeyValue keyValueX = new
		// KeyValue(levelOneBackground.rotateProperty(),0);
		// KeyFrame keyFrame = new KeyFrame(new Duration(35), onFinished ,
		// keyValueX); //, keyValueY);
		// animation.getKeyFrames().add(keyFrame);
		// animation.play();
	}

	// counter, when divisible by some x create
	// update attributes
	// myBouncer.setX(myBouncer.getX() + BOUNCER_SPEED * elapsedTime);
	// myTopBlock.setRotate(myBottomBlock.getRotate() - 1);
	// myBottomBlock.setRotate(myBottomBlock.getRotate() + 1);
	//
	// // check for collisions
	// // with shapes, can check precisely
	// Shape intersect = Shape.intersect(myTopBlock, myBottomBlock);
	// if (intersect.getBoundsInLocal().getWidth() != -1) {
	// myTopBlock.setFill(Color.MAROON);
	// }
	// else {
	// myTopBlock.setFill(Color.RED);
	// }
	// // with images can only check bounding box
	// if
	// (myBottomBlock.getBoundsInParent().intersects(myBouncer.getBoundsInParent()))
	// {
	// myBottomBlock.setFill(Color.BURLYWOOD);
	// }
	// else {
	// myBottomBlock.setFill(Color.BISQUE);
	// }
	// }

	// What to do each time a key is pressed
	private void handleKeyInput(KeyCode code) {
		switch (code) {

		case UP:
			if (player.getPlayerYPos() > -300) {
				player.setYv(player.getYv() - 3);
			}
			break;

		case DOWN:
			if (player.getPlayerYPos() < 300) {
				player.setYv(player.getYv() + 3);
			}
			break;

		default:
			// do nothing
		}
	}

	public int getGameLevel() {
		return gameLevel;
	}

	class Bubble extends Circle {
		public Bubble(double centerX, double centerY) {
			super(0, 0, 20);
			this.setTranslateX(centerX);
			this.setTranslateY(centerY);
			this.setFill(Color.WHITESMOKE);
			this.setOpacity(0.5);
		}

	}

	// What to do each time a key is pressed
	// private void handleMouseInput (double x, double y) {
	// if (myBottomBlock.contains(x, y)) {
	// myBottomBlock.setScaleX(myBottomBlock.getScaleX() * GROWTH_RATE);
	// myBottomBlock.setScaleY(myBottomBlock.getScaleY() * GROWTH_RATE);
	// }
	// }
}
