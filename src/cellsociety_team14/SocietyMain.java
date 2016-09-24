package cellsociety_team14;

import javafx.application.Application;
import javafx.stage.Stage;
import user_interface.StartScreen;

public class SocietyMain extends Application {
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		new StartScreen(primaryStage);
	}

	public static void main(String[] args) {
		launch(args);
	}

}
