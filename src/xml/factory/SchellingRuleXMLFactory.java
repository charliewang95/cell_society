package xml.factory;

import org.w3c.dom.Element;

import javafx.scene.paint.Color;
import layout.Cell;
import layout.Rule;
import layout.rule.SchellingRule;

/**
 * Creates SchellingRule object from an XML file.
 *
 * @author cellsociety_team14
 */
public class SchellingRuleXMLFactory extends RuleXMLFactory {
	private static final String XML_TAG_NAME = "SchellingRule";
	
	/**
	 * Factory for SchellingRule
	 */
	public SchellingRuleXMLFactory() {
		super(XML_TAG_NAME);
	}

	/**
	 * @return SchellingRule object
	 */
	@Override
	public Rule getRule(Element root) throws XMLFactoryException {
		checkRule(root, XML_TAG_NAME);

		double cellLength = parseXMLDouble(root, "CellLength", "10");
        Integer row = parseXMLInteger(root, "Row", "50");
        Integer column = parseXMLInteger(root, "Column", "30");
        boolean toro = parseXMLBoolean(root, "Toroidal", "true");
        int neighbor = parseXMLInteger(root, "Neighbor", "3");
        int side = parseXMLInteger(root, "Side", "6");
		
		double percentageA = parseXMLDouble(root, "PercentageA", "0.55"); 
		double percentageEmpty = parseXMLDouble(root, "PercentageEmpty", "0.3"); 
		double satisfy = parseXMLDouble(root, "Satisfy", "0.7"); 
		Color emptyColor = parseXMLColor(root, "EmptyColor", "WHITE");
		Color aaaColor = parseXMLColor(root, "AaaColor", "RED");
		Color bbbColor = parseXMLColor(root, "BbbColor", "BLUE");

		boolean initialize = parseXMLBoolean(root, "Initialize", "false");

	
		SchellingRule mySchelling = new SchellingRule(cellLength, row, column, neighbor, side, percentageA, percentageEmpty, satisfy, emptyColor, aaaColor, bbbColor, toro);
		mySchelling.setName(XML_TAG_NAME);

		if (initialize) {
			mySchelling = (SchellingRule) initSpecific(mySchelling, root, row, column, neighbor, side, new Color[]{emptyColor, aaaColor, bbbColor}, toro, 0);
			
		}
		
		
		return mySchelling;

	}
}
