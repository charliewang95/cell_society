package xml.factory;

import java.util.ResourceBundle;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javafx.scene.paint.Color;
import layout.Cell;
import layout.Rule;
import layout.rule.FireRule;

/**
 * Creates FireRule object from an XML file.
 *
 * @author cellsociety_team14
 */
public class FireRuleXMLFactory extends RuleXMLFactory {
	private static final String XML_TAG_NAME = "FireRule";
	public static final String XML_RESOURCE_PACKAGE = "xml.properties/";
	private static final String RULE_PROPERTY = "Rule";
	private ResourceBundle myResources = ResourceBundle.getBundle(XML_RESOURCE_PACKAGE + getRuleProperty());
	public FireRule myFire;
	// should this be public?

	/**
	 * Factory for FireRule
	 */
	public FireRuleXMLFactory() {
		super(XML_TAG_NAME, RULE_PROPERTY);
	}

	/**
	 * @return FireRule object
	 */
	@Override
	public Rule getRule(Element root) throws XMLFactoryException {

		if (!getTextValue(root, myResources.getString("RuleName")).equals("FireRule")) {
			throw new XMLFactoryException("XML file does not represent the %s", getRuleType());
		}

		cellLength = Integer.parseInt(getTextValue(root, myResources.getString("Length")));
		//Integer width = Integer.parseInt(getTextValue(root, myResources.getString("Width")));
		myRow = Integer.parseInt(getTextValue(root, myResources.getString("Row")));
		myColumn = Integer.parseInt(getTextValue(root, myResources.getString("Column")));
		double probCatch = Double.parseDouble(getTextValue(root, myResources.getString("ProbCatch")));
		String title = getTextValue(root, myResources.getString("Title"));

		myFire = new FireRule(cellLength, cellLength, myRow, myColumn, );
		myFire.setProbCatch(probCatch);
		myFire.setName(title);
		initGrid();
		//if true that there is specific states set in XML file
			//use initSpecifiedState(root)
		initState();
		return myFire;

	}

	private void initFactoryGrid(Element root, int length, int width, int row, int column) {
		

	}
	
	private void initFactoryState(Element root) {
		
	}

	@Override
	public void initGrid() {
		Cell[][] theGrid = new Cell[myRow][myColumn];
		int[][] theUpdatedGrid = new int[myRow][myColumn];
		//int cellWidth = width / column;
		//int cellLength = length / row;
		for (int i = 0; i < myRow; i++) {
			for (int j = 0; j < myColumn; j++) {
				double x = (cellLength) * j;
				double y = (cellLength) * i;
				theGrid[i][j] = new Cell(x, y, cellLength, cellLength, i, j);
			}
		}
		
		initState(theGrid, theUpdatedGrid);
		initNeighbor4();
		
		
	}
	
	public void initSpecifiedState(Element root) {
		NodeList x = root.getElementsByTagName("init").item(0).getChildNodes();
		for (int i = 0; i < x.getLength(); i++) {
			NodeList y = x.item(i).getChildNodes();
			for (int j = 0; j < y.getLength(); j++) {
				grid[i][j] = y.item(j).getTextContent(); 
				//do safe equiv, located in XMLFactory class getTextValue
			}
		}
	}

	@Override
	public void initState(Cell[][] theGrid, int[][]theUpdatedGrid) {
		for (int i = 0; i < myRow; i++) {
			for (int j = 0; j < myColumn; j++) {
				if (i == 0 || i == myRow - 1 || j == 0 || j == myColumn - 1) {
					theGrid[i][j].init(EMPTY, myColors[EMPTY], NUMNEIGHBOR);
					theUpdatedGrid[i][j] = EMPTY;
				} else if (i == myRow / 2 && j == myColumn / 2) {
					theGrid[i][j].init(BURN, myColors[BURN], NUMNEIGHBOR);
					theUpdatedGrid[i][j] = BURN;
				} else {
					theGrid[i][j].init(TREE, myColors[TREE], NUMNEIGHBOR);
					myUpdatedGrid[i][j] = TREE;
				}
			}
		}
		myFire.setGrid(theGrid);
		
		
	}

}
