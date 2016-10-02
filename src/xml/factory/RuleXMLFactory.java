package xml.factory;

import layout.Rule;

import org.w3c.dom.Element;

import javafx.scene.paint.Color;

import java.util.Objects;
import java.util.ResourceBundle;

/**
 * An XMLFactory that gives back a Rule object.
 *
 * @author Joy Kim
 */
public abstract class RuleXMLFactory extends XMLFactory {
	public static final String XML_RESOURCE_PACKAGE = "xml.properties/Rule";

	private String myRuleType;
	protected ResourceBundle myXMLResources;

	// protected double cellLength;
	// protected int myRow;
	// protected int myColumn;
	// protected Cell[][] myGrid;

	/**
	 * Create a factory for making Rule objects.
	 */
	protected RuleXMLFactory(String ruleType) {
		myXMLResources = ResourceBundle.getBundle(XML_RESOURCE_PACKAGE);
		myRuleType = ruleType;
	}

	public double parseXMLDouble(Element root, String tag) {
		return Double.parseDouble(getTextValue(root, myXMLResources.getString(tag)));

	}

	public int parseXMLInteger(Element root, String tag) {
		return Integer.parseInt(getTextValue(root, myXMLResources.getString(tag)));
	}

	public boolean parseXMLBoolean(Element root, String tag) {
		return Boolean.parseBoolean(getTextValue(root, myXMLResources.getString(tag)));
	}

	public Color parseXMLColor(Element root, String tag) {
		return Color.valueOf(getTextValue(root, myXMLResources.getString(tag)).toUpperCase());
	}

	public String parseXMLString(Element root, String tag) {
		return getTextValue(root, myXMLResources.getString(tag));
	}

	public void checkRule(Element root, String ruleTag) throws XMLFactoryException {
		if (!parseXMLString(root, "RuleName").equals(ruleTag)) {
			throw new XMLFactoryException("XML file does not represent the %s", getRuleType());
		}
	}

	/**
	 * @return the type of rule this file represents
	 */
	public String getRuleType() {
		return myRuleType;
	}


	/**
	 * Get the actual rule contained in this XML File.
	 */
	public abstract Rule getRule(Element root) throws XMLFactoryException;

	/**
	 * @see XMLFactory#isValidFile()
	 */
	@Override
	protected boolean isValidFile(Element root) {
		return Objects.equals(getAttribute(root, "RuleType"), getRuleType());
	}
}
