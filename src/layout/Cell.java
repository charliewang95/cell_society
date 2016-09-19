package layout;

import java.util.ArrayList;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Cell {
	private Rectangle myRec;
	private int myState;
	private ArrayList<Cell> myNeighbor;
	
	public Cell (int x, int y, int length, int width) {
		myRec = new Rectangle (x, y, length, width);
	}
	
	/**
	 * Initialize the cell according to the rule
	 * @param state the cell's initial state
	 * @param c the rectangle's initial color
	 */
	public void init(int state, Color c) {
		myState = state;
		myRec.setFill(c);
		myNeighbor = new ArrayList<Cell>();
	}
	
	/**
	 * Add all the neighbors of this cell according to the rule
	 * @param cell the neighbor cell
	 */
	public void addNeighbor(Cell cell) {
		myNeighbor.add(cell);
	}
	
	/**
	 * Set the cell's new state
	 * @param newState the cell's new state
	 */
	public void setState(int newState) {
		myState = newState;
	}	
	
	/**
	 * Set the cell's new color
	 * @param newState the cell's new color
	 */
	public void setColor(int newState) {
		myState = newState;
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
	public Rectangle getRec() {
		return myRec;
	}
}
