package layout.rule.watoranimals;

import layout.Cell;

public class Animal extends Cell {

	protected int myReproduce;
	protected int myHealth;

	public Animal(double[] x, double[] y, double width, double length, int row, int col) {
		super(x, y, width, length, row, col);
	}

	public int getReproduce() {
		return myReproduce;
	}

	public void addReproduce() {
		myReproduce--;
	}

	public void setReproduce(int r) {
		myReproduce = r;
	}

	public void setHealth(int h) {
		myHealth = h;
	}
	
	public void addHealth() {
		myHealth--;
	}
	
	public int getHealth() {
		return myHealth;
	}
	
}
