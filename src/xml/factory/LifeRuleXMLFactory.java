package xml.factory;

import java.util.ResourceBundle;

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
	public static final String DEFAULT_RESOURCE_PACKAGE = "xml.properties/";
	private static final String RULE_PROPERTY = "Rule";
	private ResourceBundle myXMLResources;

	/**
	 * Factory for LifeRule
	 */
	public LifeRuleXMLFactory() {
		super(XML_TAG_NAME, RULE_PROPERTY);
	}

	/**
	 * @return LifeRule object
	 */
	@Override
	public Rule getRule(Element root) throws XMLFactoryException {
		myXMLResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + getRuleProperty());
		if (!getTextValue(root, myXMLResources.getString("RuleName")).equals("LifeRule")) {
			throw new XMLFactoryException("XML file does not represent the %s", getRuleType());
		}
		//Integer length = Integer.parseInt(getTextValue(root, myXMLResources.getString("Length")));
		double cellLength = Double.parseDouble(getTextValue(root, myXMLResources.getString("CellLength")));
		Integer row = Integer.parseInt(getTextValue(root, myXMLResources.getString("Row")));
		Integer column = Integer.parseInt(getTextValue(root, myXMLResources.getString("Column")));
		
		Color emptyColor = Color.valueOf(getTextValue(root, myXMLResources.getString("EmptyColor")));
		Color liveColor = Color.valueOf(getTextValue(root, myXMLResources.getString("LiveColor")));

		int neighbor = Integer.parseInt(getTextValue(root, myXMLResources.getString("Neighbor")));
		String typeLife = getTextValue(root, myXMLResources.getString("LifeType"));
		if (typeLife.equals("Gosper") && (row < 30 || column < 50)) {
			throw new XMLFactoryException("GOSPER SIZE");
		}
		String name = getTextValue(root, myXMLResources.getString("Title"));

		LifeRule myLife = new LifeRule(cellLength, row, column, neighbor, emptyColor, liveColor, typeLife);
		myLife.setName(name);

		return myLife;

	}
}
