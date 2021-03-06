package layout;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ResourceBundle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Slider;
import javafx.stage.Stage;
import javafx.util.Duration;
import layout.rule.Parameter;
import layout.rule.SugarRule;
import layout.rule.agents.Agent;
import layout.rule.WatorRule;
import layout.rule.watoranimals.Animal;
import user_interface.UIObjectPlacer;
import xml.XMLParser;
import xml.XMLParserException;
import xml.XMLWriter;
import xml.factory.FireRuleXMLFactory;
import xml.factory.LifeRuleXMLFactory;
import xml.factory.RuleXMLFactory;
import xml.factory.SchellingRuleXMLFactory;
import xml.factory.SugarRuleXMLFactory;
import xml.factory.WatorRuleXMLFactory;
import xml.factory.XMLFactoryException;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Class that shows the simulation and takes the input from the start screen and
 * sends it to be parsed Is called in StartScreen Can be called using the init
 * method (e.g., playground.init(stage))
 * 
 * @author Noah Over
 *
 */
public class Playground {
	private static final int CHART_HEIGHT = 100;
	private static final int CHART_Y_OFFSET = 135;
	private static final int CHART_SPACE = 140;
	private static final int SIZE = 600;
	private static final int FRAMES_PER_SECOND = 60;
	private static final String DEFAULT_RESOURCE_PACKAGE = "resources/";
	private static final String XML_RESOURCE_PACKAGE = "xml.properties/";
	private static final String RULE_PROPERTY = "Rule";
	private static final String XML_FILES_LOCATION = "data/xml/";
	private static final int BUTTON_SPACE = 190;
	private static final int PAUSE_Y = 0;
	private static final int Y_OFFSET = 30;
	private static final int STEP_Y = 2 * Y_OFFSET;
	private static final int RESET_Y = 3 * Y_OFFSET;
	private static final int OUTPUT_XML_Y = 4 * Y_OFFSET;
	private static final int SLIDER_Y = 5 * Y_OFFSET;
	private static final int TEXTFIELD_Y = 6 * Y_OFFSET;
	private static final int CUSTOM_SLIDER_Y = 11 * Y_OFFSET;
	private static final int SUGAR_BUTTON_Y = 14 * Y_OFFSET;
	private static final int X_OFFSET = BUTTON_SPACE - 10;
	private static final double MAX_SLIDER = 10;
	private static final double MIN_SLIDER = 0.1;
	private static final double INITIAL_VALUE = 1;
	private static final int MILLISECOND_DELAY = 10000 / FRAMES_PER_SECOND;
	private static final double SECOND_DELAY = 10.0 / FRAMES_PER_SECOND;
	private static final int FONT_SIZE = 15;

	private Map<String, RuleXMLFactory> myFactoryMap = new HashMap<String, RuleXMLFactory>();
	private Group myRoot;
	private Rule myRule;
	private Timeline myAnimation;
	private String myFileName;
	private ResourceBundle myResources;
	private ResourceBundle myXMLResources;
	private Scene myScene;
	private Slider mySlider;
	private Stage myStage;
	private UIObjectPlacer myPlacer;
	private Parameter[] myParameters;
	private Slider[] myCustomSliders;
	private LineChart<Number, Number> myLineChart;
	private ArrayList<XYChart.Series<Number, Number>> mySeries;
	private double mySliderValue = INITIAL_VALUE;
	private int mySteps;
	private File myFile;
	private double myWidth;
	private double myLength;
	private boolean mySugarButtonBool;

	public Playground(Stage s, String fileName) throws XMLFactoryException {
		myStage = s;
		setFileName(fileName);
	}

	public Playground(Stage s, File file) throws XMLFactoryException {
		myStage = s;
		myFile = file;
		setFileName(myFile.getName());
	}

