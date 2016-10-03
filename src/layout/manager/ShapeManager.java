package layout.manager;

import layout.Cell;
import layout.Rule;
import layout.rule.WatorRule;
import layout.rule.watoranimals.Animal;

/**
 * 
 * This class help the rule class initialize its shape and board size
 * 
 * @author Charlie Wang
 *
 */
public class ShapeManager {
	private double myLength;
	private double myWidth;
	private double myCellLength;
	private Cell[][] myGrid;
	private int myRow;
	private int myColumn;
	private int mySide;
	private Rule myRule;
	
	public ShapeManager(int row, int col, double cellLength) {
		myRow=row;
		myColumn=col;
		myCellLength = cellLength;
	}
	
	public void init(int numSide, Cell[][] grid, Rule rule) {
		myGrid = grid;
		mySide = numSide;
		myRule = rule;
	}
	
	public Cell[][] getGrid() {
		return myGrid;
	}
	
	public void chooseMethod() {
		if (mySide == 3) {
			initTri();
		} else if (mySide == 4) {
			initRec();
		} else if (mySide == 6) {
			initHex();
		}
	}
	
	private void initRec() {
		myWidth = myCellLength * myColumn;
		myLength = myCellLength * myRow;

		for (int i = 0; i < myRow; i++) {
			for (int j = 0; j < myColumn; j++) {
				double x1 = myCellLength * j;
				double y1 = myCellLength * i;
				double x2 = myCellLength * (j + 1);
				double y2 = myCellLength * i;
				double x3 = myCellLength * (j + 1);
				double y3 = myCellLength * (i + 1);
				double x4 = myCellLength * (j);
				double y4 = myCellLength * (i + 1);
				if (myRule instanceof WatorRule) {
					myGrid[i][j] = new Animal(new double[] { x1, x2, x3, x4 }, new double[] { y1, y2, y3, y4 }, i, j);
				} else {
					myGrid[i][j] = new Cell(new double[] { x1, x2, x3, x4 }, new double[] { y1, y2, y3, y4 }, i, j);
				}
			}
		}
	}

	private void initTri() {
		myWidth = myCellLength * (myColumn + 1) / 2;
		myLength = (myCellLength * Math.sqrt(3) / 2) * myRow;
		
		for (int i = 0; i < myRow; i++) {
			for (int j = 0; j < myColumn; j++) {
				double x1, x2, x3, y1, y2, y3;
				if ((i + j) % 2 == 0) {
					x1 = (myCellLength / 2) * j;
					y1 = (myCellLength * Math.sqrt(3) / 2) * i;
					x2 = (myCellLength / 2) * (j + 2);
					y2 = (myCellLength * Math.sqrt(3) / 2) * i;
					x3 = (myCellLength / 2) * (j + 1);
					y3 = (myCellLength * Math.sqrt(3) / 2) * (i + 1);
				} else {
					x1 = (myCellLength / 2) * (j + 1);
					y1 = (myCellLength * Math.sqrt(3) / 2) * i;
					x2 = (myCellLength / 2) * (j + 2);
					y2 = (myCellLength * Math.sqrt(3) / 2) * (i + 1);
					x3 = (myCellLength / 2) * (j);
					y3 = (myCellLength * Math.sqrt(3) / 2) * (i + 1);
				}
				if (myRule instanceof WatorRule) {
					myGrid[i][j] = new Animal(new double[] { x1, x2, x3 }, new double[] { y1, y2, y3 }, i, j);
				} else {
					myGrid[i][j] = new Cell(new double[] { x1, x2, x3 }, new double[] { y1, y2, y3 }, i, j);
				}
			}
		}
	}

	private void initHex() {
		myWidth = myCellLength * (3.0 / 2) * (myColumn) + myCellLength / 2;
		myLength = myCellLength * (Math.sqrt(3)) * myRow + myCellLength * (Math.sqrt(3)) / 2;
		
		for (int i = 0; i < myRow; i++) {
			for (int j = 0; j < myColumn; j++) {
				double[] x = new double[6];
				double[] y = new double[6];
				x[0] = (myCellLength / 2) * (3 * j + 1);
				x[1] = (myCellLength / 2) * (3 * j + 3);
				x[2] = (myCellLength / 2) * (3 * j + 4);
				x[3] = (myCellLength / 2) * (3 * j + 3);
				x[4] = (myCellLength / 2) * (3 * j + 1);
				x[5] = (myCellLength / 2) * (3 * j + 0);
				if (j % 2 == 0) {
					y[0] = (myCellLength / 2 * Math.sqrt(3)) * (2 * i);
					y[1] = (myCellLength / 2 * Math.sqrt(3)) * (2 * i);
					y[2] = (myCellLength / 2 * Math.sqrt(3)) * (2 * i + 1);
					y[3] = (myCellLength / 2 * Math.sqrt(3)) * (2 * i + 2);
					y[4] = (myCellLength / 2 * Math.sqrt(3)) * (2 * i + 2);
					y[5] = (myCellLength / 2 * Math.sqrt(3)) * (2 * i + 1);
				} else {
					y[0] = (myCellLength / 2 * Math.sqrt(3)) * (2 * i + 1);
					y[1] = (myCellLength / 2 * Math.sqrt(3)) * (2 * i + 1);
					y[2] = (myCellLength / 2 * Math.sqrt(3)) * (2 * i + 2);
					y[3] = (myCellLength / 2 * Math.sqrt(3)) * (2 * i + 3);
					y[4] = (myCellLength / 2 * Math.sqrt(3)) * (2 * i + 3);
					y[5] = (myCellLength / 2 * Math.sqrt(3)) * (2 * i + 2);
				}
				if (myRule instanceof WatorRule) {
					myGrid[i][j] = new Animal(x, y, i, j);
				} else {
					myGrid[i][j] = new Cell(x, y, i, j);
				}
			}
		}
	}
	
	public double getWidth() {
		return myWidth;
	}
	
	public double getLength() {
		return myLength;
	}
	
}
