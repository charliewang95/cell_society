package user_interface;

import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import layout.Playground;
import xml.factory.XMLFactoryException;

/**
 * Class that sets up the start screen, reads the input from the user, and passes it on to Playground
 * Is called in SocietyMain
 * It can be called through its constructor (e.g., StartScreen(stage))
 * 
 * @author Noah Over
 *
 */
public class StartScreen {
	private static final int SIZE = 400;
	private static final Paint BACKGROUND_COLOR = Color.WHITE;
	private static final int TITLE_Y = 0;
	private static final int SUBTITLE_Y = 60;
	private static final int TITLE_SIZE = 50;
	private static final int X_OFFSET = 70;
	private static final int Y_OFFSET = 30;
	private static final int TEXT_Y_OFFSET = 2*Y_OFFSET;
	private static final int BUTTON_Y_OFFSET = 3*Y_OFFSET;
	private static final String DEFAULT_RESOURCE_PACKAGE = "resources/";
	private static final int TEXT_SIZE = 20;
	
	
	private Group myRoot;
	private Stage myStage;
	private ResourceBundle myResources;
	private UIObjectPlacer myPlacer;
	private List<String> myRuleList = Arrays.asList("FireRule", "LifeRule", "SchellingRule", "WatorRule");
	
	/**
	 * Constructor for StartScreen that initializes the stage, the resource bundle, and the playground, calls init to
	 * create the scene, and shows the scene on the stage
	 * 
	 * @param s - the stage on which the start screen will be displayed
	 */
	public StartScreen(Stage s){
		myStage = s;
		myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "English");
		myStage.setTitle(getTitle());
		Scene scene = init();
		myStage.setScene(scene);
		myStage.show();
	}
	
	/**
	 * Getter for the title of the simulation
	 * 
	 * @return the title of the simulation
	 */
	public String getTitle(){
		return myResources.getString("Title");
	}
	
	/**
	 * Creates the scene for the start screen, initializes the root and object placer, and adds text and a text 
	 * field to the scene through setUpTitle and setUpTextField
	 * 
	 * @return the scene of the start screen that it just set up
	 */
	private Scene init(){
		myRoot = new Group();
		myPlacer = new UIObjectPlacer(myRoot, myResources);
		Scene scene = new Scene(myRoot, SIZE, SIZE, BACKGROUND_COLOR);
		setUpTitle(scene);
		setUpTextField(scene);
		myPlacer.addText(scene.getWidth()/2 - X_OFFSET, scene.getHeight()/2 + TEXT_Y_OFFSET, TEXT_SIZE, 
						 myResources.getString("Or"), false);
		myPlacer.addButton(scene.getWidth()/2 - X_OFFSET, scene.getHeight()/2 + BUTTON_Y_OFFSET, 
						   myResources.getString("ChooseFile"), new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				
			}
		});
		return scene;
	}
	
	/**
	 * adds the text field and sets up what to do when the user inputs something into it
	 * 
	 * @param scene - the scene that the text field should be added to
	 */
	private void setUpTextField(Scene scene) {
		TextField textField = myPlacer.addTextField(myResources.getString("TextFieldText"), 
													scene.getWidth()/2 - X_OFFSET, 
													scene.getHeight()/2 + Y_OFFSET);
		textField.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				String inText = textField.getCharacters().toString();
				if (myRuleList.contains(inText)) {
					try {
						Playground playground = new Playground(myStage, inText);
						playground.init();
					} catch (XMLFactoryException e){
						myPlacer.showError(myResources.getString("ReadingFileError") + inText);
					}
				} else {
					myPlacer.showError(myResources.getString("CouldNotLoadError") + inText);
				}
				
			}
		});
	}

	/**
	 * Adds the title and subtitle to the scene
	 * 
	 * @param scene - the scene to which the title and subtitle should be added
	 */
	private void setUpTitle(Scene scene) {
		myPlacer.addText(scene.getWidth()/2, TITLE_Y, TITLE_SIZE, myResources.getString("Title"), true);
		myPlacer.addText(scene.getWidth()/2, SUBTITLE_Y, TITLE_SIZE/2, myResources.getString("Subtitle"), true);
	}
}
