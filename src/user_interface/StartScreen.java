package user_interface;

import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import layout.Playground;


public class StartScreen {
	private static final int SIZE = 400;
	private static final Paint BACKGROUND_COLOR = Color.WHITE;
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
	private UIObjectPlacer myPlacer;
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
		myPlacer = new UIObjectPlacer(myRoot, myResources);
		Scene scene = new Scene(myRoot, SIZE, SIZE, BACKGROUND_COLOR);
		myPlacer.addText(scene.getWidth()/2, TITLE_Y, TITLE_SIZE, myResources.getString("Title"));
		myPlacer.addText(scene.getWidth()/2, SUBTITLE_Y, TITLE_SIZE/2, myResources.getString("Subtitle"));
		TextField textField = myPlacer.addTextField(myResources.getString("TextFieldText"), 
													scene.getWidth()/2 - TEXTFIELD_X_OFFSET, 
													scene.getHeight()/2 + TEXTFIELD_Y_OFFSET);
		textField.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				String inText = textField.getCharacters().toString();
				if (ruleList.contains(inText)) {
					myPlayground.setFileName(inText);
					myPlayground.init(myStage);
				} else {
					myPlacer.showError(myResources.getString("CouldNotLoadError") + inText);
				}
				
			}
		});
		return scene;
	}
}
