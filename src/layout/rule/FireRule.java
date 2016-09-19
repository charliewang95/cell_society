package layout.rule;

import layout.Cell;
import layout.Rule;

public class FireRule extends Rule {
	private Cell[][] grid;
	private int myLength;
	private int myWidth;
	private int mySizeX;
	private int mySizeY;
	private int cellLength;
	private int cellWidth;

	public FireRule(int length, int width, int sizeX, int sizeY) {
		super(length, width, sizeX, sizeY);
	}

	public void initGrid() {
		grid = new Cell[mySizeX][mySizeY];
		for (int i = 0; i < mySizeX; i++) {
			for (int j = 0; j < mySizeY; j++) {
				int x = cellLength * i;
				int y = cellWidth * j;
				grid[i][j] = new Cell(x, y, cellLength, cellWidth);
			}
		}
	}
	
	@Override
	public void initState() {
		
	}
	
	public void changeState() {

	}

	@Override
	public boolean endState() {
		return false;
	}

}
