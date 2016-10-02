package user_interface;

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
import xml.XMLParserException;
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
	
	/**
	 * Constructor for StartScreen that initializes the stage, the resource bundle, and the playground, calls init to
	 * create the scene, and shows the scene on the stage
	 * 
	 * @param s - the stage on which the start screen will be displayed
	 */
	public StartScreen(Stage s) throws XMLFactoryException{
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
		myPlacer.addNewSimulationTextField(scene.getWidth()/2-X_OFFSET,scene.getHeight()/2+Y_OFFSET, myStage, null);
//		setUpTextField(scene);
		myPlacer.addText(scene.getWidth()/2 - X_OFFSET, scene.getHeight()/2 + TEXT_Y_OFFSET, TEXT_SIZE, 
						 myResources.getString("Or"), false);
		myPlacer.addBrowseButton(scene.getWidth()/2 - X_OFFSET, scene.getHeight()/2 + BUTTON_Y_OFFSET, myStage, null);
		return scene;
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
