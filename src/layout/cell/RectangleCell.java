package layout.cell;

import java.util.ArrayList;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import layout.Cell;

public class RectangleCell extends Cell{
	/**
	 *    width
	 * ___________   
	 * |		 |
	 * |		 |---> length
	 * |		 |
	 * |		 |
	 * ___________
	 * @param x x-coordinate
	 * @param y y-coordinate
	 * @param width (along x-axis)
	 * @param length (along y-axis)
	 */
	public RectangleCell (double[] x, double[] y, double width, double length, int row, int col) {
		super(x, y, width, length, row, col);
	}
	
	public int getX() {
		return myX;
	}
	
	public int getY() {
		return myY;
	}
}
