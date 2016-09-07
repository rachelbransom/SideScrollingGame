//import Game.Sliding;
//import javafx.animation.KeyFrame;
//import javafx.animation.KeyValue;
//import javafx.animation.Timeline;
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
//import javafx.scene.Group;
//import javafx.scene.Node;
//import javafx.util.Duration;
//
//public class BackgroundScroller {
//    class Sliding extends Group {
//    	final Node[] content;
//    	Timeline anim = new Timeline();
//    	
//    	public Sliding(final Node[] content, final int width){
//    		this.content = content;
//    		this.getChildren().addAll(content);
//    		anim.setCycleCount(Timeline.INDEFINITE);
//            EventHandler<ActionEvent> onFinished = new EventHandler<ActionEvent>() {
//                public void handle(ActionEvent t) {
//                    for(Node n : content) {
//                        n.setTranslateX(n.getTranslateX() - 1.0);
//                        if(n.getLayoutX() + n.getTranslateX() + n.getBoundsInLocal().getWidth() <= 0) {
//                            n.setTranslateX(width - n.getLayoutX());
//                        }
//                    }
//                }
//    	};
//    	KeyValue keyValueX = new KeyValue(Sliding.this.rotateProperty(),0);
//        KeyFrame keyFrame = new KeyFrame(new Duration(35), onFinished , keyValueX); //, keyValueY);
//        anim.getKeyFrames().add(keyFrame);
//        anim.play();
//    }
//    }
//}
