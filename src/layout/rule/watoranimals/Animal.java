package layout.rule.watoranimals;

import layout.Cell;

public class Animal extends Cell{

	protected int myReproduce;
	public Animal(int x, int y, int width, int length, int row, int col) {
		super(x, y, width, length, row, col);
		myReproduce = 1;
	}
	
	public int getReproduce() {
		return myReproduce;
	}
	
	public void addReproduce() {
		setReproduce(1);
	}
	
	public void setReproduce(int r) {
		myReproduce = r;
	}

}
