package xml.factory;

import java.util.ResourceBundle;

import org.w3c.dom.Element;

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
    private ResourceBundle myResources;

    /**
     * Factory for SchellingRule
     */
    public SchellingRuleXMLFactory () {
        super(XML_TAG_NAME, RULE_PROPERTY);
    }

    /**
     * @return SchellingRule object
     */
    @Override
    public Rule getRule (Element root) throws XMLFactoryException {
        myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + getRuleProperty());
        if (!getTextValue(root, myResources.getString("RuleName")).equals("SchellingRule")) {
            throw new XMLFactoryException("XML file does not represent the %s", getRuleType());
        }
        Integer length = Integer.parseInt(getTextValue(root, myResources.getString("Length")));
        Integer width = Integer.parseInt(getTextValue(root, myResources.getString("Width")));
        Integer row = Integer.parseInt(getTextValue(root, myResources.getString("Row")));
        Integer column = Integer.parseInt(getTextValue(root, myResources.getString("Column")));
        double percentageA = Double.parseDouble(getTextValue(root, myResources.getString("PercentageA")));
        double percentageEmpty = Double.parseDouble(getTextValue(root, myResources.getString("PercentageEmpty")));
        double satisfy = Double.parseDouble(getTextValue(root, myResources.getString("Satisfy")));

        SchellingRule mySchelling = new SchellingRule(length, width, row, column);
        mySchelling.setSatisfied(satisfy);
        mySchelling.setPercentageA(percentageA);
        mySchelling.setPercentageEmpty(percentageEmpty);
        
        return mySchelling;
    
    }
}
