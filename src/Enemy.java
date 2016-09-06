import java.util.Random;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class Enemy extends Sprite{
	private Rectangle rect;
	
	public Enemy() {
		rect = new Rectangle();
		Random rand = new Random();
		float r = (float) (rand.nextFloat()/2f+0.5);
		float g = (float) (rand.nextFloat()/2f+0.5);
		float b = (float) (rand.nextFloat()/2f+0.5);
		Color randomColor = Color.color(r, g, b);
		rect.setFill(randomColor);
		
		double size = rand.nextInt(60)+20;
		rect.setWidth(size);
		rect.setHeight(size);
		rect.setX(680);
		rect.setY(rand.nextFloat()*600);
		this.setXv(rand.nextInt(60)+20);
	}
	
	public void update(double elapsedTime){
		rect.setX(rect.getX()-this.getX()*elapsedTime);
		
	}
	
	public Node getRect(){
		return rect;
	}
	
	public void colision(){
		
	}

		 
	
}
