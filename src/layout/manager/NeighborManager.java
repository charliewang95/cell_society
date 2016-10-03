package layout.manager;

import layout.Cell;
import layout.Rule;
import layout.rule.SugarRule;

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
	private Rule myRule;

	public NeighborManager(int row, int col) {
		myRow = row;
		myColumn = col;
	}

	public void init(int numNeighbor, Cell[][] grid, boolean toroidal, Rule rule) {
		myNumNeighbor = numNeighbor;
		myGrid = grid;
		myToroidal = toroidal;
		myRule = rule;
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
		if (myRule instanceof SugarRule) {
			initNeighbor3Sugar();
			return;
		}
		for (int i = 0; i < myRow; i++) {
			for (int j = 0; j < myColumn; j++) {
				if ((i + j) % 2 == 0) {
					initNeighborSideHelper(i, j, myRow-1, j, i-1, j, i, 0);
				} else {
					initNeighborSideHelper(i, j, 0, j, i+1, j, i, myRow -1);
				}
				initNeighborSideHelper(i, j, i, myColumn - 1, i, j-1, j, 0);
				initNeighborSideHelper(i, j, i, 0, i, j+1, j, myColumn - 1);
			}
		}
	}

	private void initNeighbor4() {
		if (myRule instanceof SugarRule) {
			initNeighbor4Sugar();
			return;
		}
		for (int i = 0; i < myRow; i++) {
			for (int j = 0; j < myColumn; j++) {
				initNeighborSideHelper(i, j, myRow-1, j, i-1, j, i, 0);
				initNeighborSideHelper(i, j, i, myColumn - 1, i, j-1, j, 0);
				initNeighborSideHelper(i, j, i, 0, i, j+1, j, myColumn -1);
				initNeighborSideHelper(i, j, 0, j, i+1, j, i, myRow -1);
			}
		}
	}

	private void initNeighbor6() {
		if (myRule instanceof SugarRule) {
			initNeighbor6Sugar();
			return;
		}
		for (int i = 0; i < myRow; i++) {
			for (int j = 0; j < myColumn; j++) {
				if (j % 2 == 0) {
					initNeighborCornerHelper(i, j, myRow - 1, myColumn - 1, i - 1, j - 1);
					initNeighborCornerHelper(i, j, myRow - 1, 0, i - 1, j + 1);
				} else {
					initNeighborCornerHelper(i, j, 0, myColumn - 1, i + 1, j - 1);
					initNeighborCornerHelper(i, j, 0, 0, i + 1, j + 1);
				}
				initNeighborSideHelper(i, j, myRow-1, j, i-1, j, i, 0);
				initNeighborSideHelper(i, j, i, myColumn - 1, i, j-1, j, 0);
				initNeighborSideHelper(i, j, i, 0, i, j+1, j, myColumn -1);
				initNeighborSideHelper(i, j, 0, j, i+1, j, i, myRow -1);
			}
		}
	}

	private void initNeighbor8() {
		for (int i = 0; i < myRow; i++) {
			for (int j = 0; j < myColumn; j++) {
				initNeighborCornerHelper(i, j, myRow - 1, myColumn - 1, i - 1, j - 1);
				initNeighborSideHelper(i, j, myRow-1, j, i-1, j, i, 0);
				initNeighborCornerHelper(i, j, myRow - 1, 0, i - 1, j + 1);
				initNeighborSideHelper(i, j, i, myColumn - 1, i, j-1, j, 0);
				initNeighborSideHelper(i, j, i, 0, i, j+1, j, myColumn - 1);
				initNeighborCornerHelper(i, j, 0, myColumn - 1, i + 1, j - 1);
				initNeighborSideHelper(i, j, 0, j, i+1, j, i, myRow -1);
				initNeighborCornerHelper(i, j, 0, 0, i + 1, j + 1);
			}
		}
	}

	/**
	 * Specific helper methods to create a single neighbor
	 */

	private void initNeighborSideHelper(int i, int j, int iflag, int jflag, int imp, int jmp, int com1, int com2) {
		if (com1 != com2) {
			myGrid[i][j].addNeighbor(myGrid[imp][jmp]);
		} else if (myToroidal) {
			initNeighborToroidalSideHelper(i, j, iflag, jflag);
		}
	}

	private void initNeighborCornerHelper(int i, int j, int iflag, int jflag, int imp, int jmp) {
		if (i != myRow - 1 - iflag && j != myColumn - 1 - jflag) {
			myGrid[i][j].addNeighbor(myGrid[imp][jmp]);
		} else if (myToroidal) {
			initNeighborToroidalCornerHelper(i, j, iflag, jflag, imp, jmp);
		}
	}
	
	/**
	 * Methods below are for toroidal neighbor set-up
	 */
	
	private void initNeighborToroidalSideHelper(int i, int j, int iflag, int jflag) {
		myGrid[i][j].addNeighbor(myGrid[iflag][jflag]);
	}

	private void initNeighborToroidalCornerHelper(int i, int j, int iflag, int jflag, int imp, int jmp) {
		if (i == myRow - 1 - iflag && j == myColumn - 1 - jflag) {
			myGrid[i][j].addNeighbor(myGrid[iflag][jflag]);
		} else if (i == myRow - 1 - iflag && j != myColumn - 1 - jflag) {
			myGrid[i][j].addNeighbor(myGrid[iflag][jmp]);
		} else {
			myGrid[i][j].addNeighbor(myGrid[imp][jflag]);
		}
	}

	/**
	 * Specific neighbor set-up method for SugarRule
	 */

	private void initNeighbor3Sugar() {
		int vision = ((SugarRule) myRule).getVision();
		for (int i = 0; i < myRow; i++) {
			for (int j = 0; j < myColumn; j++) {
				for (int k = 1; k <= vision; k++) {
					// add neighbors left
					if (i - k >= 0) {
						myGrid[i][j].addNeighbor(myGrid[i - k][j]);
					} else if (myToroidal) {
						myGrid[i][j].addNeighbor(myGrid[myRow - (k - i)][j]);
					}

					// add neighbors right
					if (i + k < myRow) {
						myGrid[i][j].addNeighbor(myGrid[i + k][j]);
					} else if (myToroidal) {
						myGrid[i][j].addNeighbor(myGrid[i + k - myRow][j]);
					}

					sugarhelper(i, j, (k + (j + 1) % 2) / 2, (k + (i + 1) % 2) / 2);
				}
			}
		}
	}

	private void initNeighbor4Sugar() {
		int vision = ((SugarRule) myRule).getVision();
		for (int i = 0; i < myRow; i++) {
			for (int j = 0; j < myColumn; j++) {
				for (int k = 1; k <= vision; k++) {

					// add neighbors above
					if (i - k >= 0) {
						myGrid[i][j].addNeighbor(myGrid[i - k][j]);
					} else if (myToroidal) {
						myGrid[i][j].addNeighbor(myGrid[myRow - (k - i)][j]);
					}

					// add neighbors below
					if (i + k < myRow) {
						myGrid[i][j].addNeighbor(myGrid[i + k][j]);
					} else if (myToroidal) {
						myGrid[i][j].addNeighbor(myGrid[i + k - myRow][j]);
					}

					// add neighbors to the left
					if (j - k >= 0) {
						myGrid[i][j].addNeighbor(myGrid[i][j - k]);
					} else if (myToroidal) {
						myGrid[i][j].addNeighbor(myGrid[i][myColumn - (k - j)]);
					}

					// add neighbors to the right
					if (j + k < myColumn) {
						myGrid[i][j].addNeighbor(myGrid[i][j + k]);
					} else if (myToroidal) {
						myGrid[i][j].addNeighbor(myGrid[i][j + k - myColumn]);
					}
				}
			}
		}
	}

	private void initNeighbor6Sugar() {
		int vision = ((SugarRule) myRule).getVision();
		for (int i = 0; i < myRow; i++) {
			for (int j = 0; j < myColumn; j++) {
				for (int k = 1; k <= vision; k++) {
					// add neighbors above
					if (i - k >= 0) {
						myGrid[i][j].addNeighbor(myGrid[i - k][j]);
					} else if (myToroidal) {
						myGrid[i][j].addNeighbor(myGrid[myRow - (k - i)][j]);
					}

					// add neighbors below
					if (i + k < myRow) {
						myGrid[i][j].addNeighbor(myGrid[i + k][j]);
					} else if (myToroidal) {
						myGrid[i][j].addNeighbor(myGrid[i + k - myRow][j]);
					}

					// add neighbor top left, top right, bottom left, bottom
					// right
					sugarhelper(i, j, (k + (j + 1) % 2) / 2, k);
				}
			}
		}
	}

	private void sugarhelper(int i, int j, int iflag, int jflag) {
		if (j - jflag >= 0 && i - iflag >= 0) { // top left
			myGrid[i][j].addNeighbor(myGrid[i - iflag][j - jflag]);
		} else {
			if (myToroidal) {
				if (j - jflag < 0 && i - iflag >= 0) {
					myGrid[i][j].addNeighbor(myGrid[i - iflag][myColumn + j - jflag]);
				} else if (j - jflag >= 0 && i - iflag < 0) {
					myGrid[i][j].addNeighbor(myGrid[myRow + i - iflag][j - jflag]);
				} else {
					myGrid[i][j].addNeighbor(myGrid[myRow + i - iflag][myColumn + j - jflag]);
				}
			}
		}

		if (j + jflag < myColumn && i - iflag >= 0) { // top right
			myGrid[i][j].addNeighbor(myGrid[i - iflag][j + jflag]);
		} else {
			if (myToroidal) {
				if (j + jflag >= myColumn && i - iflag >= 0) {
					myGrid[i][j].addNeighbor(myGrid[i - iflag][j + jflag - myColumn]);
				} else if (j + jflag < myColumn && i - iflag < 0) {
					myGrid[i][j].addNeighbor(myGrid[myRow + i - iflag][j + jflag]);
				} else {
					myGrid[i][j].addNeighbor(myGrid[myRow + i - iflag][j + jflag - myColumn]);
				}
			}
		}

		if (j - jflag >= 0 && i + iflag < myRow) { // bottom left
			myGrid[i][j].addNeighbor(myGrid[i + iflag][j - jflag]);
		} else {
			if (myToroidal) {
				if (j - jflag < 0 && i + iflag < myRow) {
					myGrid[i][j].addNeighbor(myGrid[i + iflag][myColumn + j - jflag]);
				} else if (j - jflag >= 0 && i + iflag >= myRow) {
					myGrid[i][j].addNeighbor(myGrid[i + iflag - myRow][j - jflag]);
				} else {
					myGrid[i][j].addNeighbor(myGrid[i + iflag - myRow][myColumn + j - jflag]);
				}
			}
		}

		if (j + jflag < myColumn && i + iflag < myRow) { // bottom right
			myGrid[i][j].addNeighbor(myGrid[i + iflag][j + jflag]);
		} else {
			if (myToroidal) {
				if (j + jflag >= myColumn && i + iflag < myRow) {
					myGrid[i][j].addNeighbor(myGrid[i + iflag][j + jflag - myColumn]);
				} else if (j + jflag < myColumn && i + iflag >= myRow) {
					myGrid[i][j].addNeighbor(myGrid[i + iflag - myRow][j + jflag]);
				} else {
					myGrid[i][j].addNeighbor(myGrid[i + iflag - myRow][j + jflag - myColumn]);
				}
			}
		}
	}
}
