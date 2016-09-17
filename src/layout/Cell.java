package layout;

import javafx.scene.shape.Rectangle;

public class Cell {
	Rectangle myRec;
	
	public Cell (int x, int y, int length, int width) {
		myRec = new Rectangle (x, y, length, width);
	}
}
