package layout;

import java.io.File;
import java.util.ResourceBundle;
import java.util.HashMap;
import java.util.Map;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.util.Duration;
import layout.rule.FireRule;
import layout.rule.LifeRule;
import layout.rule.SchellingRule;
import xml.XMLParser;
import xml.factory.FireRuleXMLFactory;
import xml.factory.LifeRuleXMLFactory;
import xml.factory.RuleXMLFactory;
import xml.factory.SchellingRuleXMLFactory;
import xml.factory.WatorRuleXMLFactory;
import xml.factory.XMLFactory;
import xml.factory.XMLFactoryException;

public class Playground {

	public static final int FRAMES_PER_SECOND = 60;
	private static final String DEFAULT_RESOURCE_PACKAGE = "resources/";
	private static final String XML_FILES_LOCATION = "data/xml/";
	private static final String XML_SUFFIX = ".xml";
	private static final int BUTTON_SPACE = 100;
	private static final int PAUSE_X = 0;
	private static final int RESUME_X = 50;
	private static final int STEP_X = 90;
	private static final int BUTTON_Y_OFFSET = 50;


	private static final int MILLISECOND_DELAY = 10000 / FRAMES_PER_SECOND;
	private static final double SECOND_DELAY = 10.0 / FRAMES_PER_SECOND;
	private Map<String, RuleXMLFactory> ruleMap = new HashMap<String, RuleXMLFactory>();

	private Group root;
	private Rule rule;
	private Timeline myAnimation;
	private String myFileName;
	private ResourceBundle myResources;
	private Scene myScene;
//	private int myLength = 600;
//	private int myWidth = 600;
//	private int myRowNum = 100;
//	private int myColNum = 100;

	public void init(Stage s) {

		// READ XML FILE
		// get file and author name, global config parameters, dimensions of
		// grid and initial states of the cell
		// http://stackoverflow.com/questions/428073/what-is-the-best-simplest-way-to-read-in-an-xml-file-in-java-application
		// http://stackoverflow.com/questions/7704827/java-reading-xml-file
		myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "English");

		ruleMap.put("FireRule", new FireRuleXMLFactory());
		ruleMap.put("LifeRule", new LifeRuleXMLFactory());
		ruleMap.put("WatorRule", new WatorRuleXMLFactory());
		ruleMap.put("SchellingRule", new SchellingRuleXMLFactory());
		
		getParsedObject(myFileName);
		
		s.setTitle("It works!");

		// how to consider user input
		
		//rule = new LifeRule(myLength, myWidth, myRowNum, myColNum);

		root = new Group();
		// determine how to take XML instructions for initial states into each
		// square: Rule.initState()


		rule.initGrid();
		drawGrid();
		
		myScene = new Scene(root, rule.myWidth, rule.myLength + BUTTON_SPACE);
		addButton(PAUSE_X, myScene.getHeight() - BUTTON_Y_OFFSET, myResources.getString("PauseButton"), 
				  new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				pause();
			}
		});
		addButton(RESUME_X, myScene.getHeight() - BUTTON_Y_OFFSET, myResources.getString("ResumeButton"), 
				  new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				resume();
			}
		});
		addButton(STEP_X, myScene.getHeight() - BUTTON_Y_OFFSET, myResources.getString("TakeStepButton"), 
				  new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				rule.changeState();
			}
		});
		s.setScene(myScene);
		s.show();

		KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY));
		myAnimation = new Timeline();
		myAnimation.setCycleCount(Timeline.INDEFINITE);
		myAnimation.getKeyFrames().add(frame);
		myAnimation.play();
	}

	public String getFileName() {
		return myFileName;
	}

	public void setFileName(String file) {
		myFileName = file;
	}

	private void getParsedObject(String fileName) {
		XMLParser parser = new XMLParser();
		
		
		RuleXMLFactory factory = ruleMap.get(fileName);
  
        //System.out.println(XML_FILES_LOCATION + myFileName + ".xml");
		File f = new File(XML_FILES_LOCATION + myFileName + ".xml");
		Rule ruleInXML;
//		System.out.print(000);
		if (f.isFile() && f.getName().endsWith(XML_SUFFIX)) {
			try {
				ruleInXML = factory.getRule(parser.getRootElement(f.getAbsolutePath()));
				rule = ruleInXML;
			} catch (XMLFactoryException e) {
				System.err.println(myResources.getString("ReadingFileError") + f.getPath());
				e.printStackTrace();
				// REDO EXCEPTION so that it'll just give pop up window, enter a
				// valid file.
			}
		}
	}

	public void drawGrid() {
		for (int i = 0; i < rule.myRow; i++) {
			for (int j = 0; j < rule.myColumn; j++) {
				root.getChildren().add(rule.getGrid()[i][j].getRec());
			}
		}
	}

	public void step(double elapsedTime) {
		/*
		 * for each step of the way: for each square, update it
		 */
		rule.changeState();
	}
	
	private void pause(){
		myAnimation.pause();
	}
	
	private void resume(){
		myAnimation.play();
	}
	
	private Button addButton(double x, double y, String message, EventHandler<ActionEvent> handler){
		Button button = new Button(message);
		button.relocate(x, y);
		button.setOnAction(handler);
		root.getChildren().add(button);
		return button;
	}

}
