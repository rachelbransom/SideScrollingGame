import java.util.ArrayList;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.util.Duration;


public class LevelOne extends Game{
	private Group levelOneRoot;
	private Scene myLevel1Scene;
	private ImageView levelOneBackground1;
	private ImageView levelOneBackground2;
	private PlayersFish player;
	private int stepCounter;
	private ArrayList<Enemy> enemiesList = new ArrayList<Enemy>();
	private ParallelTransition parallelTransition;
	private boolean gameOver = false;
	
	public void levelOneInit(Group root){
		levelOneRoot = root;
		//myLevel1Scene = new Scene(levelOneRoot, width, height, Color.TRANSPARENT);
		Image image = new Image(getClass().getClassLoader().getResourceAsStream("SeaFloorBackground.png"));
		Image image2 = new Image(getClass().getClassLoader().getResourceAsStream("SeaFloorBackground.png"));
		levelOneBackground1 = new ImageView(image);
		levelOneBackground2 = new ImageView(image2);
		
		levelOneRoot.getChildren().addAll(levelOneBackground1, levelOneBackground2);
		levelOneBackground2.setX(1089);
		makeBackgroundScroll(SIZE);
		player = new PlayersFish();
		levelOneRoot.getChildren().add(player.getPlayer());
		gameAnimationStarter();
		root.getScene().setOnKeyPressed(e -> handleKeyInput(e.getCode()));
		//return myLevel1Scene;
	}
	
	public void step(double elapsedTime, int width, int height) {
		if (gameOver == true) {
			//end game
		}
		stepCounter++;
		if (stepCounter % 50 == 0) {
			Enemy enemy = new Enemy();
			enemiesList.add(enemy);
			levelOneRoot.getChildren().add(enemy.getRect());
		}
			
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
		
	}
	
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
}
