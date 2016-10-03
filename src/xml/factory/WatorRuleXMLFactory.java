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

        double cellLength = parseXMLDouble(root, "CellLength", "10");
        int row = parseXMLInteger(root, "Row", "30");
        int column = parseXMLInteger(root, "Column", "20");
        boolean toro = parseXMLBoolean(root, "Toroidal", "true");
        int neighbor = parseXMLInteger(root, "Neighbor", "3");
        int side = parseXMLInteger(root, "Side", "3");
        
        double pWater = parseXMLDouble(root, "PercentWater", "0.9"); 
        double pFish = parseXMLDouble(root, "PercentFish", "0.9"); 
        int fishRepro = parseXMLInteger(root, "FishReproduce", "8");
        int sharkRepro = parseXMLInteger(root, "SharkReproduce", "12");
        int sharkDeath = parseXMLInteger(root, "SharkDeath","10");
       
        Color waterColor = parseXMLColor(root, "EmptyColor", "LIGHTBLUE");
		Color fishColor = parseXMLColor(root, "FishColor", "GREEN");
		Color sharkColor = parseXMLColor(root, "SharkColor", "ORANGE");
        
        
        WatorRule myWator = new WatorRule(cellLength, row, column, neighbor, side, waterColor, fishColor, sharkColor, fishRepro, sharkRepro, sharkDeath, pWater, pFish, toro);
        myWator.setName(XML_TAG_NAME);

        return myWator;
    
    }
}
