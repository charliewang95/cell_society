package layout;


import java.io.File;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import layout.rule.FireRule;
import xml.XMLParser;
import xml.factory.FireRuleXMLFactory;
import xml.factory.XMLFactoryException;

public class Playground {
	
	public static final int FRAMES_PER_SECOND = 60;
	private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
	private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
	private static final String XML_FILES_LOCATION = "data/xml/";
    private static final String XML_SUFFIX = ".xml";
    private String myFileName;
	
	Group root;
	Rule sim;
	int LENGTH = 0;
	int WIDTH = 0;
	int SIZEX = 0;
	int SIZEY = 0;
	
	public void init(Stage s, String file) {
		
		//READ XML FILE
		//get file and author name, global config parameters, dimensions of grid and initial states of the cell
		//http://stackoverflow.com/questions/428073/what-is-the-best-simplest-way-to-read-in-an-xml-file-in-java-application
		//http://stackoverflow.com/questions/7704827/java-reading-xml-file
		
		getParsedObject(myFileName);
		
		
		
		
		
		//s.setTitle("sksks")
		
		//set length, width, sizex, sizey according to the XML decision. 
		
		//how to consider user input
		
		
		//determine how to take XML instructions for initial states into each square: Rule.initState()
		
//		Scene scene = new Scene(sim.init());
//		s.setScene(scene);
//		s.show();
		
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
		FireRuleXMLFactory factory = new FireRuleXMLFactory();
        File f = new File(XML_FILES_LOCATION+fileName);
        Rule ruleInXML;
        
        if (f.isFile() && f.getName().endsWith(XML_SUFFIX)) {
            try {
                ruleInXML = factory.getRule(parser.getRootElement(f.getAbsolutePath()));
                sim = ruleInXML;
            }
            catch (XMLFactoryException e) {
                System.err.println("Reading file " + f.getPath());
                e.printStackTrace();
                //REDO EXCEPTION so that it'll just give pop up window, enter a valid file. 
            }
        }
        
        
	}
	
	
	
	public void step(double elapsedTime) {
		/* 
		 * for each step of the way:
		 * 		for each square, update it
		 * 		
		 * 
		 */
		
		sim.changeState();
	}

}
