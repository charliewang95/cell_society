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
	private static final int TITLE_Y = 0;
	private static final int SUBTITLE_Y = 60;
	private static final int TITLE_SIZE = 50;
	private static final int TEXTFIELD_X_OFFSET = 70;
	private static final int TEXTFIELD_Y_OFFSET = 30;
	
	
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
		addText(scene.getWidth()/2, TITLE_Y, TITLE_SIZE, TITLE);
		addText(scene.getWidth()/2, SUBTITLE_Y, TITLE_SIZE/2, SUBTITLE);
		addTextField(TEXT_FIELD_1, scene.getWidth()/2, scene.getHeight()/2);
		addTextField(TEXT_FIELD_2, scene.getWidth()/2, scene.getHeight()/2 + TEXTFIELD_Y_OFFSET);
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
		textField.relocate(x - TEXTFIELD_X_OFFSET, y);
		myRoot.getChildren().add(textField);
	}

}
