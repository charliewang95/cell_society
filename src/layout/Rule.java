package layout;

public abstract class Rule {
	protected Cell[][] myGrid;
	protected int myLength;
	protected int myWidth;
	protected int myRow;
	protected int myColumn;
	protected int cellLength;
	protected int cellWidth;
	private boolean ended;

	/**
	 * Construct the rule
	 * 
	 * @param length
	 *            length of the board
	 * @param width
	 *            width of the board
	 * @param row
	 *            total number of rows
	 * @param column
	 *            total number of columns
	 */
	protected Rule(int length, int width, int row, int column) {
		myLength = length;
		myWidth = width;
		myRow = row;
		myColumn = column;
		cellLength = myLength / myRow;
		cellWidth = myWidth / myColumn;
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
	public abstract void initState();
	
	/**
	 * Initialize the neighbor cells of the selected cell
	 * number of cell (4) 
	 */
	public void initNeighbor4(){
		for (int i = 0; i < myRow; i++) {
			for (int j = 0; j < myColumn; j++) {
				initNeighborUp(i, j);
				initNeighborLeft(i, j);
				initNeighborRight(i, j);
				initNeighborDown(i, j);
			}
		}
	}
	
	/**
	 * Initialize the neighbor cells of the selected cell
	 * number of cell (4) 
	 */
	public void initNeighbor8() {
		for (int i = 0; i < myRow; i++) {
			for (int j = 0; j < myColumn; j++) {
				initNeighborTopLeft(i, j);
				initNeighborUp(i, j);
				initNeighborTopRight(i, j);
				initNeighborLeft(i, j);
				initNeighborRight(i, j);
				initNeighborBottomLeft(i, j);
				initNeighborDown(i, j);
				initNeighborBottomRight(i, j);
			}
		}
	}
	
	public void initNeighborUp(int i, int j) {
		if (i != 0) {
			myGrid[i][j].addNeighbor(myGrid[i - 1][j]);
		}
	}

	/**
	 * generate the neighbor on the left
	 */
	public void initNeighborLeft(int i, int j) {
		if (j != 0) {
			myGrid[i][j].addNeighbor(myGrid[i][j - 1]);
		}
	}

	/**
	 * generate the neighbor on the right
	 */
	public void initNeighborRight(int i, int j) {
		if (j != myColumn - 1) {
			myGrid[i][j].addNeighbor(myGrid[i][j + 1]);
		}
	}

	/**
	 * generate the neighbor below
	 */
	public void initNeighborDown(int i, int j) {
		if (i != myRow - 1) {
			myGrid[i][j].addNeighbor(myGrid[i + 1][j]);
		}
	}
	
	public void initNeighborBottomRight(int i, int j) {
		if (i != myRow - 1 && j != myColumn - 1) {
			myGrid[i][j].addNeighbor(myGrid[i + 1][j + 1]);
		}
	}

	public void initNeighborBottomLeft(int i, int j) {
		if (i != myRow - 1 && j != 0) {
			myGrid[i][j].addNeighbor(myGrid[i + 1][j - 1]);
		}
	}

	public void initNeighborTopRight(int i, int j) {
		if (i != 0 && j != myColumn - 1) {
			myGrid[i][j].addNeighbor(myGrid[i - 1][j + 1]);
		}
	}

	public void initNeighborTopLeft(int i, int j) {
		if (i != 0 && j != 0) {
			myGrid[i][j].addNeighbor(myGrid[i - 1][j - 1]);
		}
	}

	
	/**
	 * Change each cell's state according to its neighbors
	 */
	public abstract void changeState();

	/**
	 * Check if the cell grid has reached the end state -- no further movement
	 * 
	 * @return whether end state has reached
	 */
	public boolean endState() {
		return ended;
	}
	
	/**
	 * @return 
	 */
	public Cell[][] getGrid(){
		return myGrid;
	}
	
	/**
	 * A testing method that prints each step's states in console as a grid
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
