import javafx.animation.Animation.Status;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class PlayersFish extends Sprite {
	private Rectangle player;
	private static Boolean jump;
	private TranslateTransition translation;

	public PlayersFish() {
		player = new Rectangle((Main.sizeOfScreen() / 2) - 25, (Main.sizeOfScreen() / 2) - 25, 50, 50);
		player.setFill(Color.RED);
		translation = new TranslateTransition(Duration.millis(500), player);
		translation.interpolatorProperty().set(Interpolator.SPLINE(.1, .1, .7, .7));
		translation.setByY(-100);
		translation.setAutoReverse(true);
		translation.setCycleCount(2);
	}

	public Node getPlayer() {
		return player;
	}
	
	public void setLevelTwoStartingPosition(){
		player.setLayoutY(124);
	}

	public Double getPlayerYPos() {
		return player.getLayoutY();
	}
	
	public Double getPlayerXPos() {
		return player.getLayoutX();
	}
	
	public void jump(){
		if(!jump)
			translation.play();
		jump = true;
	}

	public void update(double elapsedTime) {
		player.setLayoutY(this.getYv() * elapsedTime * 200 + player.getLayoutY());
		if (this.getYv() != 0) {
			double newV = this.getYv() > 0 ? this.getYv() - 1 : this.getYv() + 1;
			this.setYv(newV);
		}
		player.setLayoutX(this.getXv() * elapsedTime * 200 + player.getLayoutX());
		if (this.getXv() != 0) {
			double newV = this.getXv() > 0 ? this.getXv() - 1 : this.getXv() + 1;
			this.setXv(newV);	
		}
		if(translation.getStatus()!=Status.RUNNING){
			jump=false;
		}	
	}
	
}