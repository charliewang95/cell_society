package xml.factory;

import java.util.Arrays;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

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

	/**
	 * Factory for FireRule
	 */
	public FireRuleXMLFactory() {
		super(XML_TAG_NAME);
	}
	/**
	 * @return FireRule object
	 */
	@Override
	public Rule getRule(Element root) throws XMLFactoryException {
		checkRule(root, XML_TAG_NAME);

		double cellLength = parseXMLDouble(root, "CellLength");
        Integer row = parseXMLInteger(root, "Row");
        Integer column = parseXMLInteger(root, "Column");
        boolean toro = parseXMLBoolean(root, "Toroidal");
        int neighbor = parseXMLInteger(root, "Neighbor");
		
		double probCatch = parseXMLDouble(root, "ProbCatch");
		Color emptyColor = parseXMLColor(root, "EmptyColor");
		Color treeColor = parseXMLColor(root, "TreeColor");
		Color burnColor = parseXMLColor(root, "BurnColor");
		
		boolean initialize = parseXMLBoolean(root, "Initialize");
		
		String title = parseXMLString(root, "Title");
		
		FireRule myFire = new FireRule(cellLength, row, column, emptyColor, treeColor, burnColor, probCatch, neighbor, toro);
		myFire.setName(title);
		
		if (initialize) {
			Cell[][] temp = new Cell[row][column];
			myFire.setGrid(temp);
			//need to have a grid already created in order to init the board. aghrielagjra
			
			myFire.initBoard(neighbor);
			
			Cell[][] temp2 = myFire.getGrid();
			int[][] tempUpdated = buildSpecific(root, row, column);
			Color[] stateColor = {emptyColor, treeColor, burnColor};
			for (int i = 0; i < row; i++) {
				for (int j=0; j < column; j++) {
					int current = tempUpdated[i][j];
					temp2[i][j].init(current, stateColor[current]);
				}
				
			}
			myFire.setUpdatedGrid(tempUpdated);
			myFire.initNeighbor(neighbor, toro);
			
		}
		
		return myFire;
	}
	
	private int[][] buildSpecific(Element root, int row, int column) {
		NodeList rowStates = root.getElementsByTagName(myXMLResources.getString("RowState"));
		int[][] locations = new int[row][column];
		
		for (int[] toFill : locations) {
			Arrays.fill(toFill, 1);
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
	
	
}