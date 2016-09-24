package layout;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
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

	private static final String XML_FILES_LOCATION = "data/xml/";
	private static final String XML_SUFFIX = ".xml";
	private String myFileName;

	private static final int MILLISECOND_DELAY = 10000 / FRAMES_PER_SECOND;
	private static final double SECOND_DELAY = 10.0 / FRAMES_PER_SECOND;
	private Map<String, RuleXMLFactory> ruleMap = new HashMap<String, RuleXMLFactory>();

	private Group root;
	private Rule rule;
//	private int myLength = 600;
//	private int myWidth = 600;
//	private int myRowNum = 100;
//	private int myColNum = 100;

	public void init(Stage s) {

		ruleMap.put("FireRule", new FireRuleXMLFactory());
		ruleMap.put("LifeRule", new LifeRuleXMLFactory());
		ruleMap.put("WatorRule", new WatorRuleXMLFactory());
		ruleMap.put("SchellingRule", new SchellingRuleXMLFactory());
		

		getParsedObject(myFileName);
		
		
		s.setTitle("It works!");

		// set length, width, sizex, sizey according to the XML decision.

		// how to consider user input
		
		// rule = new LifeRule(myLength, myWidth, myRowNum, myColNum);

		root = new Group();
		// determine how to take XML instructions for initial states into each
		// square: Rule.initState()


		rule.initGrid();
		drawGrid();
		
		Scene scene = new Scene(root);
		s.setScene(scene);
		s.show();

		KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY));
		Timeline animation = new Timeline();
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.getKeyFrames().add(frame);
		animation.play();
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
				System.err.println("Reading file " + f.getPath());
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

}
