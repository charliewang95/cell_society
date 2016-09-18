package layout;

public abstract class Rule {
	private Cell[][] grid;
	private int myLength;
	private int myWidth;
	private int mySizeX;
	private int mySizeY;
	
	public Rule () {
		
	}
	
	public abstract Cell[][] changeState();
}
