import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
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


/**
 * Separate the game code from some of the boilerplate code.
 * 
 * @author Rachel Bransom
 */
class Game {
    public static final String TITLE = "Sink or Swim";
    public static final int KEY_INPUT_SPEED = 1000;
    private static final double GROWTH_RATE = 1.1;
    private static final int BOUNCER_SPEED = 30;

    private Scene myScene;
//    private ImageView myBouncer;
    private PlayersFish player;
    private Rectangle myBottomBlock;
    private int stepCounter;
    private ArrayList<Enemy> enemiesList = new ArrayList<Enemy>();
    private Group root = new Group();
    
    /**
     * Returns name of the game.
     */
    public String getTitle () {
        return TITLE;
    }

    /**
     * Create the game's scene
     */
    public Scene init (int width, int height) {
        // create a scene graph to organize the scene
        //Group root = new Group();
        // create a place to see the shapes
        myScene = new Scene(root, width, height, Color.YELLOW);
        Rectangle sea = new Rectangle(width, height*0.9);
        sea.setFill(Color.LIGHTSKYBLUE);
        root.getChildren().add(sea);
        Bubble[] bubbles = new Bubble[]{new Bubble(400,100), new Bubble(150,200), new Bubble(20,450),
        		new Bubble(400,400)};
        Sliding bubbleSlider = new Sliding(bubbles,width);
        root.getChildren().addAll(bubbleSlider);
        // make some shapes and set their properties
//        Image image = new Image(getClass().getClassLoader().getResourceAsStream("duke.gif"));
//        myBouncer = new ImageView(image);
//        // x and y represent the top left corner, so center it
//        myBouncer.setX(width / 2 - myBouncer.getBoundsInLocal().getWidth() / 2);
//        myBouncer.setY(height / 2  - myBouncer.getBoundsInLocal().getHeight() / 2);
//        myTopBlock = new Rectangle(width / 2 - 25, height / 2 - 100, 50, 50);
//        myTopBlock.setFill(Color.RED);
        
//        myBottomBlock = new Rectangle(width / 2 - 25, height / 2 + 50, 50, 50);
//        myBottomBlock.setFill(Color.BISQUE);
        // order added to the group is the order in whuch they are drawn
//       root.getChildren().add(myBouncer);
        player = new PlayersFish();
        root.getChildren().add(player.getPlayer());
//       root.getChildren().add(myBottomBlock);
        // respond to input
        myScene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
//        myScene.setOnMouseClicked(e -> handleMouseInput(e.getX(), e.getY()));
        return myScene;
    }

    /**
     * Change properties of shapes to animate them
     * 
     * Note, there are more sophisticated ways to animate shapes,
     */
    //but these simple ways work too.
     //called every frame
    public void step (double elapsedTime, int width, int height) {
    	stepCounter ++;
    	if (stepCounter % 50==0){
    		Enemy enemy = new Enemy();
    		enemiesList.add(enemy);
    		root.getChildren().add(enemy.getRect());
    	}
    	
    	player.update(elapsedTime);
    	for (Enemy e : enemiesList){
    		e.update(elapsedTime);
    	}
    	
    }
    	
    	
    
    // counter, when divisible by some x create
        // update attributes
//        myBouncer.setX(myBouncer.getX() + BOUNCER_SPEED * elapsedTime);
//        myTopBlock.setRotate(myBottomBlock.getRotate() - 1);
//        myBottomBlock.setRotate(myBottomBlock.getRotate() + 1);
//        
//        // check for collisions
//        // with shapes, can check precisely
//        Shape intersect = Shape.intersect(myTopBlock, myBottomBlock);
//        if (intersect.getBoundsInLocal().getWidth() != -1) {
//            myTopBlock.setFill(Color.MAROON);
//        }
//        else {
//            myTopBlock.setFill(Color.RED);
//        }
//        // with images can only check bounding box
//        if (myBottomBlock.getBoundsInParent().intersects(myBouncer.getBoundsInParent())) {
//            myBottomBlock.setFill(Color.BURLYWOOD);
//        }
//        else {
//            myBottomBlock.setFill(Color.BISQUE);
//        }
//    }


    // What to do each time a key is pressed
    private void handleKeyInput (KeyCode code) {
        switch (code) {
     
            case UP:
            //	for(int i = 0; i < 5; i++){
            	//	player.getPlayer().setLayoutY(player.getPlayer().getTranslateY() - KEY_INPUT_SPEED*0.01);
            	//}
            	player.setYv(player.getY()-3);
            	break;
            case DOWN:
            	player.setYv(player.getY()+3);
            	//getPlayer().setLayoutY(player.getPlayer().getTranslateY() + KEY_INPUT_SPEED*0.01);
            	break;
            default:
                // do nothing
        }
    }
    
    class Bubble extends Circle{
    		public Bubble(double centerX, double centerY){
    			super(0,0,20);
    			this.setTranslateX(centerX);
    			this.setTranslateY(centerY);
    			this.setFill(Color.WHITESMOKE);
    			this.setOpacity(0.5);
    		}
    }
    
    class Sliding extends Group {
    	final Node[] content;
    	Timeline anim = new Timeline();
    	
    	public Sliding(final Node[] content, final int width){
    		this.content = content;
    		this.getChildren().addAll(content);
    		anim.setCycleCount(Timeline.INDEFINITE);
            EventHandler<ActionEvent> onFinished =new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    for(Node n : content) {
                        n.setTranslateX(n.getTranslateX() - 1.0);
                        if(n.getLayoutX() + n.getTranslateX() + n.getBoundsInLocal().getWidth() <= 0) {
                            n.setTranslateX(width - n.getLayoutX());
                        }
                    }
                }
    	};
    	KeyValue keyValueX = new KeyValue(Sliding.this.rotateProperty(),0);
        KeyFrame keyFrame = new KeyFrame(new Duration(35), onFinished , keyValueX); //, keyValueY);
        anim.getKeyFrames().add(keyFrame);
        anim.play();
    }
    }

    // What to do each time a key is pressed
//    private void handleMouseInput (double x, double y) {
//        if (myBottomBlock.contains(x, y)) {
//            myBottomBlock.setScaleX(myBottomBlock.getScaleX() * GROWTH_RATE);
//            myBottomBlock.setScaleY(myBottomBlock.getScaleY() * GROWTH_RATE);
//        }
//    }
}