	public void init() throws XMLFactoryException, XMLParserException {
		myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "English");
		myXMLResources = ResourceBundle.getBundle(XML_RESOURCE_PACKAGE + RULE_PROPERTY);
		setUpFactoryMap();
		myRoot = new Group();
		myPlacer = new UIObjectPlacer(myRoot, myResources);
		try {
			getRuleFromFile(myFileName);
		} catch (XMLFactoryException e) {
			throw e;
		}
		myRule.initGrid();
		drawGrid();
		NumberAxis xAxis = new NumberAxis();
		NumberAxis yAxis = new NumberAxis();
		myLineChart = new LineChart<Number, Number>(xAxis, yAxis);
		setWidthAndLength();
		myScene = new Scene(myRoot, myWidth, myLength);
		addLineChart();
		setUpButtons();
		mySlider = myPlacer.addSlider(myScene.getWidth() - X_OFFSET, SLIDER_Y, MIN_SLIDER, MAX_SLIDER, mySliderValue,
				myResources.getString("SpeedSlider"));
		setUpNewSimulationControls();
		addCustomSliders();
		myScene.setOnMouseClicked(e -> handleMouseInput(e.getX(), e.getY()));
		myStage.setScene(myScene);
		myStage.setTitle(myRule.getName());
		myStage.show();
		runSimulation();
	}

	public String getFileName() {
		return myFileName;
	}

	public void setFileName(String file) {
		myFileName = file;
	}

	public Timeline getAnimation(){
		return myAnimation;
	}
	
	public Slider getSlider(){
		return mySlider;
	}
	
	public void setSliderValue(double value){
		mySliderValue = value;
	}

	private void setWidthAndLength() {
		if (myRule.getWidth() + BUTTON_SPACE < SIZE)
			myWidth = SIZE;
		else
			myWidth = myRule.getWidth() + BUTTON_SPACE;
		if (myRule.getCounters().length > 0) {
			if (myRule.getLength() + CHART_SPACE < SIZE)
				myLength = SIZE;
			else
				myLength = myRule.getLength() + CHART_SPACE;
		} else {
			if (myRule.getLength() < SIZE - CHART_SPACE)
				myLength = SIZE - CHART_SPACE;
			else
				myLength = myRule.getLength();
		}
	}

	private void setUpFactoryMap() {
		myFactoryMap.put("FireRule", new FireRuleXMLFactory());
		myFactoryMap.put("LifeRule", new LifeRuleXMLFactory());
		myFactoryMap.put("WatorRule", new WatorRuleXMLFactory());
		myFactoryMap.put("SchellingRule", new SchellingRuleXMLFactory());
		myFactoryMap.put("SugarRule", new SugarRuleXMLFactory());
	}

	private void setUpButtons() {
		myPlacer.addButton(myScene.getWidth() - X_OFFSET, PAUSE_Y, myResources.getString("PauseButton"),
				new EventHandler<ActionEvent>() {
					public void handle(ActionEvent event) {
						myAnimation.pause();
					}
				});
		myPlacer.addButton(myScene.getWidth() - X_OFFSET, Y_OFFSET, myResources.getString("ResumeButton"),
				new EventHandler<ActionEvent>() {
					public void handle(ActionEvent event) {
						myAnimation.play();
					}
				});
		myPlacer.addButton(myScene.getWidth() - X_OFFSET, STEP_Y, myResources.getString("TakeStepButton"),
				new EventHandler<ActionEvent>() {
					public void handle(ActionEvent event) {
						step(0);
					}
				});
		myPlacer.addButton(myScene.getWidth() - X_OFFSET, RESET_Y, myResources.getString("ResetButton"),
				new EventHandler<ActionEvent>() {
					public void handle(ActionEvent event) {
						try {
							reset();
						} catch (XMLFactoryException | FileNotFoundException e) {
							myPlacer.showError(e.getMessage());
						}
					}
				});
		if (myRule instanceof SugarRule){
			mySugarButtonBool = false;
			myPlacer.addButton(myScene.getWidth() - X_OFFSET, SUGAR_BUTTON_Y, myResources.getString("SugarButton"), 
					new EventHandler<ActionEvent>(){
				public void handle(ActionEvent event){
					if (mySugarButtonBool)
						mySugarButtonBool = false;
					else
						mySugarButtonBool = true;
				}
			});
		}
		if (!(myRule instanceof SugarRule) && !(myRule instanceof WatorRule)){
			myPlacer.addButton(myScene.getWidth() - X_OFFSET, OUTPUT_XML_Y, myResources.getString("XMLOutputButton"),
					new EventHandler<ActionEvent>() {
						public void handle(ActionEvent event) {
							try {
								XMLWriter printer = new XMLWriter(myRule);
								printer.saveXML();
								Alert alert = new Alert(AlertType.CONFIRMATION);
								alert.setTitle(myResources.getString("XMLPrintedTitle"));
								alert.setContentText(myResources.getString("XMLPrintedMessage"));
								alert.showAndWait();
							} catch (Exception e) {
								throw e;
							}
						}
					});
		}
	}

	private void setUpNewSimulationControls() {
		myPlacer.addText(myScene.getWidth() - X_OFFSET, TEXTFIELD_Y, FONT_SIZE, myResources.getString("SameWindow"),
				false);
		myPlacer.addNewSimulationTextField(myScene.getWidth() - X_OFFSET, TEXTFIELD_Y + 0.5 * Y_OFFSET, myStage, this);
		myPlacer.addBrowseButton(myScene.getWidth() - X_OFFSET, TEXTFIELD_Y + 1.5 * Y_OFFSET, myStage, this);
		myPlacer.addText(myScene.getWidth() - X_OFFSET, TEXTFIELD_Y + 2.5 * Y_OFFSET, FONT_SIZE,
				myResources.getString("NewWindow"), false);
		myPlacer.addNewSimulationTextField(myScene.getWidth() - X_OFFSET, TEXTFIELD_Y + 3 * Y_OFFSET, new Stage(),
				null);
		myPlacer.addBrowseButton(myScene.getWidth() - X_OFFSET, TEXTFIELD_Y + 4 * Y_OFFSET, new Stage(), null);
	}

	private void runSimulation() {
		KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY));
		myAnimation = new Timeline();
		myAnimation.setCycleCount(Timeline.INDEFINITE);
		myAnimation.getKeyFrames().add(frame);
		mySteps = 0;
	}

	private void getRuleFromFile(String fileName) throws XMLFactoryException, XMLParserException {
		try {
			File f;
			if (myFile == null) {
				if (fileName.length() < 4) {
					fileName = fileName + ".xml";
				} else if (!(fileName.substring(fileName.length() - 4)).equals(".xml")) {
					fileName = fileName + ".xml";
				}
				f = new File(XML_FILES_LOCATION + fileName);
			} else {
				f = myFile;
			}
			XMLParser parser = new XMLParser();
			Element fileRoot = parser.getRootElement(f.getAbsolutePath());
			NodeList nodeList = fileRoot.getElementsByTagName(myXMLResources.getString("RuleName"));
			String chosenRule = nodeList.item(0).getTextContent();
			if (!myFactoryMap.containsKey(chosenRule)) {
				throw new XMLFactoryException(fileName + " " + myResources.getString("XMLError")+ " '%s'",
						chosenRule);
			}
			RuleXMLFactory factory = myFactoryMap.get(chosenRule);
			myRule = factory.getRule(fileRoot);
		} catch (XMLParserException e) {
			throw new XMLParserException(e, myResources.getString("ParseError")+" %s", fileName);
		}
	}

	private void drawGrid() {
		for (int i = 0; i < myRule.myRow; i++) {
			for (int j = 0; j < myRule.myColumn; j++) {
				myRoot.getChildren().add(myRule.getGrid()[i][j].getShape());
			}
		}
		if (myRule instanceof SugarRule) {
			for (Agent a : ((SugarRule) myRule).getAgent()) {
				myRoot.getChildren().add(a.getCircle());
			}
		}
	}

	private void step(double elapsedTime) {
		mySteps++;
		myAnimation.setRate(mySlider.getValue());
		for (int i = 0; i < myParameters.length; i++) {
			myParameters[i].setValue(myCustomSliders[i].getValue());
		}
		myRule.changeState();
		for (int j = 0; j < mySeries.size(); j++) {
			mySeries.get(j).getData().add(new XYChart.Data<Number, Number>(mySteps, myRule.getCounters()[j]));
		}
	}

	private void reset() throws XMLFactoryException, FileNotFoundException {
		myAnimation.stop();
		mySliderValue = mySlider.getValue();
		try {
			init();
		} catch (XMLFactoryException e) {
			myPlacer.showError(myResources.getString("ReadingFileError") + myFileName);
		}
	}

	private void handleMouseInput(double x, double y) {
		Cell[][] grid = myRule.getGrid();
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				if (grid[i][j].getShape().contains(x, y)) {
					if (!mySugarButtonBool){
						int newState = handleNormalMouseInput(grid, i, j);
						if (myRule instanceof WatorRule){
							handleWatorMouseInput(grid, i, j, newState);
						}
					}
					else {
						handleSugarMouseInput(i, j);
					}
				}
			}
		}
	}

	private void handleSugarMouseInput(int i, int j) {
		boolean agentChecker = false;
		for (Agent agent: ((SugarRule) myRule).getAgent()){
			if (agent.getRow() == i && agent.getCol() == j){
				agentChecker = true;
				((SugarRule) myRule).removeAgent(agent);
				break;
			}
		}
		if (!agentChecker){
			Random r = new Random();
			Agent newAgent = ((SugarRule) myRule).createAgent(r, i, j);
			myRoot.getChildren().add(newAgent.getCircle());
		}
	}

	private void handleWatorMouseInput(Cell[][] grid, int i, int j, int newState) {
		((WatorRule) myRule).getWatorUpdatedGrid()[i][j].setTempState(newState);
		if (newState == 1){
			((WatorRule) myRule).getWatorUpdatedGrid()[i][j]
					.setTempReproduce((int) myCustomSliders[0].getValue());
		}
		else if (newState == 2){
			((Animal) grid[i][j]).setReproduce((int) myCustomSliders[2].getValue());
			((Animal) grid[i][j]).setHealth((int) myCustomSliders[1].getValue());
		}
	}

	private int handleNormalMouseInput(Cell[][] grid, int i, int j) {
		if (grid[i][j].getState() != 0 && 
			myRule.getCounters().length > 0 &&
			!(myRule instanceof SugarRule)) {
			myRule.getCounters()[grid[i][j].getState() - 1]--;
		}
		int newState = grid[i][j].getState() + 1;
		if (newState >= myRule.getColors().length) {
			newState = 0;
		} else if (myRule.getCounters().length > 0 &&
				!(myRule instanceof SugarRule)) {
			myRule.getCounters()[newState - 1]++;
		}
		grid[i][j].setState(newState, myRule.getColors()[newState]);
		myRule.getUpdatedGrid()[i][j] = newState;
		return newState;
	}

	private void addCustomSliders() {
		List<Parameter> parameters = myRule.getParameters();
		myParameters = new Parameter[parameters.size()];
		myCustomSliders = new Slider[parameters.size()];
		int i = 0;
		for (Parameter parameter : parameters) {
			myParameters[i] = parameter;
			Slider slider = myPlacer.addSlider(myScene.getWidth() - X_OFFSET, CUSTOM_SLIDER_Y + i * Y_OFFSET,
					parameter.getMin(), parameter.getMax(), parameter.getValue(), parameter.getMessage());
			myCustomSliders[i] = slider;
			i++;
		}
	}

	private void addLineChart() {
		mySeries = new ArrayList<XYChart.Series<Number, Number>>();
		if (myRule.getCounters().length == 0) {
			return;
		}
		myLineChart.setMaxHeight(CHART_HEIGHT);
		int i = 0;
		for (int counter : myRule.getCounters()) {
			XYChart.Series<Number, Number> series = new XYChart.Series<Number, Number>();
			series.getData().add(new XYChart.Data<Number, Number>(0, counter));
			series.setName(myRule.getLegend()[i]);
			mySeries.add(series);
			myLineChart.getData().add(series);
			i++;
		}
		myLineChart.relocate(0, myScene.getHeight() - CHART_Y_OFFSET);
		myLineChart.setLegendSide(Side.RIGHT);
		myLineChart.setLegendVisible(true);
		myRoot.getChildren().add(myLineChart);
	}

}
