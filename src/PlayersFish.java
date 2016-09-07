import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class PlayersFish extends Sprite{
	private Rectangle player;
	
	public PlayersFish(){
		player = new Rectangle((650/2)-25,(650/2)-25, 50, 50);	
		player.setFill(Color.RED);
	}
	
	public Node getPlayer(){
		return player;
	}
	
	public Double getPlayerYPos(){
		return player.getLayoutY();
	}
	
	public void update(double elapsedTime){
		player.setLayoutY(this.getYv()*elapsedTime*200 + player.getLayoutY());
		if(this.getYv() != 0){
			double newV = this.getYv() > 0 ? this.getYv()-1 : this.getYv()+1;
			this.setYv(newV);
		}
	}


	
	
	//what does it look like?
	// set javafx node to circle
	//updating velocity of fish
	// collision boolean
	//handle collision -  turn fish red and end world
}


// updating velocity
// nodes
// initialize in enemy and update in sprite?