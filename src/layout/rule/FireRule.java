package layout.rule;

import javafx.scene.paint.Color;
import layout.Cell;
import layout.Rule;

public class FireRule extends Rule {
	private static final int EMPTY=0;
	private static final int TREE=1;
	private static final int BURN=2;
	private static final Color EMPTYCOLOR=Color.YELLOW;
	private static final Color TREECOLOR=Color.GREEN;
	private static final Color BURNCOLOR=Color.RED;
	private Cell[][] grid;
	private int myLength;
	private int myWidth;
	private int mySizeX;
	private int mySizeY;
	private int cellLength;
	private int cellWidth;
	private int myState; //0=empty, 1=tree, 2=burning

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
				if (i==0 || i==mySizeX-1 ||j==0||j==mySizeY-1) {
					initState(grid[i][j], EMPTY, EMPTYCOLOR);
				}
				if (i==mySizeX/2 && j==mySizeY/2) {
					initState(grid[i][j], BURN, BURNCOLOR);
				}
				initState(grid[i][j], BURN, BURNCOLOR);
			}
		}
	}
	
	@Override
	public void initState(Cell cell, int state, Color color) {
		cell.init(state, color);
	}
	
	public void changeState() {
		
	}

	@Override
	public boolean endState() {
		return false;
	}
	
}
