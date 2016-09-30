package layout;

import javafx.scene.paint.Color;

public abstract class Rule {
	protected Cell[][] myGrid;
	protected int[][] myUpdatedGrid;
	protected double myCellLength;
	protected int myRow;
	protected int myColumn;
	protected double myLength;
	protected double myWidth;
	protected String ruleName;

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
	protected Rule(double cellLength, int row, int column) {
		myCellLength = cellLength;
		myRow = row;
		myColumn = column;
	}

	public abstract void initGrid();

	protected void initRec() {
		myWidth = myCellLength * myColumn;
		myLength = myCellLength * myRow;
		myGrid = new Cell[myRow][myColumn];
		myUpdatedGrid = new int[myRow][myColumn];
		for (int i = 0; i < myRow; i++) {
			for (int j = 0; j < myColumn; j++) {
				double x1 = myCellLength * j;
				double y1 = myCellLength * i;
				double x2 = myCellLength * (j + 1);
				double y2 = myCellLength * i;
				double x3 = myCellLength * (j + 1);
				double y3 = myCellLength * (i + 1);
				double x4 = myCellLength * (j);
				double y4 = myCellLength * (i + 1);
				myGrid[i][j] = new Cell(new double[] { x1, x2, x3, x4 }, new double[] { y1, y2, y3, y4 }, i, j);
			}
		}
	}

	protected void initTriangle() {
		myWidth = myCellLength * (myColumn + 1) / 2;
		myLength = (myCellLength * Math.sqrt(3) / 2) * myRow;
		myGrid = new Cell[myRow][myColumn];
		myUpdatedGrid = new int[myRow][myColumn];
		for (int i = 0; i < myRow; i++) {
			for (int j = 0; j < myColumn; j++) {
				double x1, x2, x3, y1, y2, y3;
				if ((i + j) % 2 == 0) {
					x1 = (myCellLength / 2) * j;
					y1 = (myCellLength * Math.sqrt(3) / 2) * i;
					x2 = (myCellLength / 2) * (j + 2);
					y2 = (myCellLength * Math.sqrt(3) / 2) * i;
					x3 = (myCellLength / 2) * (j + 1);
					y3 = (myCellLength * Math.sqrt(3) / 2) * (i + 1);
				} else {
					x1 = (myCellLength / 2) * (j + 1);
					y1 = (myCellLength * Math.sqrt(3) / 2) * i;
					x2 = (myCellLength / 2) * (j);
					y2 = (myCellLength * Math.sqrt(3) / 2) * (i + 1);
					x3 = (myCellLength / 2) * (j + 2);
					y3 = (myCellLength * Math.sqrt(3) / 2) * (i + 1);
				}
				myGrid[i][j] = new Cell(new double[] { x1, x2, x3 }, new double[] { y1, y2, y3 }, i, j);
			}
		}
	}

	protected void initHex() {
		myWidth = myCellLength * (3/2) * (myRow - 1) + myCellLength / 2;
		myLength = myCellLength * (Math.sqrt(3)) * myColumn + myCellLength * (Math.sqrt(3)) / 2;
		myGrid = new Cell[myRow][myColumn];
		myUpdatedGrid = new int[myRow][myColumn];
		for (int i = 0; i < myRow; i++) {
			for (int j = 0; j < myColumn; j++) {
				double[] x = new double[6];
				double[] y = new double[6];
				if ((i + j) % 2 == 0) {
					x[0] = (myCellLength / 2) * j;
					y[0] = (myCellLength/2 * Math.sqrt(3)) * 2 * i;
					x[1] = (myCellLength / 2) * (j + 2);
					y[1] = (myCellLength/2 * Math.sqrt(3)) * i;
					x[2] = (myCellLength / 2) * (j + 1);
					y[2] = (myCellLength/2 * Math.sqrt(3)) * (i + 1);
					x[3];
					y[3];
					x[4];
					y[4];
					x[5];
					y[5];
					x[6];
					y[6];
				}
			}
		}
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

	/**
	 * Initialize the state and color of the cell grid
	 * 
	 * @param i
	 *            row number
	 * @param j
	 *            col number
	 */
	public abstract void initState();

	public void initNeighbor3() {
		for (int i = 0; i < myRow; i++) {
			for (int j = 0; j < myColumn; j++) {
				if ((i + j) % 2 == 0) {
					initNeighborUp(i, j);
				} else {
					initNeighborDown(i, j);
				}
				initNeighborLeft(i, j);
				initNeighborRight(i, j);
			}
		}
	}

	public void initNeighbor4() {
		for (int i = 0; i < myRow; i++) {
			for (int j = 0; j < myColumn; j++) {
				initNeighborUp(i, j);
				initNeighborLeft(i, j);
				initNeighborRight(i, j);
				initNeighborDown(i, j);
			}
		}
	}

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

	public void initNeighborLeft(int i, int j) {
		if (j != 0) {
			myGrid[i][j].addNeighbor(myGrid[i][j - 1]);
		}
	}

	public void initNeighborRight(int i, int j) {
		if (j != myColumn - 1) {
			myGrid[i][j].addNeighbor(myGrid[i][j + 1]);
		}
	}

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

	public Cell[][] getGrid() {
		return myGrid;
	}
	
	public int[][] getUpdatedGrid(){
		return myUpdatedGrid;
	}

	public double getWidth() {
		return myWidth;
	}

	public double getLength() {
		return myLength;
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
	
	public abstract Color[] getColors();
}
