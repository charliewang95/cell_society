package layout;

import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.scene.paint.Color;
import layout.manager.NeighborManager;
import layout.manager.ShapeManager;
import layout.rule.Parameter;
import layout.rule.WatorRule;
import layout.rule.watoranimals.Animal;

/**
 * The parent class for all the subclass rules. Most common methods are defined in this
 * class.
 * 
 * @author Charlie Wang
 *
 */
public abstract class Rule {
	private static final String DEFAULT_RESOURCE_PACKAGE = "resources/";

	protected Cell[][] myGrid;
	protected int[][] myUpdatedGrid;
	protected double myCellLength;
	protected int myRow;
	protected int myColumn;
	protected double myLength;
	protected double myWidth;
	protected String ruleName;
	private NeighborManager myNeighborManager;
	private ShapeManager myShapeManager;

	protected ArrayList<Parameter> parameters;
	protected ResourceBundle myResources;
	protected int[] myCounters;
	protected Color[] myColors;
	protected String[] myLegend;

	/**
	 * Construct the rule
	 * 
	 * @param cellLength
	 *            length of each cell (the side length of each polygon)
	 * @param row
	 *            total number of rows
	 * @param column
	 *            total number of columns
	 */

	protected Rule(double cellLength, int row, int column) {
		myCellLength = cellLength;
		myRow = row;
		myColumn = column;
		myNeighborManager = new NeighborManager(row, column);
		myShapeManager = new ShapeManager(row, column, cellLength);
		parameters = new ArrayList<Parameter>();
		myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "English");
	}

	/**
	 * Initialize myGrid. Different rules have different appraoches.
	 */
	public abstract void initGrid();

	/**
	 * Initialize the state and color of the cell grid
	 * 
	 * @param i
	 *            row number
	 * @param j
	 *            col number
	 */
	public abstract void initState();

	/**
	 * Change each cell's state according to its neighbors
	 */
	public abstract void changeState();
	
	/**
	 * initialize the board according to different shapes
	 * 
	 * @param numSide the shape of the cell (options: triangle, rectangle, hexagon)
	 */
	public void initBoard(int numSide) {
		myUpdatedGrid = new int[myRow][myColumn];
		myShapeManager.init(numSide, myGrid, this);
		myShapeManager.chooseMethod();
		myGrid=myShapeManager.getGrid();
		myWidth = myShapeManager.getWidth();
		myLength = myShapeManager.getLength();
	}

	public double getCellLength() {
		return myCellLength;
	}

	public void setName(String name) {
		ruleName = name;
	}

	public String getName() {
		return ruleName;
	}

	public void initNeighbor(int numNeighbor, boolean toroidal) {
		myNeighborManager.init(numNeighbor, myGrid, toroidal);
		myNeighborManager.chooseMethod();
		myGrid = myNeighborManager.getGrid();
	}

	public Cell[][] getGrid() {
		return myGrid;
	}

	public int[][] getUpdatedGrid() {
		return myUpdatedGrid;
	}

	public double getWidth() {
		return myWidth;
	}

	public double getLength() {
		return myLength;
	}

	public ArrayList<Parameter> getParameters() {
		return parameters;
	}

	public int[] getCounters() {
		return myCounters;
	}

	public Color[] getColors() {
		return myColors;
	}

	public String[] getLegend() {
		return myLegend;
	}
	
	/**
	 * A tester that prints each step's states in console as a grid
	 */
	protected void testByPrintingEachState() {
		for (Cell[] p : myGrid) {
			for (Cell q : p) {
				System.out.print(q.getState() + " ");
			}
			System.out.println();
		}
		System.out.println();
	}

}
