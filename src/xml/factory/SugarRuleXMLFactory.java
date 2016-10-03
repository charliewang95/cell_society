package xml.factory;

import java.util.Arrays;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javafx.scene.paint.Color;
import layout.Cell;
import layout.Rule;
import layout.rule.FireRule;
import layout.rule.SugarRule;

/**
 * Creates SugarRule object from an XML file.
 *
 * @author cellsociety_team14
 */
public class SugarRuleXMLFactory extends RuleXMLFactory {
	private static final String XML_TAG_NAME = "SugarRule";

	/**
	 * Factory for SugarRule
	 */
	public SugarRuleXMLFactory() {
		super(XML_TAG_NAME);
	}
	/**
	 * @return SugarRule object
	 */
	@Override
	public Rule getRule(Element root) throws XMLFactoryException {
		checkRule(root, XML_TAG_NAME);

		double cellLength = parseXMLDouble(root, "CellLength");
        Integer row = parseXMLInteger(root, "Row");
        Integer column = parseXMLInteger(root, "Column");
        boolean toro = parseXMLBoolean(root, "Toroidal");
        int neighbor = parseXMLInteger(root, "Neighbor");
        int side = parseXMLInteger(root, "Side");
		
        double percent0 = parseXMLDouble(root, "Percent0");
        double percent1 = parseXMLDouble(root, "Percent1");
        double percent2 = parseXMLDouble(root, "Percent2");
        double percent3 = parseXMLDouble(root, "Percent3");
        double percentA = parseXMLDouble(root, "PercentA");
        double[] percent = {percent0, percent1, percent2, percent3, percentA};

		Color ZeroColor = parseXMLColor(root, "Color0");
		Color OneColor = parseXMLColor(root, "Color1");
		Color TwoColor = parseXMLColor(root, "Color2");
		Color ThreeColor = parseXMLColor(root, "Color3");
		Color FourColor = parseXMLColor(root, "Color4");
		Color[] color = {ZeroColor, OneColor, TwoColor, ThreeColor, FourColor};
		
		int vision = parseXMLInteger(root, "Vision");
		int metabolism = parseXMLInteger(root, "Metabolism");
		int minSugar = parseXMLInteger(root, "MinSugar");
		int maxSugar = parseXMLInteger(root, "MaxSugar");
		int sugarGrow = parseXMLInteger(root, "SugarGrow");
		int preset = parseXMLInteger(root, "Preset");
		int[] misc = {vision, metabolism, minSugar, maxSugar, sugarGrow, preset};

		SugarRule mySugar = new SugarRule(cellLength, row, column, neighbor, side, toro, percent, color, misc);
		mySugar.setName(XML_TAG_NAME);
		
		if (true) {
			mySugar = (SugarRule) initSpecific(mySugar, root, row, column, neighbor, side, color, toro, 1);
		}
		
		return mySugar;
	}
	
	protected Rule initSpecific(Rule rule, Element root, int row, int column, int neighbor, int side, Color[] color, boolean toro, int defaultState) throws XMLFactoryException {
		Cell[][] temp = new Cell[row][column];
		rule.setGrid(temp);
		rule.initBoard(side);
		int[][] tempUpdated = buildSpecific(root, row, column, defaultState);
		for (int i=0; i<row; i++) {
			for (int j=0; j<column; j++) {
				rule.getGrid()[i][j].setState(tempUpdated[i][j], color[tempUpdated[i][j]]);
			}
		}
		return rule;
		
	}
	
}