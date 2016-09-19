package layout;

public abstract class Rule {
	private Cell[][] grid;
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
	
	/*
	 * Code to calculate grid coordinates and initialize
	 * grid = new Cell[mySizeX][mySizeY];
		for (int i = 0; i < mySizeX; i++) {
			for (int j = 0; j < mySizeY; j++) {
				int x = cellLength * i;
				int y = cellWidth * j;
				grid[i][j] = new Cell(x, y, cellLength, cellWidth);
				\\\\add codes to initialize the states
			}
		}
	 * 
	 * 
	 */
	
	
	/**
	 * Initialize the state and color of the cell grid
	 */
	public abstract void initState();
	
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
}
