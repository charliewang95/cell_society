package layout.rule;

import javafx.scene.paint.Color;
import layout.Cell;
import layout.Rule;

public class LifeRule extends Rule {
	private static final int EMPTY = 0;
	private static final int LIVE = 1;
	private static final int NUMNEIGHBOR = 8;
	private static final Color EMPTYCOLOR = Color.WHITE;
	private static final Color LIVECOLOR = Color.BLACK;
	private Color[] myColors;
	private int[][] myUpdatedGrid;

	public LifeRule(int length, int width, int row, int column) {
		super(length, width, row, column);
		myColors = new Color[] { EMPTYCOLOR, LIVECOLOR };
	}

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
		initNeighbor8();
	}

	@Override
	public void initState() {
		for (int i = 0; i < myRow; i++) {
			for (int j = 0; j < myColumn; j++) {
				if (i == myRow / 2 && j < myColumn / 2 + 6 && j > myColumn / 2 - 5) {
					myGrid[i][j].init(LIVE, myColors[LIVE], NUMNEIGHBOR);
					myUpdatedGrid[i][j] = LIVE;
				} else {
					myGrid[i][j].init(EMPTY, myColors[EMPTY], NUMNEIGHBOR);
					myUpdatedGrid[i][j] = EMPTY;
				}
			}
		}

	}

	@Override
	public void changeState() {
		for (int i = 0; i < myRow; i++) {
			for (int j = 0; j < myColumn; j++) {
				int neighbors = 0;
				for (Cell c : myGrid[i][j].getNeighbors()) {
					if (c.getState() == LIVE) {
						neighbors++;
					}
				}
				if (myGrid[i][j].getState() == EMPTY) {
					if (neighbors == 3) {
						myUpdatedGrid[i][j] = LIVE;
					}
				} else if (myGrid[i][j].getState() == LIVE) {
					if (neighbors < 2 || neighbors > 3) {
						myUpdatedGrid[i][j] = EMPTY;
					}
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
