import java.util.Random;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class Coin extends Sprite {
	private Circle circle;

	public Coin() {
		Random rand = new Random();
		circle = new Circle();
		circle.setCenterX(680);
		circle.setCenterY(rand.nextFloat() * 600);
		circle.setRadius(20);
		circle.setFill(Color.GOLD);
		this.setXv(rand.nextInt(100) + 20);
	}

	public void update(double elapsedTime) {
		circle.setCenterX(circle.getCenterX() - this.getXv() * elapsedTime);
	}

	public Node getCircle() {
		return circle;
	}

	public boolean collision(Shape mainPlayer) {
		Shape intersect = Shape.intersect((Shape) this.getCircle(), mainPlayer);
		return (intersect.getBoundsInLocal().getWidth() != -1);
	}
	
}
