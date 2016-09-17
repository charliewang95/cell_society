package layout;

import java.util.ArrayList;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Cell {
	private Rectangle myRec;
	private int myState;
	private ArrayList<Cell> myNeighbor;
	
	public Cell (int x, int y, int length, int width, int state, Color c) {
		myRec = new Rectangle (x, y, length, width);
		init(state, c);
	}
	
	private void init(int state, Color c) {
		myState = state;
		myRec.setFill(c);
		myNeighbor = new ArrayList<Cell>();
	}
	
	public void addNeighbor(Cell cell) {
		myNeighbor.add(cell);
	}
	
	public void setState(int newState) {
		myState = newState;
	}	
	
	public int getState() {
		return myState;
	}
	
	public Rectangle getRec() {
		return myRec;
	}
}
