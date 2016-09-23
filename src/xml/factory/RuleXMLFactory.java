package xml.factory;

import layout.Rule;

import org.w3c.dom.Element;
import java.util.Objects;
import java.util.ResourceBundle;


/**
 * An XMLFactory that gives back a Rule object.
 *
 * @author Rhondu Smithwick
 * @author Robert Duvall
 */
public abstract class RuleXMLFactory extends XMLFactory {
    private String myRuleType;
    private String myRuleProperty;

    
    /**
     * Create a factory for making Rule objects.  
     */
    protected RuleXMLFactory (String ruleType, String ruleProperty) {
        myRuleType = ruleType;
        myRuleProperty = ruleProperty;
    }

    /**
     * @return the type of rule this file represents
     */
    public String getRuleType () {
        return myRuleType;
    }
    
    /**
     * @return the property file used for creating Rule object
     */
    public String getRuleProperty() {
    	return myRuleProperty;
    }

    /**
     * Get the actual rule contained in this XML File.
     */
    public abstract Rule getRule (Element root) throws XMLFactoryException;

    /**
     * @see XMLFactory#isValidFile()
     */
    @Override
    protected boolean isValidFile (Element root) {
        return Objects.equals(getAttribute(root, "RuleType"), getRuleType());
    }
}
