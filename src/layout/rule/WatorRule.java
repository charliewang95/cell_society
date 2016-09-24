package layout.rule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import javafx.scene.paint.Color;
import layout.Cell;
import layout.Rule;
import layout.rule.watoranimals.Animal;

public class WatorRule extends Rule {
	private static final int WATER = 0;
	private static final int FISH = 1;
	private static final int SHARK = 2;
	private static final int NUMNEIGHBOR = 4;
	private static final int FISHREPRODUCERATE = 1; // parameter
	private static final int SHARKREPRODUCERATE = 20; // parameter
	private static final int SHARKDEATHRATE = 5; // parameter
	private static final double PERCENTAGEWATER = 0.9; // parameter
	private static final double PERCENTAGEFISH = 0.98; // parameter
	private static final Color WATERCOLOR = Color.LIGHTBLUE;
	private static final Color FISHCOLOR = Color.GREEN;
	private static final Color SHARKCOLOR = Color.ORANGE;
	private Color[] myColors;
	private int[][] myUpdatedGrid;
	private int[][] myUpdatedReproduceGrid;
	private int[][] myUpdatedHealthGrid;

	public WatorRule(int length, int width, int sizeX, int sizeY) {
		super(length, width, sizeX, sizeY);
		myColors = new Color[] { WATERCOLOR, FISHCOLOR, SHARKCOLOR };
	}
	
	private class TempGrid{
		int tempState;
		int tempReproduce;
		int tempHealth;
		private TempGrid(int state, int reproduce, int health) {
			tempState=state;
			tempReproduce=reproduce;
			tempHealth=health;
		}
	}
	
	@Override
	public void initGrid() {
		myGrid = new Cell[myRow][myColumn];
		myUpdatedGrid = new int[myRow][myColumn];
		myUpdatedReproduceGrid = new int[myRow][myColumn];
		myUpdatedHealthGrid = new int[myRow][myColumn];
		initState();
		initNeighbor4();
	}

	@Override
	public void initState() {
		ArrayList<Integer> list = makeRandomList(myRow * myColumn);
		int initWater = (int) (myRow * myColumn * PERCENTAGEWATER);
		int initFish = (int) ((myRow * myColumn - initWater) * PERCENTAGEFISH);
		int initShark = myRow * myColumn - initWater - initFish;
		for (int k = 0; k < myRow * myColumn; k++) {
			int index = list.get(k);
			int i = index / myRow;
			int j = index - i * myRow;
			int x = cellWidth * j;
			int y = cellLength * i;
			if (k < initWater) {
				myGrid[i][j] = new Cell(x, y, cellWidth, cellLength, i, j);
				myGrid[i][j].init(WATER, myColors[WATER], NUMNEIGHBOR);
				myUpdatedGrid[i][j] = WATER;
			} else if (k >= initWater && k < initWater + initFish) {
				myGrid[i][j] = new Animal(x, y, cellWidth, cellLength, i, j);
				myGrid[i][j].init(FISH, myColors[FISH], NUMNEIGHBOR);
				if (myGrid[i][j] instanceof Animal) {
					((Animal) myGrid[i][j]).setReproduce(FISHREPRODUCERATE);
				}
				myUpdatedGrid[i][j] = FISH;
			} else {
				myGrid[i][j] = new Animal(x, y, cellWidth, cellLength, i, j);
				myGrid[i][j].init(SHARK, myColors[SHARK], NUMNEIGHBOR);
				if (myGrid[i][j] instanceof Animal) {
					((Animal) myGrid[i][j]).setReproduce(SHARKREPRODUCERATE);
					((Animal) myGrid[i][j]).setHealth(SHARKDEATHRATE);
				}
				myUpdatedGrid[i][j] = SHARK;
			}
		}
	}

	private ArrayList<Integer> makeRandomList(int top) {
		ArrayList<Integer> list = new ArrayList<>(top);
		for (int i = 0; i < top; i++) {
			list.add(i);
		}
		Collections.shuffle(list);
		return list;
	}

	@Override
	public void changeState() {
		for (int i = 0; i < myRow; i++) {
			for (int j = 0; j < myColumn; j++) {
				Cell cell = myGrid[i][j];
				if (cell instanceof Animal && cell.getState() == SHARK) {
					changeStateShark(cell);
				}

				// if (((Fish) cell).getReproduce() == FISHREPRODUCERATE) {
				// Random random = new Random();
				// int randomInt = random.nextInt(NUMNEIGHBOR);
				// }
			}
		}
	}

	private void changeStateShark(Cell cell) {
		ArrayList<Cell> eat = new ArrayList<Cell>();
		ArrayList<Cell> vacant = new ArrayList<Cell>();
		for (Cell c : cell.getNeighbors()) {
			if (c.getState() == FISH) {
				eat.add(c);
			} else if (c.getState() == WATER) {
				vacant.add(c);
			}
		}
		if (eat.size() != 0) {
			Random random = new Random();
			int randomInt = random.nextInt(eat.size());
			Cell fishGetEaten = eat.get(randomInt);
			myUpdatedGrid[fishGetEaten.getRow()][fishGetEaten.getCol()] = SHARK;
			myUpdatedGrid[cell.getRow()][cell.getCol()] = needReproduce(cell) ? SHARK : WATER;
			myUpdatedReproduceGrid[fishGetEaten.getRow()][fishGetEaten.getCol()] = ((Animal) cell).getReproduce() - 1;
			myUpdatedHealthGrid[fishGetEaten.getRow()][fishGetEaten.getCol()] = ((Animal) cell).getHealth() - 1;
		} else if (vacant.size() != 0) {
			Random random = new Random();
			int randomInt = random.nextInt(vacant.size());
			Cell toMove = eat.get(randomInt);
			myUpdatedGrid[toMove.getRow()][toMove.getCol()] = SHARK;
			myUpdatedGrid[cell.getRow()][cell.getCol()] = WATER;
		}
	}

	private boolean needReproduce(Cell cell) {
		if (cell instanceof Animal) {
			cell = (Animal) cell;
			return ((Animal) cell).getReproduce() == 0;
		}
		return false;
	}

}
