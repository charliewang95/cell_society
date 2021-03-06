package layout.rule;

import java.util.ArrayList;
import java.util.Random;

import javafx.scene.paint.Color;
import layout.Cell;
import layout.Rule;

/**
 * Back-end Algorithm file for Schelling's Segregation model
 * 
 * @author Charlie Wang
 *
 */
public class SchellingRule extends Rule {
	private static final int EMPTY = 0;
	private static final int AAA = 1; // group A
	private static final int BBB = 2; // group B
	private double myPercentageA;
	private double myPercentageEmpty;
	private Parameter mySatisfied; 
	private boolean myToroidal;
	private int myNumA;
	private int myNumB;
	private int myNumE;
	private int[] myAs;
	private int[] myBs;
	private int[] myEs;
	private int[] myEsTMP;

	public SchellingRule(double cellLength, int row, int column, int neighbor, int side, double percentA, double percentEmpty, double satisfy, Color empty, Color aColor, Color bColor, boolean toro) {
		super(cellLength, row, column);
		myColors = new Color[] { empty, aColor, bColor };
		myPercentageA = percentA;
		myPercentageEmpty = percentEmpty;
		myNumNeighbor = neighbor;
		mySide = side;
		myToroidal = toro;
		mySatisfied = new Parameter(satisfy, myResources.getString("SchellingRuleSlider"), 0, 1);
		parameters.add(mySatisfied);
		myCounters = new int[0];
		myLegend = new String[0];
	}

	@Override
	public void initGrid() {
		myNumE = (int) (myRow * myColumn * myPercentageEmpty);
		myNumA = (int) ((myRow * myColumn - myNumE) * myPercentageA);
		myNumB = (int) (myRow * myColumn - myNumE - myNumA);
		myAs = new int[myNumA];
		myBs = new int[myNumB];
		myEs = new int[myNumE];
		myEsTMP = new int[myNumE];
		
		if (myGrid==null) {
			myGrid = new Cell[myRow][myColumn];
			initBoard(mySide);
			initState();
			initNeighbor(myNumNeighbor, myToroidal);
		}
		
	}

	@Override
	public void initState() {
		ArrayList<Integer> list = makeRandomList(myRow * myColumn);
		
		for (int k = 0; k < myRow * myColumn; k++) {
			int index = list.get(k);
			int i = index / myColumn;
			int j = index - i * myColumn;
			
			if (k < myNumA) {
				myGrid[i][j].init(AAA, myColors[AAA]);
				myUpdatedGrid[i][j] = AAA;
				myAs[k] = index;
			} else if (k >= myNumA && k < myNumA + myNumB) {
				myGrid[i][j].init(BBB, myColors[BBB]);
				myUpdatedGrid[i][j] = BBB;
				myBs[k - myNumA] = index;
			} else {
				myGrid[i][j].init(EMPTY, myColors[EMPTY]);
				myUpdatedGrid[i][j] = EMPTY;
				myEs[k - myNumA - myNumB] = index;
				myEsTMP[k - myNumA - myNumB] = index;
			}
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

					if (percent < mySatisfied.getValue()) {
						myUpdatedGrid[i][j] = EMPTY;
						Random random = new Random();
						int r = random.nextInt(myEs.length);
						int chosen = myEs[r];
						
						int a = chosen / myColumn;
						int b = chosen - a * myColumn;
						
						myUpdatedGrid[a][b] = myGrid[i][j].getState();
						myEs[r] = i * myColumn + j;
					}
				}
			}
		}
		System.out.println();
		for (int i = 0; i < myRow; i++) {
			for (int j = 0; j < myColumn; j++) {
				int stateNum = myUpdatedGrid[i][j];
				myGrid[i][j].setState(stateNum, myColors[stateNum]);
			}
		}
		// testByPrintingEachState();
	}

	public void setSatisfied(double satisfied) {
		mySatisfied.setValue(satisfied);
	}
	
	public double getSatisfied() {
		return mySatisfied.getValue();
	}
	
	public void setPercentageA(double percentageA) {
		myPercentageA = percentageA;
	}
	
	public double getPercentageA() {
		return myPercentageA;
	}
	
	public void setPercentageEmpty(double percentageEmpty) {
		myPercentageEmpty = percentageEmpty;
	}
	
	public double getPercentageEmpty() {
		return myPercentageEmpty;
	}

}
