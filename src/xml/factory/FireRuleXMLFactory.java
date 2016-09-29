package xml.factory;

import java.util.ResourceBundle;

import org.w3c.dom.Element;

import layout.Rule;
import layout.rule.FireRule;

/**
 * Creates FireRule object from an XML file.
 *
 * @author cellsociety_team14
 */
public class FireRuleXMLFactory extends RuleXMLFactory {
	private static final String XML_TAG_NAME = "FireRule";
	public static final String XML_RESOURCE_PACKAGE = "xml.properties/";
	private static final String RULE_PROPERTY = "Rule";
	private ResourceBundle myResources;

	/**
	 * Factory for FireRule
	 */
	public FireRuleXMLFactory() {
		super(XML_TAG_NAME, RULE_PROPERTY);
	}

	/**
	 * @return FireRule object
	 */
	@Override
	public Rule getRule(Element root) throws XMLFactoryException {

		myResources = ResourceBundle.getBundle(XML_RESOURCE_PACKAGE + getRuleProperty());
		if (!getTextValue(root, myResources.getString("RuleName")).equals("FireRule")) {
			throw new XMLFactoryException("XML file does not represent the %s", getRuleType());
		}

		Integer length = Integer.parseInt(getTextValue(root, myResources.getString("Length")));
		Integer cellLength = Integer.parseInt(getTextValue(root, myResources.getString("Width")));
		Integer row = Integer.parseInt(getTextValue(root, myResources.getString("Row")));
		Integer column = Integer.parseInt(getTextValue(root, myResources.getString("Column")));
		double probCatch = Double.parseDouble(getTextValue(root, myResources.getString("ProbCatch")));
		String title = getTextValue(root, myResources.getString("Title"));

		FireRule myFire = new FireRule(cellLength, row, column);
		myFire.setProbCatch(probCatch);
		myFire.setName(title);
		return myFire;

	}
}
