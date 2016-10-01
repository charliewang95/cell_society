package xml.factory;

import java.util.ResourceBundle;

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
	public static final String DEFAULT_RESOURCE_PACKAGE = "xml.properties/";
	private static final String RULE_PROPERTY = "Rule";
	private ResourceBundle myXMLResources;

	/**
	 * Factory for SchellingRule
	 */
	public SchellingRuleXMLFactory() {
		super(XML_TAG_NAME, RULE_PROPERTY);
	}

	/**
	 * @return SchellingRule object
	 */
	@Override
	public Rule getRule(Element root) throws XMLFactoryException {
		myXMLResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + getRuleProperty());
		if (!getTextValue(root, myXMLResources.getString("RuleName")).equals("SchellingRule")) {
			throw new XMLFactoryException("XML file does not represent the %s", getRuleType());
		}

		// Integer length = Integer.parseInt(getTextValue(root,
		// myResources.getString("Length")));
		double cellLength = Double.parseDouble(getTextValue(root, myXMLResources.getString("CellLength")));
		Integer row = Integer.parseInt(getTextValue(root, myXMLResources.getString("Row")));
		Integer column = Integer.parseInt(getTextValue(root, myXMLResources.getString("Column")));
		double percentageA = Double.parseDouble(getTextValue(root, myXMLResources.getString("PercentageA")));
		double percentageEmpty = Double.parseDouble(getTextValue(root, myXMLResources.getString("PercentageEmpty")));
		double satisfy = Double.parseDouble(getTextValue(root, myXMLResources.getString("Satisfy")));
		Color emptyColor = Color.valueOf(getTextValue(root, myXMLResources.getString("EmptyColor")));
		Color aaaColor = Color.valueOf(getTextValue(root, myXMLResources.getString("AaaColor")));
		Color bbbColor = Color.valueOf(getTextValue(root, myXMLResources.getString("BbbColor")));
		
		int neighbor = Integer.parseInt(getTextValue(root, myXMLResources.getString("Neighbor")));

		String name = getTextValue(root, myXMLResources.getString("Title"));

		SchellingRule mySchelling = new SchellingRule(cellLength, row, column, neighbor, percentageA, percentageEmpty, satisfy, emptyColor, aaaColor, bbbColor);
		//mySchelling.setSatisfied(satisfy);
		//mySchelling.setPercentageA(percentageA);
		//mySchelling.setPercentageEmpty(percentageEmpty);
		mySchelling.setName(name);

		return mySchelling;

	}
}
