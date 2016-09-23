package layout.rule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import javafx.scene.paint.Color;
import layout.Cell;
import layout.Rule;

public class SchellingRule extends Rule {
	private static final int EMPTY = 0;
	private static final int AAA = 1; // group A
	private static final int BBB = 2; // group B
	private static final int NUMNEIGHBOR = 8;
	private static final double PERCENTAGEA = 0.5; // parameter
	private static final double PERCENTAGEEMPTY = 0.3; // parameter
	private static final double SATISFIED = 0.7; // parameter
	private static final Color EMPTYCOLOR = Color.WHITE;
	private static final Color AAACOLOR = Color.RED;
	private static final Color BBBCOLOR = Color.BLUE;
	private Color[] myColors;
	private Cell[][] myGrid;
	private int[][] myUpdatedGrid;
	private int myLength;
	private int myWidth;
	private int myRow;
	private int myColumn;
	private int cellLength;
	private int cellWidth;
	private int myNumA;
	private int myNumB;
	private int myNumE;
	private int[] myAs;
	private int[] myBs;
	private int[] myEs;
	private int[] myEsTMP;
	private boolean ended;

	public SchellingRule(int length, int width, int row, int column) {
		super(length, width, row, column);
		myLength = length;
		myWidth = width;
		myRow = row;
		myColumn = column;
		cellLength = myLength / myRow;
		cellWidth = myWidth / myColumn;
		myColors = new Color[] { EMPTYCOLOR, AAACOLOR, BBBCOLOR };
		ended = false;
		myNumE = (int) (myRow * myColumn * PERCENTAGEEMPTY);
		myNumA = (int) ((myRow * myColumn - myNumE) * PERCENTAGEA);
		myNumB = (int) (myRow * myColumn - myNumE - myNumA);
		myAs = new int[myNumA];
		myBs = new int[myNumB];
		myEs = new int[myNumE];
		myEsTMP = new int[myNumE];
	}

	@Override
	public void initGrid() {
		myGrid = new Cell[myRow][myColumn];
		myUpdatedGrid = new int[myRow][myColumn];
		for (int i = 0; i < myRow; i++) {
			for (int j = 0; j < myColumn; j++) {
				int x = cellWidth * j;
				int y = cellLength * i;
				myGrid[i][j] = new Cell(x, y, cellWidth, cellLength, i, j);
			}
		}
		initState();
		initNeighbor();
	}

	@Override
	public void initState() {
		ArrayList<Integer> list = makeRandomList(myRow * myColumn);
		for (int k = 0; k < myRow * myColumn; k++) {
			int index = list.get(k);
			int i = index / myRow;
			int j = index - i * myRow;
			if (k < myNumA) {
				myGrid[i][j].init(AAA, myColors[AAA], NUMNEIGHBOR);
				myUpdatedGrid[i][j] = AAA;
				myAs[k] = index;
			} else if (k >= myNumA && k < myNumA + myNumB) {
				myGrid[i][j].init(BBB, myColors[BBB], NUMNEIGHBOR);
				myUpdatedGrid[i][j] = BBB;
				myBs[k - myNumA] = index;
			} else {
				myGrid[i][j].init(EMPTY, myColors[EMPTY], NUMNEIGHBOR);

				myUpdatedGrid[i][j] = EMPTY;
				myEs[k - myNumA - myNumB] = index;
				myEsTMP[k - myNumA - myNumB] = index;
			}
		}
	}

	private ArrayList<Integer> makeRandomList(int top) {
		ArrayList<Integer> list = new ArrayList<>(top);
		for (int i = 0; i < top; i++) {
			list.add(i);
		}
		Collections.shuffle(list);
		return list;
	}

	@Override
	public void initNeighbor() {
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

	private void initNeighborDown(int i, int j) {
		if (i != myRow - 1) {
			myGrid[i][j].addNeighbor(myGrid[i + 1][j]);
		}
	}

	private void initNeighborBottomRight(int i, int j) {
		if (i != myRow - 1 && j != myColumn - 1) {
			myGrid[i][j].addNeighbor(myGrid[i + 1][j + 1]);
		}
	}

	private void initNeighborBottomLeft(int i, int j) {
		if (i != myRow - 1 && j != 0) {
			myGrid[i][j].addNeighbor(myGrid[i + 1][j - 1]);
		}
	}

	private void initNeighborRight(int i, int j) {
		if (j != myColumn - 1) {
			myGrid[i][j].addNeighbor(myGrid[i][j + 1]);
		}
	}

	private void initNeighborLeft(int i, int j) {
		if (j != 0) {
			myGrid[i][j].addNeighbor(myGrid[i][j - 1]);
		}
	}

	private void initNeighborTopRight(int i, int j) {
		if (i != 0 && j != myColumn - 1) {
			myGrid[i][j].addNeighbor(myGrid[i - 1][j + 1]);
		}
	}

	private void initNeighborUp(int i, int j) {
		if (i != 0) {
			myGrid[i][j].addNeighbor(myGrid[i - 1][j]);
		}
	}

	private void initNeighborTopLeft(int i, int j) {
		if (i != 0 && j != 0) {
			myGrid[i][j].addNeighbor(myGrid[i - 1][j - 1]);
		}
	}

	@Override
	public void changeState() {
		if (myEs.length < 1)
			return;

		for (int i = 0; i < myRow; i++) {
			for (int j = 0; j < myColumn; j++) {
				double same = 0;
				double total = 0;
				if (myGrid[i][j].getState() != EMPTY) {
					for (Cell c : myGrid[i][j].getNeighbors()) {
						if (c.getState() != EMPTY) {
							if (c.getState() == myGrid[i][j].getState()) {
								same++;
							}
							total++;
						}
					}

					double percent = total == 0 ? 0 : same / total;

					if (percent < SATISFIED) {
						myUpdatedGrid[i][j] = EMPTY;
						Random random = new Random();
						int r = random.nextInt(myEs.length);
						int chosen = myEs[r];

						int a = chosen / myRow;
						int b = chosen - a * myRow;
						myUpdatedGrid[a][b] = myGrid[i][j].getState();
						myEs[r] = i * myRow + j;
					}
				}
			}
		}
		System.out.println();
		for (int i = 0; i < myRow; i++) {
			for (int j = 0; j < myColumn; j++) {
				myGrid[i][j].setState(myUpdatedGrid[i][j]);
				myGrid[i][j].setColor(myColors[myUpdatedGrid[i][j]]);
			}
		}
		// testByPrintingEachState();
	}

	public void testByPrintingEachState() {
		for (Cell[] p : myGrid) {
			for (Cell q : p) {
				System.out.print(q.getState() + " ");
			}
			System.out.println();
		}
		System.out.println();
	}

	@Override
	public boolean endState() {
		return ended;
	}

	@Override
	public Cell[][] getGrid() {
		return myGrid;
	}

}
