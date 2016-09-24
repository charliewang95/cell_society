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
import javafx.scene.control.Slider;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
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
	private static final int BUTTON_SPACE = 150;
	private static final int PAUSE_Y = 0;
	private static final int Y_OFFSET = 30;
	private static final int STEP_Y = 2*Y_OFFSET;
	private static final int SLIDER_Y = 3*Y_OFFSET;
	private static final int X_OFFSET = BUTTON_SPACE - 10;
	private static final double MAX_SLIDER = 10;
	private static final double MIN_SLIDER = 0.1;
	private static final double INITIAL_VALUE = 1;


	private static final int MILLISECOND_DELAY = 10000 / FRAMES_PER_SECOND;
	private static final double SECOND_DELAY = 10.0 / FRAMES_PER_SECOND;
	private Map<String, RuleXMLFactory> ruleMap = new HashMap<String, RuleXMLFactory>();

	private Group root;
	private Rule rule;
	private Timeline myAnimation;
	private String myFileName;
	private ResourceBundle myResources;
	private Scene myScene;
	private Slider mySlider;
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

		// how to consider user input
		
		//rule = new LifeRule(myLength, myWidth, myRowNum, myColNum);

		root = new Group();
		// determine how to take XML instructions for initial states into each
		// square: Rule.initState()


		rule.initGrid();
		drawGrid();
		
		myScene = new Scene(root, rule.myWidth + BUTTON_SPACE, rule.myLength);
		addButton(myScene.getWidth() - X_OFFSET, PAUSE_Y, myResources.getString("PauseButton"), 
				  new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				pause();
			}
		});
		addButton(myScene.getWidth() - X_OFFSET, Y_OFFSET, myResources.getString("ResumeButton"), 
				  new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				resume();
			}
		});
		addButton(myScene.getWidth() - X_OFFSET, STEP_Y, myResources.getString("TakeStepButton"), 
				  new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				rule.changeState();
			}
		});
		mySlider = addSlider(myScene.getWidth() - X_OFFSET, SLIDER_Y, MIN_SLIDER, MAX_SLIDER, INITIAL_VALUE, 
							 myResources.getString("Slider"));
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
		myAnimation.setRate(mySlider.getValue());
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
	
	private Slider addSlider(double x, double y, double min, double max, double value, String message){
		Slider slider = new Slider(min, max, value);
		slider.relocate(x, y);
		root.getChildren().add(slider);
		Text text = new Text(x + 50, y + Y_OFFSET, message);
		text.setFont(new Font(15));
		root.getChildren().add(text);
		return slider;
	}

}
