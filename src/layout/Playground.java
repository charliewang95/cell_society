package layout;

import java.io.File;
import java.util.ResourceBundle;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import layout.rule.FireRule;
import layout.rule.LifeRule;
import layout.rule.SchellingRule;
import user_interface.UIObjectPlacer;
import xml.XMLParser;
import xml.factory.FireRuleXMLFactory;
import xml.factory.LifeRuleXMLFactory;
import xml.factory.RuleXMLFactory;
import xml.factory.SchellingRuleXMLFactory;
import xml.factory.WatorRuleXMLFactory;
import xml.factory.XMLFactory;
import xml.factory.XMLFactoryException;

/**
 * Class that shows the simulation and takes the input from the start screen and sends it to be parsed
 * Is called in StartScreen
 * Can be called using the init method (e.g., playground.init(stage))
 * 
 * @author Noah Over
 *
 */
public class Playground {
	private static final int FRAMES_PER_SECOND = 60;
	private static final String DEFAULT_RESOURCE_PACKAGE = "resources/";
	private static final String XML_FILES_LOCATION = "data/xml/";
	private static final String XML_SUFFIX = ".xml";
	private static final int BUTTON_SPACE = 165;
	private static final int PAUSE_Y = 0;
	private static final int Y_OFFSET = 30;
	private static final int STEP_Y = 2*Y_OFFSET;
	private static final int RESET_Y = 3*Y_OFFSET;
	private static final int SLIDER_Y = 4*Y_OFFSET;
	private static final int TEXTFIELD_Y = 5*Y_OFFSET;
	private static final int X_OFFSET = BUTTON_SPACE - 10;
	private static final double MAX_SLIDER = 10;
	private static final double MIN_SLIDER = 0.1;
	private static final double INITIAL_VALUE = 1;
	private static final int MILLISECOND_DELAY = 10000 / FRAMES_PER_SECOND;
	private static final double SECOND_DELAY = 10.0 / FRAMES_PER_SECOND;
	private static final int FONT_SIZE = 15;
	
	private Map<String, RuleXMLFactory> myRuleMap = new HashMap<String, RuleXMLFactory>();
	private Group myRoot;
	private Rule myRule;
	private Timeline myAnimation;
	private String myFileName;
	private ResourceBundle myResources;
	private Scene myScene;
	private Slider mySlider;
	private Stage myStage;
	private UIObjectPlacer myPlacer;
	private double mySliderValue = INITIAL_VALUE;
	private List<String> myRuleList = Arrays.asList("FireRule", "LifeRule", "SchellingRule", "WatorRule");

