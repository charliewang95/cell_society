package layout.rule;

import java.util.Random;
import javafx.scene.paint.Color;
import layout.Cell;
import layout.Rule;

public class FireRule extends Rule {
	private static final int EMPTY = 0;
	private static final int TREE = 1;
	private static final int BURN = 2;
	private static final int NUMNEIGHBOR = 6;
	private Parameter myProbCatch; // parameter
	private static final Color EMPTYCOLOR = Color.YELLOW;
	private static final Color TREECOLOR = Color.GREEN;
	private static final Color BURNCOLOR = Color.RED;

	public FireRule(double cellLength, int row, int column) {
		super(cellLength, row, column);
		myColors = new Color[] { EMPTYCOLOR, TREECOLOR, BURNCOLOR };
		myProbCatch = new Parameter(0.5, myResources.getString("FireRuleSlider"), 0, 1);
		parameters.add(myProbCatch);
		myCounters = new int[2];
	}

	@Override
	public void initGrid() {
		myGrid = new Cell[myRow][myColumn];
		initBoard(NUMNEIGHBOR);
		initState();
		initNeighbor(NUMNEIGHBOR);
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
					myCounters[1]++;
				} else {
					myGrid[i][j].init(TREE, myColors[TREE], NUMNEIGHBOR);
					myUpdatedGrid[i][j] = TREE;
					myCounters[0]++;
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
						if (random.nextDouble() < myProbCatch.getValue() && c.getState() == TREE) {
							myUpdatedGrid[c.getRow()][c.getCol()] = BURN;
						}
					}
					myUpdatedGrid[i][j] = EMPTY;
				}
			}
		}
		myCounters[0] = 0;
		myCounters[1] = 0;
		for (int i = 0; i < myRow; i++) {
			for (int j = 0; j < myColumn; j++) {
				myGrid[i][j].setState(myUpdatedGrid[i][j]);
				if (myUpdatedGrid[i][j] == TREE)
					myCounters[0]++;
				else if (myUpdatedGrid[i][j] == BURN)
					myCounters[1]++;
				myGrid[i][j].setColor(myColors[myUpdatedGrid[i][j]]);
			}
		}
	}

	public void setProbCatch(double probcatch) {
		myProbCatch.setValue(probcatch);
	}

}
