package xml.factory;

import layout.Cell;
import layout.Rule;

import org.w3c.dom.Element;
import java.util.Objects;
import java.util.ResourceBundle;


/**
 * An XMLFactory that gives back a Rule object.
 *
 * @author Joy Kim
 */
public abstract class RuleXMLFactory extends XMLFactory {
    private String myRuleType;
    private String myRuleProperty;
//    protected double cellLength;
//    protected int myRow;
//    protected int myColumn;
//    protected Cell[][] myGrid;
    

    
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
    
//    /**
//     * Initialize the grid of the Rule object
//     */
//    public abstract void initGrid();
//    
//    /**
//     * Initialize the states of the grid for the first time
//     */
//    public abstract void initState(Cell[][] grid, int[][] updatedGrid);
//    
//    
//    /**
//	 * Initialize the neighbor cells of the selected cell
//	 * number of cell (4) 
//	 */
//	/**
//	 * Initialize the neighbor cells of the selected cell
//	 * number of cell (4) 
//	 */
//	public void initNeighbor4(){
//		for (int i = 0; i < myRow; i++) {
//			for (int j = 0; j < myColumn; j++) {
//				initNeighborUp(i, j);
//				initNeighborLeft(i, j);
//				initNeighborRight(i, j);
//				initNeighborDown(i, j);
//			}
//		}
//	}
//	
//	/**
//	 * Initialize the neighbor cells of the selected cell
//	 * number of cell (4) 
//	 */
//	public void initNeighbor8() {
//		for (int i = 0; i < myRow; i++) {
//			for (int j = 0; j < myColumn; j++) {
//				initNeighborTopLeft(i, j);
//				initNeighborUp(i, j);
//				initNeighborTopRight(i, j);
//				initNeighborLeft(i, j);
//				initNeighborRight(i, j);
//				initNeighborBottomLeft(i, j);
//				initNeighborDown(i, j);
//				initNeighborBottomRight(i, j);
//			}
//		}
//	}
//	
//	/**
//	 * generate the neighbor on the top
//	 */
//	public void initNeighborUp(int i, int j) {
//		if (i != 0) {
//			myGrid[i][j].addNeighbor(myGrid[i - 1][j]);
//		}
//	}
//
//	/**
//	 * generate the neighbor on the left
//	 */
//	public void initNeighborLeft(int i, int j) {
//		if (j != 0) {
//			myGrid[i][j].addNeighbor(myGrid[i][j - 1]);
//		}
//	}
//
//	/**
//	 * generate the neighbor on the right
//	 */
//	public void initNeighborRight(int i, int j) {
//		if (j != myColumn - 1) {
//			myGrid[i][j].addNeighbor(myGrid[i][j + 1]);
//		}
//	}
//
//	/**
//	 * generate the neighbor below
//	 */
//	public void initNeighborDown(int i, int j) {
//		if (i != myRow - 1) {
//			myGrid[i][j].addNeighbor(myGrid[i + 1][j]);
//		}
//	}
//	
//	public void initNeighborBottomRight(int i, int j) {
//		if (i != myRow - 1 && j != myColumn - 1) {
//			myGrid[i][j].addNeighbor(myGrid[i + 1][j + 1]);
//		}
//	}
//
//	public void initNeighborBottomLeft(int i, int j) {
//		if (i != myRow - 1 && j != 0) {
//			myGrid[i][j].addNeighbor(myGrid[i + 1][j - 1]);
//		}
//	}
//
//	public void initNeighborTopRight(int i, int j) {
//		if (i != 0 && j != myColumn - 1) {
//			myGrid[i][j].addNeighbor(myGrid[i - 1][j + 1]);
//		}
//	}
//
//	public void initNeighborTopLeft(int i, int j) {
//		if (i != 0 && j != 0) {
//			myGrid[i][j].addNeighbor(myGrid[i - 1][j - 1]);
//		}
//	}

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
