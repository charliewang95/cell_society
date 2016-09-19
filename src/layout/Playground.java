package layout;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Playground {
	
	public static final int FRAMES_PER_SECOND = 60;
	private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
	private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
	
	Group root;
	
	public void init(Stage s) {
		
		//READ XML FILE
		//get file and author name, global config parameters, dimensions of grid and initial states of the cell
		//http://stackoverflow.com/questions/428073/what-is-the-best-simplest-way-to-read-in-an-xml-file-in-java-application
		//http://stackoverflow.com/questions/7704827/java-reading-xml-file
		
		//s.setTitle("sksks")
		//
		
		//Rule sim = new Rule();
		
//		Scene scene = new Scene(sim.init());
//		s.setScene(scene);
//		s.show();
		
		KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY));
		Timeline animation = new Timeline();
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.getKeyFrames().add(frame);
		animation.play();
		
		
	}
	
	
	
	public void step(double elapsedTime) {
		/* 
		 * for each step of the way:
		 * 
		 * 
		 */
	}

}
