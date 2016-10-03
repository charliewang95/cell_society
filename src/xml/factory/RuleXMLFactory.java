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

	public double parseXMLDouble(Element root, String tag, String defaultDouble) {
		String check = getTextValue(root, myXMLResources.getString(tag));
		return Double.parseDouble(getValueOrDefault(check, defaultDouble));

	}

	public Integer parseXMLInteger(Element root, String tag, String defaultInt) {
		String check = getTextValue(root, myXMLResources.getString(tag));
		return Integer.parseInt(getValueOrDefault(check, defaultInt));
	}

	public boolean parseXMLBoolean(Element root, String tag, String defaultBool) {
		String check = getTextValue(root, myXMLResources.getString(tag));
		return Boolean.parseBoolean((getValueOrDefault(check, defaultBool)));
	}

	public Color parseXMLColor(Element root, String tag, String defaultColor) {
		String check = getTextValue(root, myXMLResources.getString(tag)).toUpperCase();
		return Color.valueOf((getValueOrDefault(check, defaultColor)));
	}

	public String parseXMLString(Element root, String tag, String defaultString) {
		String check = getTextValue(root, myXMLResources.getString(tag));
		return getValueOrDefault(check, defaultString);
	}

	public void checkRule(Element root, String ruleTag) throws XMLFactoryException {
		if (!parseXMLString(root, "RuleName", "").equals(ruleTag)) {
			throw new XMLFactoryException("XML file does not represent the %s", getRuleType());
		}
	}
	
	
	 private static boolean isNotNullOrEmpty(String str){
		    return (str != null && !str.isEmpty());
		}
	 
	 public String getValueOrDefault(String value, String defaultValue) {
		    return isNotNullOrEmpty(value) ? value : defaultValue;
		}
	
	protected Rule initSpecific(Rule rule, Element root, int row, int column, int neighbor, int side, Color[] color, boolean toro, int defaultState) throws XMLFactoryException {
		Cell[][] temp = new Cell[row][column];
		rule.setGrid(temp);
		//need to have a grid already created in order to init the board. aghrielagjra
		
		rule.initBoard(side);
		
		Cell[][] temp2 = rule.getGrid();
		int[][] tempUpdated = buildSpecific(root, row, column, defaultState);
		Color[] stateColor = color;
		for (int i = 0; i < row; i++) {
			for (int j=0; j < column; j++) {
				int current = tempUpdated[i][j];
				if (current >= stateColor.length) {
					throw new XMLFactoryException("Invalid state given at row " + i + ", column " + j);
				}
				temp2[i][j].init(current, stateColor[current]);
				if (current != 0 && !rule.getName().equals("SchellingRule"))
					rule.getCounters()[current-1]++;
//				
				//if (stateNum != 0)
				//	myFire.getCounters()[stateNum-1]--;
				//myCounters[1]++;
				//need to update myCounters array somehow. 
				
			}
			
		}
		//System.out.println(rule.getCounters()[1]);
		rule.setUpdatedGrid(tempUpdated);
		rule.initNeighbor(neighbor, toro);
		return rule;
	}

	protected int[][] buildSpecific(Element root, int row, int column, int defaultState) throws XMLFactoryException {
		NodeList rowStates = root.getElementsByTagName(myXMLResources.getString("RowState"));
		int[][] locations = new int[row][column];
		
		for (int[] toFill : locations) {
			Arrays.fill(toFill, defaultState);
		}

		for (int i=0; i < rowStates.getLength(); i++) {
			Element rowNode = (Element) rowStates.item(i);
			int rowIndex = Integer.parseInt(rowNode.getElementsByTagName(myXMLResources.getString("Index")).item(0).getTextContent());
			if (rowIndex >= row) {
				throw new XMLFactoryException("Row index " + rowIndex + " is not valid for the given size of board.");
			}
			NodeList columnStates = rowNode.getElementsByTagName(myXMLResources.getString("ColumnState"));
			for (int j=0; j<columnStates.getLength();j++) {
				Element columnNode = (Element) columnStates.item(j);
				int columnIndex = Integer.parseInt(columnNode.getElementsByTagName(myXMLResources.getString("Index")).item(0).getTextContent());
				if (columnIndex >= column) {
					throw new XMLFactoryException("Column index " + columnIndex + " is not valid for the given size of board.");
				}
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
