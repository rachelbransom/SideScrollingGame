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
	
	public void update(double elapsedTime){
		player.setLayoutY(this.getY()*elapsedTime*200 + player.getLayoutY());
		if(this.getY() != 0){
			double newV = this.getY() > 0 ? this.getY()-1 : this.getY()+1;
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