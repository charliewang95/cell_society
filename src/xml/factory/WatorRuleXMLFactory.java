package xml.factory;

import java.util.ResourceBundle;

import org.w3c.dom.Element;

import layout.Rule;
import layout.rule.WatorRule;


/**
 * Creates FireRule object from an XML file.
 *
 * @author Rhondu Smithwick
 * @author Robert Duvall
 */
public class WatorRuleXMLFactory extends RuleXMLFactory {
    private static final String XML_TAG_NAME = "FireRule";
    public static final String DEFAULT_RESOURCE_PACKAGE = "xml.properties/";
    private static final String RULE_PROPERTY = "Rule";
    private ResourceBundle myResources;

    /**
     * Create factory capable of generating Professor objects.
     */
    public WatorRuleXMLFactory () {
        super(XML_TAG_NAME, RULE_PROPERTY);
    }

    /**
     * @see PersonXMLFactory#getPerson()
     */
    @Override
    public Rule getRule (Element root) throws XMLFactoryException {
        myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + getRuleProperty());
        if (!getTextValue(root, myResources.getString("RuleName")).equals("WatorRule")) {
            throw new XMLFactoryException("XML file does not represent the %s", getRuleType());
        }
        Integer length = Integer.parseInt(getTextValue(root, myResources.getString("Length")));
        Integer width = Integer.parseInt(getTextValue(root, myResources.getString("Width")));
        Integer row = Integer.parseInt(getTextValue(root, myResources.getString("Row")));
        Integer column = Integer.parseInt(getTextValue(root, myResources.getString("Column")));

        return new WatorRule(length, width, row, column);

    //probcatch parameter???, length width row column
        
   //schelling: percentage of group a and group b
    
    }
}