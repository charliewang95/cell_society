package layout.rule;

import java.util.Random;
import javafx.scene.paint.Color;
import layout.Cell;
import layout.Rule;

/**
 * Back-end Algorithm for Fire Simulation rule
 * 
 * @author Charlie Wang
 *
 */
public class FireRule extends Rule {
	private static final int EMPTY = 0;
	private static final int TREE = 1;
	private static final int BURN = 2;
	private final int myNumNeighbor;
	private final boolean myToroidal; //new
	//private final boolean specifiedStates;
	private Parameter myProbCatch;

	public FireRule(double cellLength, int row, int column, Color empty, Color tree, Color burn, double probCatch, int neighbor, boolean toro) {
		super(cellLength, row, column);
		myColors = new Color[] { empty, tree, burn };
		myNumNeighbor = neighbor;
		myToroidal = toro;
		//specifiedStates = specify;
		myProbCatch = new Parameter(probCatch, myResources.getString("FireRuleSlider"), 0, 1);
		parameters.add(myProbCatch);
		myCounters = new int[2];
		myLegend = new String[2];
		myLegend[0] = myResources.getString("FireLegendTree");
		myLegend[1] = myResources.getString("FireLegendFire");
	}

	@Override
	public void initGrid() {
		//if myGrid is null, proceed. else, it was already created 
		//through XML
		if (myGrid == null){
			myGrid = new Cell[myRow][myColumn];
			initBoard(myNumNeighbor);
			initState();
			initNeighbor(myNumNeighbor, myToroidal);
		}
		
	}

	@Override
	public void initState() {
		for (int i = 0; i < myRow; i++) {
			for (int j = 0; j < myColumn; j++) {
				if (i == myRow / 2 && j == myColumn / 2) {
					myGrid[i][j].init(BURN, myColors[BURN]);
					myUpdatedGrid[i][j] = BURN;
					myCounters[1]++;
				} else {
					myGrid[i][j].init(TREE, myColors[TREE]);
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
				int stateNum = myUpdatedGrid[i][j];
				myGrid[i][j].setState(stateNum, myColors[stateNum]);
				if (stateNum == TREE)
					myCounters[0]++;
				else if (stateNum == BURN)
					myCounters[1]++;
			}
		}
	}


	public void setProbCatch(double probcatch) {
		myProbCatch.setValue(probcatch);
	}

}
