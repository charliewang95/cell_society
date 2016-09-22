package layout;

import javafx.scene.paint.Color;

public abstract class Rule {
	private Cell[][] myGrid;
	private int myLength;
	private int myWidth;
	private int mySizeX;
	private int mySizeY;
	private int cellLength;
	private int cellWidth;

	public Rule(int length, int width, int sizeX, int sizeY) {
		myLength = length;
		myWidth = width;
		mySizeX = sizeX;
		mySizeY = sizeY;
		cellLength = myLength / mySizeX;
		cellWidth = myWidth / mySizeY;
	}

	/**
	 * Initialize the cell grid
	 */
	public abstract void initGrid();
	
	/**
	 * Initialize the state and color of the cell grid
	 * @param i row number
	 * @param j col number
	 */
	public abstract void initState(int i, int j);
	
	/**
	 * Initialize the neighbor cells of the selected cell
	 * number of cell decided by rule
	 */
	public abstract void initNeighbor(int i, int j);
	
	/**
	 * Change each cell's state according to its neighbors
	 */
	public abstract void changeState();

	/**
	 * Check if the cell grid has reached the end state -- no further movement
	 * 
	 * @return whether end state has reached
	 */
	public abstract boolean endState();
	
	/**
	 * @return 
	 */
	public Cell[][] getGrid() {
		return myGrid;
	}
}
