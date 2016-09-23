package cellsociety_team14;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Duration;
import layout.Playground;
import user_interface.StartScreen;

public class SocietyMain extends Application {

	public static final int FRAMES_PER_SECOND = 60;
	private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
	private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
	
	Playground playGround;
	
	

	@Override
	public void start(Stage primaryStage) throws Exception {
		new StartScreen(primaryStage);
//		playGround = new Playground();
//		playGround.init(primaryStage);
		
		

	}

	public static void main(String[] args) {
		launch(args);
	}

}
