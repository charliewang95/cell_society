package xml.factory;

import org.w3c.dom.Element;

import javafx.scene.paint.Color;
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

		double cellLength = parseXMLDouble(root, "CellLength");
        Integer row = parseXMLInteger(root, "Row");
        Integer column = parseXMLInteger(root, "Column");
        boolean toro = parseXMLBoolean(root, "Toroidal");
        int neighbor = parseXMLInteger(root, "Neighbor");
        int side = parseXMLInteger(root, "Side");
		
		double percentageA = parseXMLDouble(root, "PercentageA"); 
		double percentageEmpty = parseXMLDouble(root, "PercentageEmpty"); 
		double satisfy = parseXMLDouble(root, "Satisfy"); 
		Color emptyColor = parseXMLColor(root, "EmptyColor");
		Color aaaColor = parseXMLColor(root, "AaaColor");
		Color bbbColor = parseXMLColor(root, "BbbColor");

		boolean initialize = parseXMLBoolean(root, "Initialize");

		SchellingRule mySchelling = new SchellingRule(cellLength, row, column, neighbor, side, percentageA, percentageEmpty, satisfy, emptyColor, aaaColor, bbbColor, toro);
		mySchelling.setName(XML_TAG_NAME);

		if (initialize) {
			mySchelling = (SchellingRule) initSpecific(mySchelling, root, row, column, neighbor, side, new Color[]{emptyColor, aaaColor, bbbColor}, toro, 0);
			
		}
		
		
		return mySchelling;

	}
}
