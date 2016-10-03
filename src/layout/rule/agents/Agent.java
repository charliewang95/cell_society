package layout.rule.agents;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Agent {
	private Circle myCircle;
	private int mySugar;
<<<<<<< HEAD
	private int myMetabolism;
	private int myVision;
	private boolean myOcupied;
	private int myRow;
	private int myColumn;

	public Agent(double x, double y, double r, int row, int col, int metabolism, int vision, boolean occupied) {
		myCircle = new Circle(x, y, r);
		myCircle.setFill(Color.RED);
		myMetabolism = metabolism;
		myVision = vision;
		myOcupied = occupied;
=======
	private int myRow;
	private int myColumn;

	public Agent(double x, double y, double r, int row, int col) {
		myCircle = new Circle(x, y, r);
		myCircle.setFill(Color.RED);
>>>>>>> df54dc9ba10ba15a1ca1b577f2e693f88f19bcf5
		myRow = row;
		myColumn = col;
	}

	public void init(int sugar) {
		mySugar = sugar;
	}

	public void setSugar(int sugar) {
		mySugar = sugar;
	}
	
	public int getSugar() {
		return mySugar;
	}
	
	public Circle getCircle() {
		return myCircle;
	}
	
	public int getRow() {
		return myRow;
	}
	
	public int getCol() {
		return myColumn;
	}
}
