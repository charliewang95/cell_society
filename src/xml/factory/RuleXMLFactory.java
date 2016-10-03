package xml.factory;

import layout.Cell;
import layout.Rule;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * An XMLFactory that gives back a Rule object.
 *
 * @author Joy Kim
 */
public abstract class RuleXMLFactory extends XMLFactory {
	public static final String XML_RESOURCE_PACKAGE = "xml.properties/Rule";

	private String myRuleType;
	protected ResourceBundle myXMLResources;

	// protected double cellLength;
	// protected int myRow;
	// protected int myColumn;
	// protected Cell[][] myGrid;

	/**
	 * Create a factory for making Rule objects.
	 */
	protected RuleXMLFactory(String ruleType) {
		myXMLResources = ResourceBundle.getBundle(XML_RESOURCE_PACKAGE);
		myRuleType = ruleType;
	}

	public double parseXMLDouble(Element root, String tag) {
		return Double.parseDouble(getTextValue(root, myXMLResources.getString(tag)));

	}

	public int parseXMLInteger(Element root, String tag) {
		return Integer.parseInt(getTextValue(root, myXMLResources.getString(tag)));
	}

	public boolean parseXMLBoolean(Element root, String tag) {
		return Boolean.parseBoolean(getTextValue(root, myXMLResources.getString(tag)));
	}

	public Color parseXMLColor(Element root, String tag) {
		return Color.valueOf(getTextValue(root, myXMLResources.getString(tag)).toUpperCase());
	}

	public String parseXMLString(Element root, String tag) {
		return getTextValue(root, myXMLResources.getString(tag));
	}

	public void checkRule(Element root, String ruleTag) throws XMLFactoryException {
		if (!parseXMLString(root, "RuleName").equals(ruleTag)) {
			throw new XMLFactoryException("XML file does not represent the %s", getRuleType());
		}
	}
	
	protected Rule initSpecific(Rule rule, Element root, int row, int column, int neighbor, Color[] color, boolean toro, int defaultState) {
		Cell[][] temp = new Cell[row][column];
		rule.setGrid(temp);
		//need to have a grid already created in order to init the board. aghrielagjra
		
		rule.initBoard(neighbor);
		
		Cell[][] temp2 = rule.getGrid();
		int[][] tempUpdated = buildSpecific(root, row, column, defaultState);
		Color[] stateColor = color;
		for (int i = 0; i < row; i++) {
			for (int j=0; j < column; j++) {
				int current = tempUpdated[i][j];
				temp2[i][j].init(current, stateColor[current]);
				//if (stateNum != 0)
				//	myFire.getCounters()[stateNum-1]--;
				//myCounters[1]++;
				//need to update myCounters array somehow. 
				
			}
			
		}
		rule.setUpdatedGrid(tempUpdated);
		rule.initNeighbor(neighbor, toro);
		return rule;
	}

	protected int[][] buildSpecific(Element root, int row, int column, int defaultState) {
		NodeList rowStates = root.getElementsByTagName(myXMLResources.getString("RowState"));
		int[][] locations = new int[row][column];
		
		for (int[] toFill : locations) {
			Arrays.fill(toFill, defaultState);
		}

		for (int i=0; i < rowStates.getLength(); i++) {
			Element rowNode = (Element) rowStates.item(i);
			int rowIndex = Integer.parseInt(rowNode.getElementsByTagName(myXMLResources.getString("Index")).item(0).getTextContent());
			NodeList columnStates = rowNode.getElementsByTagName(myXMLResources.getString("ColumnState"));
			for (int j=0; j<columnStates.getLength();j++) {
				Element columnNode = (Element) columnStates.item(j);
				int columnIndex = Integer.parseInt(columnNode.getElementsByTagName(myXMLResources.getString("Index")).item(0).getTextContent());
				int state = Integer.parseInt(columnNode.getElementsByTagName(myXMLResources.getString("State")).item(0).getTextContent());
				locations[rowIndex][columnIndex] = state;
			}
		}
		return locations;
		
	}
	
	
	/**
	 * @return the type of rule this file represents
	 */
	public String getRuleType() {
		return myRuleType;
	}


	/**
	 * Get the actual rule contained in this XML File.
	 */
	public abstract Rule getRule(Element root) throws XMLFactoryException;

	/**
	 * @see XMLFactory#isValidFile()
	 */
	@Override
	protected boolean isValidFile(Element root) {
		return Objects.equals(getAttribute(root, "RuleType"), getRuleType());
	}
}
