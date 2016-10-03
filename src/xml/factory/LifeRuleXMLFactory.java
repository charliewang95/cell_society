package xml.factory;

import org.w3c.dom.Element;

import javafx.scene.paint.Color;
import layout.Cell;
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

		double cellLength = parseXMLDouble(root, "CellLength");
        int row = parseXMLInteger(root, "Row");
        int column = parseXMLInteger(root, "Column");
        boolean toro = parseXMLBoolean(root, "Toroidal");
        int neighbor = parseXMLInteger(root, "Neighbor");

		Color emptyColor = parseXMLColor(root, "EmptyColor");
		Color liveColor = parseXMLColor(root, "LiveColor");
		
		boolean initialize = parseXMLBoolean(root, "Initialize");
		
		String typeLife = parseXMLString(root, "LifeType");
		
		if (typeLife.equals("Gosper") && (row < 30 || column < 50)) {
			throw new XMLFactoryException("GOSPER SIZE");
		}
		
		String name = parseXMLString(root, "Title");
		
		

		LifeRule myLife = new LifeRule(cellLength, row, column, neighbor, emptyColor, liveColor, typeLife, toro);
		myLife.setName(name);
		
		
		if (initialize) {
			myLife = (LifeRule) initSpecific(myLife, root, row, column, neighbor, new Color[]{emptyColor, liveColor}, toro, 0);
			
		}

		return myLife;

	}
}
