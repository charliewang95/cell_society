package layout.rule;

import javafx.scene.paint.Color;
import layout.Cell;
import layout.Rule;

public class LifeRule extends Rule {
	private static final int EMPTY = 0;
	private static final int LIVE = 1;
	private static final int NUMNEIGHBOR = 8;
	private static final Color EMPTYCOLOR = Color.LIGHTGREY;
	private static final Color LIVECOLOR = Color.BLACK;
	private String myModel;

	public LifeRule(int cellLength, int row, int column) {
		super(cellLength, row, column);
		myColors = new Color[] { EMPTYCOLOR, LIVECOLOR };
		myModel = "Gosper";
		myCounters = new int[1];
		myLegend = new String[1];
		myLegend[0] = "Live";
	}

	public void initGrid() {
		myGrid = new Cell[myRow][myColumn];
		initBoard(4);
		initState();
		initNeighbor(NUMNEIGHBOR);
	}

	@Override
	public void initState() {
		if (myModel.equals("10Cell")) {
			init10Cell();
		} else if (myModel.equals("Exploder")) {
			initExploder();
		} else if (myModel.equals("Gosper")) {
			initGosper();
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
		myCounters[0] = 0;
		for (int i = 0; i < myRow; i++) {
			for (int j = 0; j < myColumn; j++) {
				myGrid[i][j].setState(myUpdatedGrid[i][j]);
				if (myUpdatedGrid[i][j] == LIVE){
					myCounters[0]++;
				}
				myGrid[i][j].setColor(myColors[myUpdatedGrid[i][j]]);
			}
		}
	}

	private void init10Cell() {
		for (int i = 0; i < myRow; i++) {
			for (int j = 0; j < myColumn; j++) {
				if (i == myRow / 2 && j < myColumn / 2 + 6 && j > myColumn / 2 - 5) {
					myGrid[i][j].init(LIVE, myColors[LIVE], NUMNEIGHBOR);
					myUpdatedGrid[i][j] = LIVE;
					myCounters[0]++;
				} else {
					myGrid[i][j].init(EMPTY, myColors[EMPTY], NUMNEIGHBOR);
					myUpdatedGrid[i][j] = EMPTY;
				}
			}
		}
	}

	private void initExploder() {
		for (int i = 0; i < myRow; i++) {
			for (int j = 0; j < myColumn; j++) {
				myGrid[i][j].init(LIVE, myColors[LIVE], NUMNEIGHBOR);
				if ((i >= myRow / 2 - 2 && i <= myRow / 2 + 2 && j == myColumn / 2 - 2)
						|| (i >= myRow / 2 - 2 && i <= myRow / 2 + 2 && j == myColumn / 2 + 2)
						|| (i == myRow / 2 - 2 && j == myColumn / 2) || (i == myRow / 2 + 2 && j == myColumn / 2)) {
					myGrid[i][j].init(LIVE, myColors[LIVE], NUMNEIGHBOR);
					myUpdatedGrid[i][j] = LIVE;
					myCounters[0]++;
				} else {
					myGrid[i][j].init(EMPTY, myColors[EMPTY], NUMNEIGHBOR);
					myUpdatedGrid[i][j] = EMPTY;
				}
			}
		}
	}

	private void initGosper() {
		int[] xarray = { 8, 8, 8, 8, 9, 9, 9, 9, 10, 10, 10, 10, 10, 10, 11, 11, 11, 11, 12, 12, 12, 12, 13, 13, 14, 15,
				15, 16, 16, 17, 20, 20, 20, 21, 22 };
		int[] yarray = { 33, 34, 44, 45, 32, 34, 44, 45, 10, 11, 19, 20, 32, 33, 10, 11, 18, 20, 18, 19, 26, 27, 26, 28,
				26, 45, 46, 45, 47, 45, 34, 35, 36, 34, 35 };
		for (int i = 0; i < xarray.length; i++) {
			myGrid[xarray[i]][yarray[i]].init(LIVE, myColors[LIVE], NUMNEIGHBOR);
			myUpdatedGrid[xarray[i]][yarray[i]] = LIVE;
			myCounters[0]++;
		}
		for (int i = 0; i < myRow; i++) {
			for (int j = 0; j < myColumn; j++) {
				if (myGrid[i][j].getState()==0) {
					myGrid[i][j].init(EMPTY, myColors[EMPTY], NUMNEIGHBOR);
					myUpdatedGrid[i][j] = EMPTY;
				}
			}
		}
	}

	public void setModel(String model) {
		myModel = model;
	}
	
	public Color[] getColors(){
		return myColors;
	}

}
