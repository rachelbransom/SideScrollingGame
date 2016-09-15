//Rachel Bransom
//This is to intizliate and update coins, used in level one. The player has to collect
//the coins in order to pass onto the next level
//This is assuming level one is functioning perfectly
//This class is depdent on Level One and the Sprite class, because this is a subclass of it
// This class is automatically used when level one runs.
import java.util.Random;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class Coin extends Sprite {
	private Circle circle;
	//constructor
	public Coin() {
		Random rand = new Random();
		circle = new Circle();
		circle.setCenterX(680);
		circle.setCenterY(rand.nextFloat() * 600);
		circle.setRadius(20);
		circle.setFill(Color.GOLD);
		this.setXv(rand.nextInt(100) + 20);
	}
	//updates the position of the coin on the screen so it has a 'speed'
	public void update(double elapsedTime) {
		circle.setCenterX(circle.getCenterX() - this.getXv() * elapsedTime);
	}
	//getter for the shape within the coin
	public Node getCircle() {
		return circle;
	}
	//collision checker - sees if the main player has collided with coin
	public boolean collision(Shape mainPlayer) {
		Shape intersect = Shape.intersect((Shape) this.getCircle(), mainPlayer);
		return (intersect.getBoundsInLocal().getWidth() != -1);
	}
}
