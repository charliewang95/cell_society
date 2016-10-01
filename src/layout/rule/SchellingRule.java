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
	private static final int NUMNEIGHBOR = 6;
	private double myPercentageA; // parameter
	private double myPercentageEmpty; // parameter
	private Parameter mySatisfied; // parameter
	private static final Color EMPTYCOLOR = Color.WHITE;
	private static final Color AAACOLOR = Color.RED;
	private static final Color BBBCOLOR = Color.BLUE;
	private int myNumA;
	private int myNumB;
	private int myNumE;
	private int[] myAs;
	private int[] myBs;
	private int[] myEs;
	private int[] myEsTMP;

	public SchellingRule(int cellLength, int row, int column) {
		super(cellLength, row, column);
		myColors = new Color[] { EMPTYCOLOR, AAACOLOR, BBBCOLOR };
		myPercentageA = 0.1;
		myPercentageEmpty = 0.1;
		mySatisfied = new Parameter(0.7, myResources.getString("SchellingRuleSlider"), 0, 1);
		parameters.add(mySatisfied);
		myCounters = new int[0];
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
		myGrid = new Cell[myRow][myColumn];
		initBoard(NUMNEIGHBOR);
		initState();
		initNeighbor(NUMNEIGHBOR);
	}

	@Override
	public void initState() {
		ArrayList<Integer> list = makeRandomList(myRow * myColumn);
		
		for (int k = 0; k < myRow * myColumn; k++) {
			int index = list.get(k);
			int i = index / myColumn;
			int j = index - i * myColumn;
			
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
				myGrid[i][j].setState(myUpdatedGrid[i][j]);
				myGrid[i][j].setColor(myColors[myUpdatedGrid[i][j]]);
			}
		}
		// testByPrintingEachState();
	}

	public void setSatisfied(double satisfied) {
		mySatisfied.setValue(satisfied);
	}
	
	public void setPercentageA(double percentageA) {
		myPercentageA = percentageA;
	}
	
	public void setPercentageEmpty(double percentageEmpty) {
		myPercentageEmpty = percentageEmpty;
	}

}
