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
	private static final int TRIANGLE_SIDES = 3;
	private static final int RECTANGLE_SIDES = 4;
	private static final int HEXAGON_SIDES = 6;
	
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
		if (x.length == TRIANGLE_SIDES) {
			myCenterX = Math.max(x[0], x[2]);
			myCenterY = ((y[0] + y[2]) / 2);
		}
		if (x.length == RECTANGLE_SIDES) {
			myCenterX = ((x[0] + x[1]) / 2);
			myCenterY = ((y[0] + y[3]) / 2);
		}
		if (x.length == HEXAGON_SIDES) {
			myCenterX = ((x[0] + x[1]) / 2);
			myCenterY = (y[5]);
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
