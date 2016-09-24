package layout;

import java.util.ArrayList;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Cell {
	private Rectangle myRec;
	private int myState;
	private Color myColor;
	private int myRow;
	private int myCol;
	private int myX;
	private int myY;
	private int myWidth;
	private int myLength;
	private ArrayList<Cell> myNeighbors;
	private int myNumNeighbors;
	
	/**
	 *    width
	 * ___________   
	 * |		 |
	 * |		 |---> length
	 * |		 |
	 * |		 |
	 * ___________
	 * @param x x-coordinate
	 * @param y y-coordinate
	 * @param width (along x-axis)
	 * @param length (along y-axis)
	 */
	public Cell (int x, int y, int width, int length, int row, int col) {
		myRec = new Rectangle (x, y, width, length);
		myRow = row;
		myCol = col;
		myState = 0;
		myColor = null;
		myX = x;
		myY = y;
		myWidth = width;
		myLength = length;
	}
	
	/**
	 * Initialize the cell according to the rule
	 * @param state the cell's initial state
	 * @param c the rectangle's initial color
	 */
	public void init(int state, Color c, int num) {
		myState = state;
		myRec.setFill(c);
		myNeighbors = new ArrayList<Cell>();
		myNumNeighbors = num;
	}
	
	/**
	 * Add all the neighbors of this cell according to the rule
	 * @param cell the neighbor cell
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
	 * @param newState the cell's new state
	 */
	public void setState(int newState) {
		myState = newState;
	}	
	
	/**
	 * Set the cell's new color
	 * @param newState the cell's new color
	 */
	public void setColor(Color newcolor) {
		myColor = newcolor;
		myRec.setFill(newcolor);
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
	public Rectangle getRec() {
		return myRec;
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
	
	public int getX() {
		return myX;
	}
	
	public int getY() {
		return myY;
	}
	
	public int getWidth() {
		return myWidth;
	}
	
	public int getLength() {
		return myLength;
	}
}
