package user_interface;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class UserInterface {
	private static final int SIZE = 400;
	private static final String TITLE = "Cell Society";
	private static final String SUBTITLE = "A Cellular Automata Simulation";
	private static final String FONT = "Times New Roman";
	private static final Paint BACKGROUND_COLOR = Color.WHITE;
	private static final Paint FONT_COLOR = Color.BLACK;
	private static final String TEXT_FIELD_1 = "ENTER SIZE HERE";
	private static final String TEXT_FIELD_2 = "ENTER FILENAME HERE";
	
	private Group myRoot;
	
	public UserInterface(Stage s){
		Scene scene = init();
		s.setTitle(getTitle());
		s.setScene(scene);
		s.show();
	}
	
	private String getTitle(){
		return TITLE;
	}
	
	private Scene init(){
		myRoot = new Group();
		Scene scene = new Scene(myRoot, SIZE, SIZE, BACKGROUND_COLOR);
		addText(scene.getWidth()/2, 0, 50, TITLE);
		addText(scene.getWidth()/2, 60, 25, SUBTITLE);
		addTextField(TEXT_FIELD_1, scene.getWidth()/2 - 70, scene.getHeight()/2);
		addTextField(TEXT_FIELD_2, scene.getWidth()/2 - 70, scene.getHeight()/2 + 30);
		return scene;
	}
	
	private void addText(double x, double y, int fontSize, String message){
		Text text = new Text(message);
		text.setFont(new Font(FONT, fontSize));
		text.setX(x - (text.getBoundsInLocal().getWidth()/2));
		text.setY(y + text.getBoundsInLocal().getHeight());
		text.setFill(FONT_COLOR);
		myRoot.getChildren().add(text);
	}
	
	private void addTextField(String message, double x, double y){
		TextField textField = new TextField(message);
		textField.relocate(x, y);
		myRoot.getChildren().add(textField);
	}

}