	public void init(Stage s) {
		myStage = s;
		myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "English");
		setUpRuleMap();
		getParsedObject(myFileName);
		myRoot = new Group();
		myPlacer = new UIObjectPlacer(myRoot, myResources);
		myRule.initGrid();
		drawGrid();
		myScene = new Scene(myRoot, myRule.getWidth() + BUTTON_SPACE, myRule.getLength());
		setUpButtons();
		mySlider = myPlacer.addSlider(myScene.getWidth() - X_OFFSET, SLIDER_Y, MIN_SLIDER, MAX_SLIDER, 
									  mySliderValue, myResources.getString("SpeedSlider"));
		setUpTextFields();
		myScene.setOnMouseClicked(e -> handleMouseInput(e.getX(), e.getY()));
		myStage.setScene(myScene);
		myStage.setTitle(myRule.getName());
		myStage.show();
		runSimulation();
	}

	private void setUpRuleMap() {
		myRuleMap.put("FireRule", new FireRuleXMLFactory());
		myRuleMap.put("LifeRule", new LifeRuleXMLFactory());
		myRuleMap.put("WatorRule", new WatorRuleXMLFactory());
		myRuleMap.put("SchellingRule", new SchellingRuleXMLFactory());
	}

	private void setUpButtons() {
		myPlacer.addButton(myScene.getWidth() - X_OFFSET, PAUSE_Y, myResources.getString("PauseButton"), 
						   new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				pause();
			}
		});
		myPlacer.addButton(myScene.getWidth() - X_OFFSET, Y_OFFSET, myResources.getString("ResumeButton"), 
						   new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				resume();
			}
		});
		myPlacer.addButton(myScene.getWidth() - X_OFFSET, STEP_Y, myResources.getString("TakeStepButton"), 
						   new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				myRule.changeState();
			}
		});
		myPlacer.addButton(myScene.getWidth() - X_OFFSET, RESET_Y, myResources.getString("ResetButton"),
						   new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				reset();
			}
		});
	}

	private void setUpTextFields() {
		myPlacer.addText(myScene.getWidth() - X_OFFSET, TEXTFIELD_Y, FONT_SIZE, 
						 myResources.getString("SameWindow"), false);
		TextField sameWindow = myPlacer.addTextField(myResources.getString("TextFieldText"),
													 myScene.getWidth() - X_OFFSET, TEXTFIELD_Y + 0.5*Y_OFFSET);
		sameWindow.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				String inText = sameWindow.getCharacters().toString();
				if (myRuleList.contains(inText)) {
					setFileName(inText);
					myAnimation.stop();
					mySliderValue = mySlider.getValue();
					init(myStage);
				} else {
					myPlacer.showError(myResources.getString("CouldNotLoadError") + inText);
				}
			}
		});
		myPlacer.addText(myScene.getWidth() - X_OFFSET, TEXTFIELD_Y + 1.5*Y_OFFSET, FONT_SIZE, 
						 myResources.getString("NewWindow"), false);
		TextField newWindow = myPlacer.addTextField(myResources.getString("TextFieldText"), 
													myScene.getWidth() - X_OFFSET, TEXTFIELD_Y + 2*Y_OFFSET);
		newWindow.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				String inText = newWindow.getCharacters().toString();
				if (myRuleList.contains(inText)){
					Playground playground = new Playground();
					playground.setFileName(inText);
					playground.init(new Stage());
				}
				else {
					myPlacer.showError(myResources.getString("CouldNotLoadError") + inText);
				}
			}
		});
	}

	private void runSimulation() {
		KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY));
		myAnimation = new Timeline();
		myAnimation.setCycleCount(Timeline.INDEFINITE);
		myAnimation.getKeyFrames().add(frame);
	}

	public String getFileName() {
		return myFileName;
	}

	public void setFileName(String file) {
		myFileName = file;
	}

	private void getParsedObject(String fileName) {
		XMLParser parser = new XMLParser();
		RuleXMLFactory factory = myRuleMap.get(fileName);
		File f = new File(XML_FILES_LOCATION + myFileName + ".xml");
		Rule ruleInXML;
		if (f.isFile() && f.getName().endsWith(XML_SUFFIX)) {
			try {
				ruleInXML = factory.getRule(parser.getRootElement(f.getAbsolutePath()));
				myRule = ruleInXML;
			} catch (XMLFactoryException e) {
				System.err.println(myResources.getString("ReadingFileError") + f.getPath());
				e.printStackTrace();
			}
		}
	}

	public void drawGrid() {
		for (int i = 0; i < myRule.myRow; i++) {
			for (int j = 0; j < myRule.myColumn; j++) {
				myRoot.getChildren().add(myRule.getGrid()[i][j].getRec());
			}
		}
	}

	public void step(double elapsedTime) {
		myAnimation.setRate(mySlider.getValue());
		myRule.changeState();
	}
	
	private void pause(){
		myAnimation.pause();
	}
	
	private void resume(){
		myAnimation.play();
	}
	
	private void reset(){
		myAnimation.stop();
		mySliderValue = mySlider.getValue();
		init(myStage);
	}
	
	private void handleMouseInput(double x, double y){
		Cell[][] grid = myRule.getGrid();
		for (int i = 0; i < grid.length; i++){
			for (int j = 0; j < grid[0].length; j++){
				if (grid[i][j].getRec().contains(x, y)){
					int newState = grid[i][j].getState() + 1;
					if (newState >= myRule.getColors().length){
						newState = 0;
					}
					grid[i][j].setState(newState);
					grid[i][j].setColor(myRule.getColors()[newState]);
					myRule.getUpdatedGrid()[i][j] = newState;
				}
			}
		}
	}
}
