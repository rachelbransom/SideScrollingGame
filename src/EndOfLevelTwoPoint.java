
import javafx.scene.paint.Color;

import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

public class EndOfLevelTwoPoint extends Sprite{
	Polygon endPoint;
	
	public EndOfLevelTwoPoint(){
		endPoint = new Polygon();
		endPoint.getPoints().addAll(new Double[] {3.0, 0.0, 5.0, 5.0, 0.0, 2.0, 6.0, 2.0, 1.0, 5.0, 3.0, 0.0});
		endPoint.setScaleX(10);
		endPoint.setScaleY(10);
		endPoint.setTranslateX(680);
		endPoint.setTranslateY(450);
		endPoint.setFill(Color.YELLOW);
		this.setXv(300);
	}
	
	public Polygon getStar(){
		return endPoint;
	}
	
	public void update(double elapsedTime) {
		endPoint.setLayoutX(endPoint.getLayoutX() - this.getXv() * elapsedTime);
		endPoint.setRotate(100);
		
	}
	
	public boolean collision(Shape mainPlayer) {
		Shape intersect = Shape.intersect((Shape) this.getStar(), mainPlayer);
		// System.out.print(intersect.getBoundsInLocal().getWidth() != -1);
		return (intersect.getBoundsInLocal().getWidth() != -1);
	}
}
