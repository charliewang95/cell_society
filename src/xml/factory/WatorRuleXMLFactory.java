package xml.factory;

import java.util.ResourceBundle;

import org.w3c.dom.Element;

import layout.Rule;
import layout.rule.WatorRule;


/**
 * Creates FireRule object from an XML file.
 * 
 * @author cellsociety_team14
 */
public class WatorRuleXMLFactory extends RuleXMLFactory {
    private static final String XML_TAG_NAME = "FireRule";
    public static final String DEFAULT_RESOURCE_PACKAGE = "xml.properties/";
    private static final String RULE_PROPERTY = "Rule";
    private ResourceBundle myResources;

    /**
     * Factory for WatorRule
     */
    public WatorRuleXMLFactory () {
        super(XML_TAG_NAME, RULE_PROPERTY);
    }

    /**
     * @return WatorRule object
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
        
        double pWater = Double.parseDouble(getTextValue(root, myResources.getString("PercentWater")));
        double pFish = Double.parseDouble(getTextValue(root, myResources.getString("PercentFish")));
        Integer fishRepro = Integer.parseInt(getTextValue(root, myResources.getString("FishReproduce")));
        Integer sharkRepro = Integer.parseInt(getTextValue(root, myResources.getString("SharkReproduce")));
        Integer sharkDeath = Integer.parseInt(getTextValue(root, myResources.getString("SharkDeath")));
        String name = getTextValue(root, myResources.getString("Title"));
        
        WatorRule myWator = new WatorRule(length, width, row, column);
        myWator.setFishReproduce(fishRepro);
        myWator.setSharkReproduce(sharkRepro);
        myWator.setSharkDeath(sharkDeath);
        myWator.setPercentageWater(pWater);
        myWator.setPercentageFish(pFish);
        myWator.setName(name);
        
        return myWator;
    
    }
}
