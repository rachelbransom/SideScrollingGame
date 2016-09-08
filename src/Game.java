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
	//private static final double GROWTH_RATE = 1.1;
	//private static final int BACKGROUND_SPEED = 1;

	//private Scene flashScene, myLevel1Scene, myLevel2Scene, gameOverScene;
	private Scene scene;
	private ImageView levelOneBackground1;
	private ImageView levelOneBackground2;
	private PlayersFish player;
	private Rectangle myBottomBlock;
	private int stepCounter;
	private ArrayList<Enemy> enemiesList = new ArrayList<Enemy>();
	protected Group gameRoot = new Group();
	private ParallelTransition parallelTransition;
	private LevelOne levelOne;
	
	private SplashScreen splashScreen;
	
	/**
	 * Returns name of the game.
	 */
	public String getTitle() {
		return TITLE;
	}
	
	public void changeScreen(int level){
		gameRoot.getChildren().clear();
		if (level ==1){
			levelOne = new LevelOne();
			levelOne.levelOneInit(gameRoot);
		}
	}
	
	public void gameAnimationStarter(){
		KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY),
                e -> this.step(SECOND_DELAY, SIZE, SIZE));
		Timeline animation = new Timeline();
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.getKeyFrames().add(frame);
		animation.play();
	}
	
	

	/**
	 * Create the game's scene
	 */
	public Scene init(int width, int height) {
		scene = new Scene(gameRoot, width, height, Color.TRANSPARENT);
		splashScreen = new SplashScreen();
		splashScreen.splashInit(gameRoot);
		return scene;
		// create a scene graph to organize the scene
		// Group root = new Group();
		// create a place to see the shapes

//		myLevel1Scene = new Scene(root, width, height, Color.TRANSPARENT);
//		Image image = new Image(getClass().getClassLoader().getResourceAsStream("SeaFloorBackground.png"));
//		Image image2 = new Image(getClass().getClassLoader().getResourceAsStream("SeaFloorBackground.png"));
//		levelOneBackground1 = new ImageView(image);
//		levelOneBackground2 = new ImageView(image2);
//		
//		// Rectangle sea = new Rectangle(width, height*0.9);
//		// sea.setFill(Color.LIGHTSKYBLUE);
//		root.getChildren().add(levelOneBackground1);
//		root.getChildren().add(levelOneBackground2);
//		levelOneBackground2.setX(1089);
		// levelOneBackground1.setX(width / 2 -
		// levelOneBackground1.getBoundsInLocal().getWidth() / 2);
		// levelOneBackground1.setY(height / 2 -
		// levelOneBackground1.getBoundsInLocal().getHeight() / 2);
//		makeBackgroundScroll(width);
		// Bubble[] bubbles = new Bubble[]{new Bubble(400,100), new
		// Bubble(150,200), new Bubble(20,450),
		// new Bubble(400,400)};
		// Sliding backgroundSlider = new Sliding(image,width);
		// root.getChildren().addAll(bubbleSlider);
		// make some shapes and set their properties
		// Image image = new
		// Image(getClass().getClassLoader().getResourceAsStream("duke.gif"));
		// myBouncer = new ImageView(image);
		// // x and y represent the top left corner, so center it
		// myBouncer.setX(width / 2 - myBouncer.getBoundsInLocal().getWidth() /
		// 2);
		// myBouncer.setY(height / 2 - myBouncer.getBoundsInLocal().getHeight()
		// / 2);
		// myTopBlock = new Rectangle(width / 2 - 25, height / 2 - 100, 50, 50);
		// myTopBlock.setFill(Color.RED);

		// myBottomBlock = new Rectangle(width / 2 - 25, height / 2 + 50, 50,
		// 50);
		// myBottomBlock.setFill(Color.BISQUE);
		// order added to the group is the order in whuch they are drawn
		// root.getChildren().add(myBouncer);
		//levelOne = new LevelOne();
		
		//player = new PlayersFish();
		//root.getChildren().add(player.getPlayer());
		// root.getChildren().add(myBottomBlock);
		// respond to input
		//myLevel1Scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
		// myScene.setOnMouseClicked(e -> handleMouseInput(e.getX(), e.getY()));
		
	}

	/**
	 * Change properties of shapes to animate them
	 * 
	 * Note, there are more sophisticated ways to animate shapes,
	 */
	// but these simple ways work too.
	// called every frame
	public void step(double elapsedTime, int width, int height) {
		stepCounter++;
		if (stepCounter % 50 == 0) {
			Enemy enemy = new Enemy();
			enemiesList.add(enemy);
			//root.getChildren().add(enemy.getRect());
		}

		// levelOneBackground.setLayoutX(levelOneBackground.getLayoutX()-BACKGROUND_SPEED*elapsedTime);
		// if(levelOneBackground.getLayoutX() < -200){
		// levelOneBackground.setLayoutX(0);
		// }
		player.update(elapsedTime);
		for (Enemy e : enemiesList) {
			e.collision((Shape) player.getPlayer());
			e.update(elapsedTime);
		}

	}

	public void makeBackgroundScroll(final int width) {
		// System.out.println(levelOneBackground.getBoundsInLocal());
		TranslateTransition translateTransition = new TranslateTransition(Duration.millis(10000), levelOneBackground1);
		translateTransition.setFromX(0);
		translateTransition.setToX(-1 * levelOneBackground1.getLayoutBounds().getWidth());
		translateTransition.setInterpolator(Interpolator.LINEAR);
		//translateTransition.setCycleCount(Animation.INDEFINITE);
		
		//System.out.println(levelOneBackground1.getLayoutX());
		TranslateTransition translateTransition2 = new TranslateTransition(Duration.millis(10000), levelOneBackground2);
		translateTransition2.setFromX(0);
		translateTransition2.setToX(-1089);
		translateTransition2.setInterpolator(Interpolator.LINEAR);
		
		//translateTransition.play();
		//translateTransition2.play();
		parallelTransition = new ParallelTransition(translateTransition2, translateTransition);
		parallelTransition.setCycleCount(Animation.INDEFINITE);
		
		//parallelTransition.setRate(BACKGROUND_SPEED);
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
