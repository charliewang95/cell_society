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
	private double myPercentageA; // parameter
	private double myPercentageEmpty; // parameter
	private double mySatisfied; // parameter
	private static final Color EMPTYCOLOR = Color.WHITE;
	private static final Color AAACOLOR = Color.RED;
	private static final Color BBBCOLOR = Color.BLUE;
	private Color[] myColors;
	private int myNumA;
	private int myNumB;
	private int myNumE;
	private int[] myAs;
	private int[] myBs;
	private int[] myEs;
	private int[] myEsTMP;

	public SchellingRule(int length, int width, int row, int column) {
		super(length, width, row, column);
		myColors = new Color[] { EMPTYCOLOR, AAACOLOR, BBBCOLOR };
		myPercentageA = 0.1;
		myPercentageEmpty = 0.1;
		mySatisfied = 0.7;
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
		
		initRec();
		initState();
		initNeighbor8();
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

					if (percent < mySatisfied) {
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
		for (int i = 0; i < myRow; i++) {
			for (int j = 0; j < myColumn; j++) {
				myGrid[i][j].setState(myUpdatedGrid[i][j]);
				myGrid[i][j].setColor(myColors[myUpdatedGrid[i][j]]);
			}
		}
		// testByPrintingEachState();
	}

	public void setSatisfied(double satisfied) {
		mySatisfied = satisfied;
	}
	
	public void setPercentageA(double percentageA) {
		myPercentageA = percentageA;
	}
	
	public void setPercentageEmpty(double percentageEmpty) {
		myPercentageEmpty = percentageEmpty;
	}
}
