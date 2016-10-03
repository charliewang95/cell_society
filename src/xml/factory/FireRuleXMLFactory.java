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
        int side = parseXMLInteger(root, "Side");
		
		double probCatch = parseXMLDouble(root, "ProbCatch");
		Color emptyColor = parseXMLColor(root, "EmptyColor");
		Color treeColor = parseXMLColor(root, "TreeColor");
		Color burnColor = parseXMLColor(root, "BurnColor");
		
		boolean initialize = parseXMLBoolean(root, "Initialize");
		
		String title = parseXMLString(root, "Title");
		
		FireRule myFire = new FireRule(cellLength, row, column, emptyColor, treeColor, burnColor, probCatch, neighbor, side, toro);
		myFire.setName(XML_TAG_NAME);
		
		if (initialize) {
			myFire = (FireRule) initSpecific(myFire, root, row, column, neighbor, side, new Color[]{emptyColor, treeColor, burnColor}, toro, 1);
		}
		
		return myFire;
	}
	
	
	
	
}