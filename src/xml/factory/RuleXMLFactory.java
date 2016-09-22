package xml.factory;

import layout.Rule;

import org.w3c.dom.Element;
import java.util.Objects;


/**
 * An XMLFactory that gives back a Rule object.
 *
 * @author Rhondu Smithwick
 * @author Robert Duvall
 */
public abstract class RuleXMLFactory extends XMLFactory {
    private String myRuleType;


    /**
     * Create a factory for making Rule objects.  
     */
    protected RuleXMLFactory (String ruleType) {
        myRuleType = ruleType;
    }

    /**
     * @return the type of rule this file represents
     */
    public String getRuleType () {
        return myRuleType;
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
