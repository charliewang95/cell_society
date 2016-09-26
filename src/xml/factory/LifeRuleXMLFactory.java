package xml.factory;

import java.util.ResourceBundle;

import org.w3c.dom.Element;

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
	private ResourceBundle myResources;

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
		myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + getRuleProperty());
		if (!getTextValue(root, myResources.getString("RuleName")).equals("LifeRule")) {
			throw new XMLFactoryException("XML file does not represent the %s", getRuleType());
		}
		Integer length = Integer.parseInt(getTextValue(root, myResources.getString("Length")));
		Integer width = Integer.parseInt(getTextValue(root, myResources.getString("Width")));
		Integer row = Integer.parseInt(getTextValue(root, myResources.getString("Row")));
		Integer column = Integer.parseInt(getTextValue(root, myResources.getString("Column")));
		String typeLife = getTextValue(root, myResources.getString("LifeType"));
		String name = getTextValue(root, myResources.getString("Title"));

		LifeRule myLife = new LifeRule(length, width, row, column);
		myLife.setModel(typeLife);
		myLife.setName(name);

		return myLife;

	}
}
