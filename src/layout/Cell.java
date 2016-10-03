package layout;

import java.util.ArrayList;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

/**
 * The Cell class contains the state, color, and the actual shape that's going
 * to be displayed on the screen.
 * 
 * @author Charlie Wang
 *
 */
public class Cell {
	protected Polygon myShape;
	protected int myState;
	protected Color myColor;
	protected int myRow;
	protected int myCol;
	protected double myCenterX = 0;
	protected double myCenterY = 0;

	protected ArrayList<Cell> myNeighbors;

	public Cell(double[] x, double[] y, int row, int col) {
		myShape = new Polygon();
		for (int i = 0; i < x.length; i++) {
			myShape.getPoints().addAll(x[i], y[i]);
		}
		if (x.length == 3) {
			myCenterX = Math.max(x[0], x[2]);
			myCenterY = ((y[0] + y[2]) / 2);
		}
		if (x.length == 4) {
			myCenterX = ((x[0] + x[1]) / 2);
			myCenterY = ((y[0] + y[3]) / 2);
		}
		if (x.length == 6) {
			myCenterX = ((x[0] + x[1]) / 2);
			myCenterY = (x[5]);
		}
		myRow = row;
		myCol = col;
		myState = 0;
		myColor = null;

	}

	/**
	 * Initialize the cell according to the rule
	 * 
	 * @param state
	 *            the cell's initial state
	 * @param c
	 *            the rectangle's initial color
	 */
	public void init(int state, Color c) {
		myState = state;
		myShape.setFill(c);
		myNeighbors = new ArrayList<Cell>();
	}

	/**
	 * Add a neighbor of this cell according to the rule
	 * 
	 * @param cell
	 *            the neighbor cell
	 */
	public void addNeighbor(Cell cell) {
		myNeighbors.add(cell);
	}

	/**
	 * @return the neighbor arraylists
	 */
	public ArrayList<Cell> getNeighbors() {
		return myNeighbors;
	}

	/**
	 * Set the cell's new state
	 * 
	 * @param newState
	 *            the cell's new state
	 */
	public void setState(int newState, Color newColor) {
		myState = newState;
		myColor = newColor;
		myShape.setFill(newColor);
	}
<<<<<<< HEAD

//	/**
//	 * Set the cell's new color
//	 * 
//	 * @param newState
//	 *            the cell's new color
//	 */
//	public void setColor(Color newcolor) {
//		myColor = newcolor;
//		myShape.setFill(newcolor);
//	}
=======
>>>>>>> df54dc9ba10ba15a1ca1b577f2e693f88f19bcf5

	/**
	 * @return the cell's color
	 */
	public Color getColor() {
		return myColor;
	}

	/**
	 * @return the cell's current state
	 */
	public int getState() {
		return myState;
	}

	/**
	 * @return the rectangle object
	 */
	public Shape getShape() {
		return myShape;
	}

	/**
	 * @return the row number specified by the rule
	 */
	public int getRow() {
		return myRow;
	}

	/**
	 * @return the column number specified by the rule
	 */
	public int getCol() {
		return myCol;
	}

	public double getCenterX() {
		return myCenterX;
	}

	public double getCenterY() {
		return myCenterY;
	}

}
