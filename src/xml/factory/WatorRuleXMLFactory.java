package xml.factory;

import java.util.ResourceBundle;

import org.w3c.dom.Element;

import javafx.scene.paint.Color;
import layout.Rule;
import layout.rule.WatorRule;


/**
 * Creates FireRule object from an XML file.
 * 
 * @author cellsociety_team14
 */
public class WatorRuleXMLFactory extends RuleXMLFactory {
    private static final String XML_TAG_NAME = "WatorRule";
    public static final String DEFAULT_RESOURCE_PACKAGE = "xml.properties/";
    private static final String RULE_PROPERTY = "Rule";
    private ResourceBundle myXMLResources;

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
        myXMLResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + getRuleProperty());
        if (!getTextValue(root, myXMLResources.getString("RuleName")).equals("WatorRule")) {
            throw new XMLFactoryException("XML file does not represent the %s", getRuleType());
        }
        //Integer length = Integer.parseInt(getTextValue(root, myResources.getString("Length")));
        double cellLength = Double.parseDouble(getTextValue(root, myXMLResources.getString("CellLength")));
        Integer row = Integer.parseInt(getTextValue(root, myXMLResources.getString("Row")));
        Integer column = Integer.parseInt(getTextValue(root, myXMLResources.getString("Column")));
        
        double pWater = Double.parseDouble(getTextValue(root, myXMLResources.getString("PercentWater")));
        double pFish = Double.parseDouble(getTextValue(root, myXMLResources.getString("PercentFish")));
        Integer fishRepro = Integer.parseInt(getTextValue(root, myXMLResources.getString("FishReproduce")));
        Integer sharkRepro = Integer.parseInt(getTextValue(root, myXMLResources.getString("SharkReproduce")));
        Integer sharkDeath = Integer.parseInt(getTextValue(root, myXMLResources.getString("SharkDeath")));
       
		int neighbor = Integer.parseInt(getTextValue(root, myXMLResources.getString("Neighbor")));
        Color waterColor = Color.valueOf(getTextValue(root, myXMLResources.getString("EmptyColor")));
		Color fishColor = Color.valueOf(getTextValue(root, myXMLResources.getString("FishColor")));
		Color sharkColor = Color.valueOf(getTextValue(root, myXMLResources.getString("SharkColor")));
        
        
        String name = getTextValue(root, myXMLResources.getString("Title"));
        
        WatorRule myWator = new WatorRule(cellLength, row, column, neighbor, waterColor, fishColor, sharkColor, fishRepro, sharkRepro, sharkDeath, pWater, pFish);
        myWator.setName(name);
        
        return myWator;
    
    }
}
