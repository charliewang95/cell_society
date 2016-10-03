package layout.rule.agents;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Object class used specifically for SugarRule
 * 
 * @author Charlie Wang
 *
 */
public class Agent {
	private Circle myCircle;
	private int mySugar;
	private int myRow;
	private int myColumn;

	public Agent(double x, double y, double r, int row, int col) {
		myCircle = new Circle(x, y, r);
		myCircle.setFill(Color.RED);
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
	
	public void setRow(int row){
		myRow = row;
	}
	
	public int getCol() {
		return myColumn;
	}
	
	public void setCol(int col){
		myColumn = col;
	}
}
