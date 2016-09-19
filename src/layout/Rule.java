package layout;

public abstract class Rule {
	private Cell[][] grid;
	private int myLength;
	private int myWidth;
	private int mySizeX;
	private int mySizeY;

	public Rule(int length, int width, int sizeX, int sizeY) {
		myLength = length;
		myWidth = width;
		mySizeX = sizeX;
		mySizeY = sizeY;
	}

	public abstract Cell[][] changeState();
}
