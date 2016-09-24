package xml.factory;

import java.util.ResourceBundle;

import org.w3c.dom.Element;

import layout.Rule;
import layout.rule.FireRule;


/**
 * Creates FireRule object from an XML file.
 *
 * @author Rhondu Smithwick
 * @author Robert Duvall
 */
public class FireRuleXMLFactory extends RuleXMLFactory {
    private static final String XML_TAG_NAME = "FireRule";
    public static final String DEFAULT_RESOURCE_PACKAGE = "xml.properties/";
    private static final String RULE_PROPERTY = "Rule";
    private ResourceBundle myResources;

    /**
     * Create factory capable of generating Professor objects.
     */
    public FireRuleXMLFactory () {
        super(XML_TAG_NAME, RULE_PROPERTY);
    }

    /**
     * @see PersonXMLFactory#getPerson()
     */
    @Override
    public Rule getRule (Element root) throws XMLFactoryException {
//        if (! isValidFile(root)) {
//            throw new XMLFactoryException("XML file does not represent the %s", getRuleType());
//        }
        myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + getRuleProperty());
        Integer myLength= Integer.parseInt(getTextValue(root, myResources.getString("Length")));
        Integer myWidth= Integer.parseInt(getTextValue(root, myResources.getString("Width")));
        Integer myRow = Integer.parseInt(getTextValue(root, myResources.getString("Row")));
        Integer myColumn = Integer.parseInt(getTextValue(root, myResources.getString("Column")));
        return new FireRule(myLength, myWidth, myRow, myColumn);

    //probcatch parameter???, length width row column
        
   //schelling: percentage of group a and group b
    
    }
}
