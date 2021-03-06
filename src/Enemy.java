//Rachel Bransom
//This class 
import java.util.Random;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Enemy extends Sprite {
	private Rectangle rect;
	private int SPEED = 300;
	
	//constructor
	public Enemy() {
		rect = new Rectangle();
		if (Main.getMyGame().getGameLevel() == 1){
			levelOneEnemyCreation();
		}
		else {
			levelTwoEnemyCreation();
		}
	}
	//creates an enemy for use in level one (floating)
	public void levelOneEnemyCreation(){
		Random rand = new Random();
		float r = (float) (rand.nextFloat() / 2f + 0.5);
		float g = (float) (rand.nextFloat() / 2f + 0.5);
		float b = (float) (rand.nextFloat() / 2f + 0.5);
		Color randomColor = Color.color(r, g, b);
		rect.setFill(randomColor);
		rect.setX(680);
		double size = rand.nextInt(60) + 20;
		rect.setWidth(size);
		rect.setHeight(size);
		rect.setY(rand.nextFloat() * 600);
		this.setXv(rand.nextInt(150) + 20);
	}
	//creates an enemy for use un level 2 (on the ground)
	public void levelTwoEnemyCreation(){
		rect.setFill(Color.ORANGE);
		Random rand = new Random();
		rect.setWidth(25);
		rect.setHeight(rand.nextInt(20)+10);
		rect.setX(rand.nextInt(50)+670);
		rect.setY(450);
		this.setXv(SPEED);
	}
	//updating position and rotation of object
	public void update(double elapsedTime) {
		rect.setX(rect.getX() - this.getXv() * elapsedTime);
		rect.setRotate(rect.getRotate()+10);
	}

	public Node getRect() {
		return rect;
	}
	// whether the enemy is off of the screen and should be discarded
	public boolean isOffScreen(){
		return (rect.getLayoutX()<(-10));
	}
	//when the enemy has collided with the main player in a given frame
	public boolean collision(Shape mainPlayer) {
		Shape intersect = Shape.intersect((Shape) this.getRect(), mainPlayer);
		return (intersect.getBoundsInLocal().getWidth() != -1);
	}

}
