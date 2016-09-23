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
	private static final double PROBCATCH = 0.55; // parameter
	private static final Color EMPTYCOLOR = Color.YELLOW;
	private static final Color TREECOLOR = Color.GREEN;
	private static final Color BURNCOLOR = Color.RED;
	private Color[] myColors;
	private int[][] myUpdatedGrid;

	public FireRule(int length, int width, int row, int column) {
		super(length, width, row, column);
		myColors = new Color[] { EMPTYCOLOR, TREECOLOR, BURNCOLOR };
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
		initNeighbor4();
	}

	@Override
	public void initState() {
		for (int i = 0; i < myRow; i++) {
			for (int j = 0; j < myColumn; j++) {
				if (i == 0 || i == myRow - 1 || j == 0 || j == myColumn - 1) {
					myGrid[i][j].init(EMPTY, myColors[EMPTY], NUMNEIGHBOR);
					myUpdatedGrid[i][j] = EMPTY;
				} else if (i == myRow / 2 && j == myColumn / 2) {
					myGrid[i][j].init(BURN, myColors[BURN], NUMNEIGHBOR);
					myUpdatedGrid[i][j] = BURN;
				} else {
					myGrid[i][j].init(TREE, myColors[TREE], NUMNEIGHBOR);
					myUpdatedGrid[i][j] = TREE;
				}
			}
		}
	}

	@Override
	public void changeState() {
		for (int i = 0; i < myRow; i++) {
			for (int j = 0; j < myColumn; j++) {
				if (myGrid[i][j].getState() == BURN) {
					for (Cell c : myGrid[i][j].getNeighbors()) {
						Random random = new Random();
						if (random.nextDouble() < PROBCATCH && c.getState() == TREE) {
							myUpdatedGrid[c.getRow()][c.getCol()] = BURN;
						}
					}
					myUpdatedGrid[i][j] = EMPTY;
				}
			}
		}

		for (int i = 0; i < myRow; i++) {
			for (int j = 0; j < myColumn; j++) {
				myGrid[i][j].setState(myUpdatedGrid[i][j]);
				myGrid[i][j].setColor(myColors[myUpdatedGrid[i][j]]);
			}
		}
	}

}
