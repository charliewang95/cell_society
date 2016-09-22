package layout.rule;

import javafx.scene.paint.Color;
import layout.Cell;
import layout.Rule;

public class SchellingRule extends Rule{
	private static final int EMPTY = 0;
	private static final int AAA = 1; //group A
	private static final int BBB = 2; // group B
	private static final Color EMPTYCOLOR = Color.YELLOW;
	private static final Color AAACOLOR = Color.GREEN;
	private static final Color BBBCOLOR = Color.RED;
	private Color[] myColors;
	private Cell[][] myGrid;
	private int[][] myUpdatedGrid;
	private boolean ended;
	
	public SchellingRule(int length, int width, int sizeX, int sizeY) {
		super(length, width, sizeX, sizeY);
		myColors=new Color[]{EMPTYCOLOR, AAACOLOR, BBBCOLOR};
		ended = false;
	}

	@Override
	public void changeState() {
		return;
	}

	@Override
	public void initGrid() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean endState() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void initState(int i, int j) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initNeighbor(int i, int j) {
		// TODO Auto-generated method stub
		
	}

}
