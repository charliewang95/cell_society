package layout.rule;

import layout.Cell;

/**
 * 
 * This class help the rule class initialize its neighbor array
 * 
 * @author Charlie Wang
 *
 */
public class NeighborManager {
	private int myRow;
	private int myColumn;
	private int myNumNeighbor;
	private Cell[][] myGrid;
	private boolean myToroidal;

	public NeighborManager(int row, int col) {
		myRow = row;
		myColumn = col;
	}

	public void init(int numNeighbor, Cell[][] grid, boolean toroidal) {
		myNumNeighbor = numNeighbor;
		myGrid = grid;
		myToroidal = toroidal;
	}

	public void setNeighborNumber(int numNeighbor) {
		myNumNeighbor = numNeighbor;
	}

	public Cell[][] getGrid() {
		return myGrid;
	}

	public void chooseMethod() {
		if (myNumNeighbor == 3) {
			initNeighbor3();
		} else if (myNumNeighbor == 4) {
			initNeighbor4();
		} else if (myNumNeighbor == 6) {
			initNeighbor6();
		} else if (myNumNeighbor == 8) {
			initNeighbor8();
		}
	}

	private void initNeighbor3() {
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

	private void initNeighbor4() {
		for (int i = 0; i < myRow; i++) {
			for (int j = 0; j < myColumn; j++) {
				initNeighborUp(i, j);
				initNeighborLeft(i, j);
				initNeighborRight(i, j);
				initNeighborDown(i, j);
			}
		}
	}

	private void initNeighbor6() {
		for (int i = 0; i < myRow; i++) {
			for (int j = 0; j < myColumn; j++) {
				if (j % 2 == 0) {
					initNeighborTopLeft(i, j);
					initNeighborTopRight(i, j);
				} else {
					initNeighborBottomLeft(i, j);
					initNeighborBottomRight(i, j);
				}
				initNeighborUp(i, j);
				initNeighborLeft(i, j);
				initNeighborRight(i, j);
				initNeighborDown(i, j);
			}
		}
	}

	private void initNeighbor8() {
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

	private void initNeighborUp(int i, int j) {
		if (myToroidal) {
			initNeighborUpT(i, j);
			return;
		}
		if (i != 0) {
			myGrid[i][j].addNeighbor(myGrid[i - 1][j]);
		}
	}

	private void initNeighborLeft(int i, int j) {
		if (myToroidal) {
			initNeighborLeftT(i, j);
			return;
		}
		if (j != 0) {
			myGrid[i][j].addNeighbor(myGrid[i][j - 1]);
		}
	}

	private void initNeighborRight(int i, int j) {
		if (myToroidal) {
			initNeighborRightT(i, j);
			return;
		}
		if (j != myColumn - 1) {
			myGrid[i][j].addNeighbor(myGrid[i][j + 1]);
		}
	}

	private void initNeighborDown(int i, int j) {
		if (myToroidal) {
			initNeighborDownT(i, j);
			return;
		}
		if (i != myRow - 1) {
			myGrid[i][j].addNeighbor(myGrid[i + 1][j]);
		}
	}

	private void initNeighborBottomRight(int i, int j) {
		if (myToroidal) {
			initNeighborBottomRightT(i, j);
			return;
		}
		if (i != myRow - 1 && j != myColumn - 1) {
			myGrid[i][j].addNeighbor(myGrid[i + 1][j + 1]);
		}
	}

	private void initNeighborBottomLeft(int i, int j) {
		if (myToroidal) {
			initNeighborBottomLeftT(i, j);
			return;
		}
		if (i != myRow - 1 && j != 0) {
			myGrid[i][j].addNeighbor(myGrid[i + 1][j - 1]);
		}
	}

	private void initNeighborTopRight(int i, int j) {
		if (myToroidal) {
			initNeighborTopRightT(i, j);
			return;
		}
		if (i != 0 && j != myColumn - 1) {
			myGrid[i][j].addNeighbor(myGrid[i - 1][j + 1]);
		}
	}

	private void initNeighborTopLeft(int i, int j) {
		if (myToroidal) {
			initNeighborTopLeftT(i, j);
			return;
		}
		if (i != 0 && j != 0) {
			myGrid[i][j].addNeighbor(myGrid[i - 1][j - 1]);
		}
	}

	private void initNeighborUpT(int i, int j) {
		if (i != 0) {
			myGrid[i][j].addNeighbor(myGrid[i - 1][j]);
		} else {
			myGrid[i][j].addNeighbor(myGrid[myRow - 1][j]);
		}
	}

	private void initNeighborLeftT(int i, int j) {
		if (j != 0) {
			myGrid[i][j].addNeighbor(myGrid[i][j - 1]);
		} else {
			myGrid[i][j].addNeighbor(myGrid[i][myColumn - 1]);
		}
	}

	private void initNeighborRightT(int i, int j) {
		if (j != myColumn - 1) {
			myGrid[i][j].addNeighbor(myGrid[i][j + 1]);
		} else {
			myGrid[i][j].addNeighbor(myGrid[i][0]);
		}
	}

	private void initNeighborDownT(int i, int j) {
		if (i != myRow - 1) {
			myGrid[i][j].addNeighbor(myGrid[i + 1][j]);
		} else {
			myGrid[i][j].addNeighbor(myGrid[0][j]);
		}
	}

	private void initNeighborTopLeftT(int i, int j) {
		if (i != 0 && j != 0) {
			myGrid[i][j].addNeighbor(myGrid[i - 1][j - 1]);
		} else if (i == 0 && j == 0) {
			myGrid[i][j].addNeighbor(myGrid[myRow - 1][myColumn - 1]);
		} else if (i == 0 && j != 0) {
			myGrid[i][j].addNeighbor(myGrid[myRow - 1][j - 1]);
		} else {
			myGrid[i][j].addNeighbor(myGrid[i - 1][myColumn - 1]);
		}
	}

	private void initNeighborTopRightT(int i, int j) {
		if (i != 0 && j != myColumn - 1) {
			myGrid[i][j].addNeighbor(myGrid[i - 1][j + 1]);
		} else if (i == 0 && j == myColumn - 1) {
			myGrid[i][j].addNeighbor(myGrid[myRow - 1][0]);
		} else if (i == 0 && j != myColumn - 1) {
			myGrid[i][j].addNeighbor(myGrid[myRow - 1][j + 1]);
		} else {
			myGrid[i][j].addNeighbor(myGrid[i - 1][0]);
		}
	}
	
	private void initNeighborBottomLeftT(int i, int j) {
		if (i != myRow - 1 && j != 0) {
			myGrid[i][j].addNeighbor(myGrid[i + 1][j - 1]);
		} else if (i == myRow - 1 && j == 0) {
			myGrid[i][j].addNeighbor(myGrid[0][myColumn - 1]);
		} else if (i == myRow - 1 && j != 0) {
			myGrid[i][j].addNeighbor(myGrid[0][j - 1]);
		} else {
			myGrid[i][j].addNeighbor(myGrid[i + 1][myColumn - 1]);
		}
	}
	
	private void initNeighborBottomRightT(int i, int j) {
		if (i != myRow - 1 && j != myColumn - 1) {
			myGrid[i][j].addNeighbor(myGrid[i + 1][j + 1]);
		} else if (i == myRow - 1 && j == myColumn - 1) {
			myGrid[i][j].addNeighbor(myGrid[0][0]);
		} else if (i == myRow - 1 && j != myColumn - 1) {
			myGrid[i][j].addNeighbor(myGrid[0][j + 1]);
		} else {
			myGrid[i][j].addNeighbor(myGrid[i + 1][0]);
		}
	}
}
