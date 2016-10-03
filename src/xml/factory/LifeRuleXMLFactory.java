package xml.factory;

import org.w3c.dom.Element;

import javafx.scene.paint.Color;
import layout.Rule;
import layout.rule.LifeRule;

/**
 * Creates LifeRule object from an XML file.
 * 
 * @author cellsociety_team14
 */
public class LifeRuleXMLFactory extends RuleXMLFactory {
	private static final String XML_TAG_NAME = "LifeRule";

	/**
	 * Factory for LifeRule
	 */
	public LifeRuleXMLFactory() {
		super(XML_TAG_NAME);
	}

	/**
	 * @return LifeRule object
	 */
	@Override
	public Rule getRule(Element root) throws XMLFactoryException {
		checkRule(root, XML_TAG_NAME);

		double cellLength = parseXMLDouble(root, "CellLength", "10");
        int row = parseXMLInteger(root, "Row", "50");
        int column = parseXMLInteger(root, "Column", "50");
        boolean toro = parseXMLBoolean(root, "Toroidal", "false");
        int neighbor = parseXMLInteger(root, "Neighbor", "8");
        int side = parseXMLInteger(root, "Side", "4");

		Color emptyColor = parseXMLColor(root, "EmptyColor", "LIGHTGREY");
		Color liveColor = parseXMLColor(root, "LiveColor", "BLACK");
		
		boolean initialize = parseXMLBoolean(root, "Initialize", "false");
		
		String typeLife = parseXMLString(root, "LifeType", "10Cell");
		
		if (typeLife.equals("Gosper") && (row < 30 || column < 50)) {
			throw new XMLFactoryException("GOSPER SIZE");
		}
		
		LifeRule myLife = new LifeRule(cellLength, row, column, neighbor, side, emptyColor, liveColor, typeLife, toro);
		myLife.setName(XML_TAG_NAME);
		
		
		if (initialize) {
			myLife = (LifeRule) initSpecific(myLife, root, row, column, neighbor, side, new Color[]{emptyColor, liveColor}, toro, 0);
			
		}

		return myLife;

	}
}
