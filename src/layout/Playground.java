package layout;

import java.io.File;
import java.util.ResourceBundle;
import java.util.ArrayList;
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
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;
import layout.rule.FireRule;
import layout.rule.LifeRule;
import layout.rule.Parameter;
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
	private static final int BUTTON_SPACE = 180;
	private static final int PAUSE_Y = 0;
	private static final int Y_OFFSET = 30;
	private static final int STEP_Y = 2*Y_OFFSET;
	private static final int RESET_Y = 3*Y_OFFSET;
	private static final int SLIDER_Y = 4*Y_OFFSET;
	private static final int TEXTFIELD_Y = 5*Y_OFFSET;
	private static final int CUSTOM_SLIDER_Y = 8*Y_OFFSET;
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
	private Parameter[] myParameters;
	private Slider[] myCustomSliders;
	private LineChart<Number, Number> myLineChart;
	private ArrayList<XYChart.Series<Number, Number>> mySeries;
	private double mySliderValue = INITIAL_VALUE;
	private List<String> myRuleList = Arrays.asList("FireRule", "LifeRule", "SchellingRule", "WatorRule");
	private int mySteps;

	public void init(Stage s) {
		myStage = s;
		myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "English");
		setUpRuleMap();
		getParsedObject(myFileName);
		myRoot = new Group();
		myPlacer = new UIObjectPlacer(myRoot, myResources);
		myRule.initGrid();
		drawGrid();
		addLineChart();
		if (myLineChart != null)
			myScene = new Scene(myRoot, myRule.getWidth()+BUTTON_SPACE, myRule.getLength()+140);
		else
			myScene = new Scene(myRoot, myRule.getWidth()+BUTTON_SPACE, myRule.getLength());
		setUpButtons();
		mySlider = myPlacer.addSlider(myScene.getWidth() - X_OFFSET, SLIDER_Y, MIN_SLIDER, MAX_SLIDER, 
									  mySliderValue, myResources.getString("SpeedSlider"));
		setUpTextFields();
		addCustomSliders();
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
				step(0);
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
		mySteps = 0;
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
		mySteps++;
		myAnimation.setRate(mySlider.getValue());
		for (int i = 0; i < myParameters.length; i++){
			myParameters[i].setValue(myCustomSliders[i].getValue());
		}
		myRule.changeState();
		for (int j = 0; j < mySeries.size(); j++){
			mySeries.get(j).getData().add(new XYChart.Data<Number, Number>(mySteps, myRule.getCounters()[j]));
		}
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
					if (grid[i][j].getState() != 0){
						myRule.getCounters()[grid[i][j].getState() - 1]--;
					}
					int newState = grid[i][j].getState() + 1;
					if (newState >= myRule.getColors().length){
						newState = 0;
					}
					else{
						myRule.getCounters()[newState - 1]++;
					}
					grid[i][j].setState(newState);
					grid[i][j].setColor(myRule.getColors()[newState]);
					myRule.getUpdatedGrid()[i][j] = newState;
				}
			}
		}
	}
	
	private void addCustomSliders(){
		List<Parameter> parameters = myRule.getParameters();
		myParameters = new Parameter[parameters.size()];
		myCustomSliders = new Slider[parameters.size()];
		int i = 0;
		for (Parameter parameter: parameters){
			myParameters[i] = parameter;
			Slider slider = myPlacer.addSlider(myScene.getWidth() - X_OFFSET, CUSTOM_SLIDER_Y + i*Y_OFFSET, 
											   parameter.getMin(), parameter.getMax(), parameter.getValue(), 
											   parameter.getMessage());
			myCustomSliders[i] = slider;
		}
	}
	
	private void addLineChart(){
		if (myRule.getCounters().length == 0){
			return;
		}
		NumberAxis xAxis = new NumberAxis();
		NumberAxis yAxis = new NumberAxis(0,myRule.myRow*myRule.myColumn-(2*myRule.myRow+2*(myRule.myColumn-2)),1);
		myLineChart = new LineChart<Number, Number>(xAxis, yAxis);
		myLineChart.setMaxHeight(100);
		mySeries = new ArrayList<XYChart.Series<Number, Number>>();
		for (int counter: myRule.getCounters()){
			XYChart.Series<Number, Number> series = new XYChart.Series<Number, Number>();
			series.getData().add(new XYChart.Data<Number, Number>(0, counter));
			mySeries.add(series);
			myLineChart.getData().add(series);
		}
		myLineChart.relocate(0, myRule.getLength());
		myRoot.getChildren().add(myLineChart);
	}
}
