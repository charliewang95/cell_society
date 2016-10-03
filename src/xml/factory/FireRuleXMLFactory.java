package xml.factory;

import org.w3c.dom.Element;

import javafx.scene.paint.Color;
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

		Double cellLength = parseXMLDouble(root, "CellLength", "20");
        Integer row = parseXMLInteger(root, "Row", "20");
        Integer column = parseXMLInteger(root, "Column", "20");
        boolean toro = parseXMLBoolean(root, "Toroidal", "false");
        int neighbor = parseXMLInteger(root, "Neighbor", "4");
        int side = parseXMLInteger(root, "Side", "4");
		
		double probCatch = parseXMLDouble(root, "ProbCatch", "0.5");
		Color emptyColor = parseXMLColor(root, "EmptyColor", "YELLOW");
		Color treeColor = parseXMLColor(root, "TreeColor", "GREEN");
		Color burnColor = parseXMLColor(root, "BurnColor", "RED");
		
		boolean initialize = parseXMLBoolean(root, "Initialize", "false");
		
		
		FireRule myFire = new FireRule(cellLength, row, column, emptyColor, treeColor, burnColor, probCatch, neighbor, side, toro);
		myFire.setName(XML_TAG_NAME);
		
		if (initialize) {
			myFire = (FireRule) initSpecific(myFire, root, row, column, neighbor, side, new Color[]{emptyColor, treeColor, burnColor}, toro, 1);
		}
		
		return myFire;
	}
	
	
}