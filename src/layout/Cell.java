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
	protected int myX;
	protected int myY;

	protected ArrayList<Cell> myNeighbors;
	protected int myNumNeighbors;

	public Cell(double[] x, double[] y, int row, int col) {
		myShape = new Polygon();
		for (int i = 0; i < x.length; i++) {
			myShape.getPoints().addAll(x[i], y[i]);
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
	public void init(int state, Color c, int num) {
		myState = state;
		myShape.setFill(c);
		myNeighbors = new ArrayList<Cell>();
		myNumNeighbors = num;
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

}
