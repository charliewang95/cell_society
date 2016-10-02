package xml.factory;

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

    /**
     * Factory for WatorRule
     */
    public WatorRuleXMLFactory () {
        super(XML_TAG_NAME);
    }

    /**
     * @return WatorRule object
     */
    @Override
    public Rule getRule (Element root) throws XMLFactoryException {
        checkRule(root, XML_TAG_NAME);

        double cellLength = parseXMLDouble(root, "CellLength");
        int row = parseXMLInteger(root, "Row");
        int column = parseXMLInteger(root, "Column");
        boolean toro = parseXMLBoolean(root, "Toroidal");
        int neighbor = parseXMLInteger(root, "Neighbor");
        
        double pWater = parseXMLDouble(root, "PercentWater"); 
        double pFish = parseXMLDouble(root, "PercentFish"); 
        int fishRepro = parseXMLInteger(root, "FishReproduce");
        int sharkRepro = parseXMLInteger(root, "SharkReproduce");
        int sharkDeath = parseXMLInteger(root, "SharkDeath");
       
        Color waterColor = parseXMLColor(root, "EmptyColor");
		Color fishColor = parseXMLColor(root, "FishColor");
		Color sharkColor = parseXMLColor(root, "SharkColor");
        
        
        String name = parseXMLString(root, "Title");
        
        WatorRule myWator = new WatorRule(cellLength, row, column, neighbor, waterColor, fishColor, sharkColor, fishRepro, sharkRepro, sharkDeath, pWater, pFish, toro);
        myWator.setName(name);
        
        return myWator;
    
    }
}
