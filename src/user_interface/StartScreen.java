package user_interface;

import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import layout.Playground;


public class StartScreen {
	private static final int SIZE = 400;
	private static final Paint BACKGROUND_COLOR = Color.WHITE;
	private static final Paint FONT_COLOR = Color.BLACK;
	private static final int TITLE_Y = 0;
	private static final int SUBTITLE_Y = 60;
	private static final int TITLE_SIZE = 50;
	private static final int TEXTFIELD_X_OFFSET = 70;
	private static final int TEXTFIELD_Y_OFFSET = 30;
	private static final String DEFAULT_RESOURCE_PACKAGE = "resources/";
	
	
	private Group myRoot;
	private Stage myStage;
	private Playground myPlayground;
	private ResourceBundle myResources;
	private List<String> ruleList = Arrays.asList("FireRule", "LifeRule", "SchellingRule", "WatorRule");
	
	
	public StartScreen(Stage s){
		myStage = s;
		myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "English");
		myStage.setTitle(getTitle());
		Scene scene = init();
		myStage.setScene(scene);
		myStage.show();
		myPlayground = new Playground();
	}
	
	private String getTitle(){
		return myResources.getString("Title");
	}
	
	private Scene init(){
		myRoot = new Group();
		Scene scene = new Scene(myRoot, SIZE, SIZE, BACKGROUND_COLOR);
		addText(scene.getWidth()/2, TITLE_Y, TITLE_SIZE, myResources.getString("Title"));
		addText(scene.getWidth()/2, SUBTITLE_Y, TITLE_SIZE/2, myResources.getString("Subtitle"));
		TextField textField = addTextField(myResources.getString("TextFieldText"), scene.getWidth()/2, scene.getHeight()/2 + TEXTFIELD_Y_OFFSET);
		textField.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				String inText = textField.getCharacters().toString();
				if (ruleList.contains(inText)) {
					myPlayground.setFileName(inText);
					myPlayground.init(myStage);
				} else {
					showError(myResources.getString("CouldNotLoadError") + inText);
				}
				
			}
		});
		return scene;
	}
	
	private void addText(double x, double y, int fontSize, String message){
		Text text = new Text(message);
		text.setFont(new Font(myResources.getString("Font"), fontSize));
		text.setX(x - (text.getBoundsInLocal().getWidth()/2));
		text.setY(y + text.getBoundsInLocal().getHeight());
		text.setFill(FONT_COLOR);
		myRoot.getChildren().add(text);
	}
	
	private  TextField addTextField(String message, double x, double y){
		TextField textField = new TextField(message);
		textField.relocate(x - TEXTFIELD_X_OFFSET, y);
		myRoot.getChildren().add(textField);
		return textField;
	}
	
	public void showError (String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(myResources.getString("ErrorTitle"));
        alert.setContentText(message);
        alert.showAndWait();
    }

}
