package layout;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import layout.rule.FireRule;

public class Playground {

	public static final int FRAMES_PER_SECOND = 60;
	private static final int MILLISECOND_DELAY = 10000 / FRAMES_PER_SECOND;
	private static final double SECOND_DELAY = 10.0 / FRAMES_PER_SECOND;

	private Group root;
	private Rule rule;
	private int myLength = 800;
	private int myWidth = 800;
	private int myRowNum = 100;
	private int myColNum = 100;

	public void init(Stage s) {

		// READ XML FILE
		// get file and author name, global config parameters, dimensions of
		// grid and initial states of the cell
		// http://stackoverflow.com/questions/428073/what-is-the-best-simplest-way-to-read-in-an-xml-file-in-java-application
		// http://stackoverflow.com/questions/7704827/java-reading-xml-file

		s.setTitle("sksks");

		// set length, width, sizex, sizey according to the XML decision.

		// how to consider user input
		rule = new FireRule(myLength, myWidth, myRowNum, myColNum);
		root = new Group();
		// determine how to take XML instructions for initial states into each
		// square: Rule.initState()

		rule.initGrid();
		
		showGrid();
		Scene scene = new Scene(root);
		s.setScene(scene);
		s.show();

		KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY));
		Timeline animation = new Timeline();
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.getKeyFrames().add(frame);
		animation.play();
	}

	public void showGrid() {
		for (int i = 0; i < myRowNum; i++) {
			for (int j = 0; j < myColNum; j++) {
				root.getChildren().add(rule.getGrid()[i][j].getRec());
			}
		}
	}

	public void step(double elapsedTime) {
		/*
		 * for each step of the way: for each square, update it
		 * 
		 * 
		 */

		rule.changeState();
		showGrid();
	}

}
