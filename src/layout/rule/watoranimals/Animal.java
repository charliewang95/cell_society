package layout.rule.watoranimals;

import layout.Cell;

/**
 * Used specifically for Wa-tor simulation.
 * Contains reproduction rate and health data
 * 
 * @author Charlie Wang
 *
 */
public class Animal extends Cell {

	protected int myReproduce;
	protected int myHealth;

	public Animal(double[] x, double[] y, int row, int col) {
		super(x, y, row, col);
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
