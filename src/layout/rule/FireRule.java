package layout.rule;

import java.util.Random;

import javafx.scene.paint.Color;
import layout.Cell;
import layout.Rule;

public class FireRule extends Rule {
	private static final int EMPTY = 0;
	private static final int TREE = 1;
	private static final int BURN = 2;
	private static final int NUMNEIGHBOR = 4;
	private static final double PROBCATCH = 0.7;
	private static final Color EMPTYCOLOR = Color.YELLOW;
	private static final Color TREECOLOR = Color.GREEN;
	private static final Color BURNCOLOR = Color.RED;
	private Cell[][] myGrid;
	private Cell[][] myUpdatedGrid;
	private int myLength;
	private int myWidth;
	private int myRow;
	private int myColumn;
	private int cellLength;
	private int cellWidth;
	private boolean ended;

	public FireRule(int length, int width, int row, int column) {
		super(length, width, row, column);
		ended = false;
	}

	public void initGrid() {
		myGrid = new Cell[myRow][myColumn];
		myUpdatedGrid = new Cell[myRow][myColumn];
		for (int i = 0; i < myRow; i++) {
			for (int j = 0; j < myColumn; j++) {
				int x = cellWidth * j;
				int y = cellLength * i;
				myGrid[i][j] = new Cell(x, y, cellWidth, cellLength);
				initState(i, j);
			}
		}
		for (int i = 0; i < myRow; i++) {
			for (int j = 0; j < myColumn; j++) {
				initNeighbor(i, j);
			}
		}
	}

	public void initState(int i, int j) {
		if (i == 0 || i == myRow - 1 || j == 0 || j == myColumn - 1) {
			myGrid[i][j].init(EMPTY, EMPTYCOLOR, NUMNEIGHBOR);
		} else if (i == myRow / 2 && j == myColumn / 2) {
			myGrid[i][j].init(BURN, BURNCOLOR, NUMNEIGHBOR);
		} else {
			myGrid[i][j].init(TREE, TREECOLOR, NUMNEIGHBOR);
		}
	}

	public void initNeighbor(int i, int j) {
		initNeighborUp(i, j);
		initNeighborLeft(i, j);
		initNeighborRight(i, j);
		initNeighborDown(i, j);
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
		if (i != myRow - 1) {
			myGrid[i][j].addNeighbor(myGrid[i][j + 1]);
		}
	}

	public void initNeighborDown(int i, int j) {
		if (j != myColumn - 1) {
			myGrid[i][j].addNeighbor(myGrid[i + 1][j]);
		}
	}

	public void changeState() {
		ended = true;
		for (int i = 0; i < myRow; i++) {
			for (int j = 0; j < myColumn; j++) {
				if (myGrid[i][j].getState() == BURN) {
					for (Cell c: myGrid[i][j].getNeighbors()) {
						Random random = new Random();
						if (random.nextDouble()<PROBCATCH) {
							c.setState(BURN);
							ended=false;
						}
					}
					myGrid[i][j].setState(EMPTY);
				}
			}
		}
	}

	@Override
	public boolean endState() {
		return ended;
	}
}
