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
	private static final Color WATERCOLOR = Color.LIGHTBLUE;
	private static final Color FISHCOLOR = Color.GREEN;
	private static final Color SHARKCOLOR = Color.ORANGE;
	
	private static int FISHREPRODUCERATE = 8;
	private static int SHARKREPRODUCERATE = 12;
	private static int SHARKDEATHRATE = 10;
	private static double PERCENTAGEWATER = 0.98;
	private static double PERCENTAGEFISH = 0.95;
	
	private Color[] myColors;
	private TempGrid[][] myUpdatedGrid;

	public WatorRule(int length, int width, int sizeX, int sizeY) {
		super(length, width, sizeX, sizeY);
		myColors = new Color[] { WATERCOLOR, FISHCOLOR, SHARKCOLOR };
	}

	private class TempGrid {
		int tempState;
		int tempReproduce;
		int tempHealth;

		private TempGrid() {
			tempState = 0;
			tempReproduce = Integer.MAX_VALUE;
			tempHealth = Integer.MAX_VALUE;
		}

		private TempGrid(int state, int reproduce, int health) {
			tempState = state;
			tempReproduce = reproduce;
			tempHealth = health;
		}
	}

	@Override
	public void initGrid() {
		myGrid = new Cell[myRow][myColumn];
		myUpdatedGrid = new TempGrid[myRow][myColumn];
		for (int i = 0; i < myRow; i++) {
			for (int j = 0; j < myColumn; j++) {
				myUpdatedGrid[i][j] = new TempGrid();
			}
		}
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
				myGrid[i][j] = new Animal(x, y, cellWidth, cellLength, i, j);
				myGrid[i][j].init(WATER, myColors[WATER], NUMNEIGHBOR);
				myUpdatedGrid[i][j].tempState = WATER;
			} else if (k >= initWater && k < initWater + initFish) {
				Random random = new Random();
				int randomInt = random.nextInt(FISHREPRODUCERATE);
				
				myGrid[i][j] = new Animal(x, y, cellWidth, cellLength, i, j);
				myGrid[i][j].init(FISH, myColors[FISH], NUMNEIGHBOR);
				if (myGrid[i][j] instanceof Animal) {
					((Animal) myGrid[i][j]).setReproduce(randomInt);
				}
				myUpdatedGrid[i][j].tempState = FISH;
				myUpdatedGrid[i][j].tempReproduce = randomInt;
			} else {
				Random random = new Random();
				int randomInt = random.nextInt(FISHREPRODUCERATE);
				
				myGrid[i][j] = new Animal(x, y, cellWidth, cellLength, i, j);
				myGrid[i][j].init(SHARK, myColors[SHARK], NUMNEIGHBOR);
				if (myGrid[i][j] instanceof Animal) {
					((Animal) myGrid[i][j]).setReproduce(randomInt);
					((Animal) myGrid[i][j]).setHealth(SHARKDEATHRATE);
				}
				myUpdatedGrid[i][j].tempState = SHARK;
				myUpdatedGrid[i][j].tempReproduce = randomInt;
				myUpdatedGrid[i][j].tempHealth = SHARKDEATHRATE;
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
			}
		}

		copyGrid();
		for (int i = 0; i < myRow; i++) {
			for (int j = 0; j < myColumn; j++) {
				Cell cell = myGrid[i][j];
				if (cell instanceof Animal && cell.getState() == FISH) {
					changeStateFish(cell);
				}
			}
		}
		copyGrid();
	}

	private void copyGrid() {
		for (int i = 0; i < myRow; i++) {
			for (int j = 0; j < myColumn; j++) {
				myGrid[i][j].setState(myUpdatedGrid[i][j].tempState);
				myGrid[i][j].setColor(myColors[myUpdatedGrid[i][j].tempState]);
				if (myGrid[i][j].getState() == FISH) {
					((Animal) myGrid[i][j]).setReproduce(myUpdatedGrid[i][j].tempReproduce);
				}
				if (myGrid[i][j].getState() == SHARK) {
					((Animal) myGrid[i][j]).setReproduce(myUpdatedGrid[i][j].tempReproduce);
					((Animal) myGrid[i][j]).setHealth(myUpdatedGrid[i][j].tempHealth);
				}
			}
		}
	}
	
	private void changeStateShark(Cell cell) {
		ArrayList<Cell> eat = new ArrayList<Cell>();
		ArrayList<Cell> vacant = new ArrayList<Cell>();
		if (((Animal) cell).getHealth() == 0) { // if dead, turn it to water
			myUpdatedGrid[cell.getRow()][cell.getCol()].tempState = 0;
			return;
		}

		for (Cell c : cell.getNeighbors()) { // add fish list and vacant list
			if (c.getState() == FISH) {
				eat.add(c);
			} else if (c.getState() == WATER) {
				vacant.add(c);
			}
		}

		if (eat.size() != 0) { // if fish present near shark
			Random random = new Random();
			int randomInt = random.nextInt(eat.size());
			Cell fishGetEaten = eat.get(randomInt);
			
			// shark moves to new location
			myUpdatedGrid[fishGetEaten.getRow()][fishGetEaten.getCol()].tempState = SHARK;
			if (needReproduce(cell)) { // if it needs reproduce, reset its
										// reproduce rate
				
				myUpdatedGrid[fishGetEaten.getRow()][fishGetEaten.getCol()].tempReproduce = SHARKREPRODUCERATE;
			} else { // if not, --1
				myUpdatedGrid[fishGetEaten.getRow()][fishGetEaten.getCol()].tempReproduce = ((Animal) cell)
						.getReproduce() - 1;
			}
			// since it eats a fish, its health resets
			myUpdatedGrid[fishGetEaten.getRow()][fishGetEaten.getCol()].tempHealth = SHARKDEATHRATE;

		} else if (vacant.size() != 0) {
			Random random = new Random();
			int randomInt = random.nextInt(vacant.size());
			Cell toMove = vacant.get(randomInt);

			// shark moves to new location
			myUpdatedGrid[toMove.getRow()][toMove.getCol()].tempState = SHARK;
			// one less round for them to reproduce
			myUpdatedGrid[toMove.getRow()][toMove.getCol()].tempReproduce = ((Animal) cell).getReproduce() - 1;
			// one more day to their death
			myUpdatedGrid[toMove.getRow()][toMove.getCol()].tempHealth = ((Animal) cell).getHealth() - 1;
		} else {
			myUpdatedGrid[cell.getRow()][cell.getCol()].tempHealth--;
			if (needReproduce(cell)) { // if it needs to reproduce, reset its
				// reproduce rate
				myUpdatedGrid[cell.getRow()][cell.getCol()].tempReproduce = SHARKREPRODUCERATE;
			} else { // if not, --1
				myUpdatedGrid[cell.getRow()][cell.getCol()].tempReproduce = ((Animal) cell).getReproduce() - 1;
			}
			return;
		}
		// if reproduce, leave a child
		myUpdatedGrid[cell.getRow()][cell.getCol()].tempState = needReproduce(cell) ? SHARK : WATER;
		// if reproduce, reset reproduce rate
		myUpdatedGrid[cell.getRow()][cell.getCol()].tempReproduce = needReproduce(cell) ? SHARKREPRODUCERATE
				: Integer.MAX_VALUE;
		// if reproduce, reset health
		myUpdatedGrid[cell.getRow()][cell.getCol()].tempHealth = needReproduce(cell) ? SHARKDEATHRATE
				: Integer.MAX_VALUE;
	}

	private void changeStateFish(Cell cell) {
		ArrayList<Cell> vacant = new ArrayList<Cell>();

		for (Cell c : cell.getNeighbors()) { // add fish list and vacant list
			if (c.getState() == WATER) {
				vacant.add(c);
			}
		}

		if (vacant.size() != 0) {
			Random random = new Random();
			int randomInt = random.nextInt(vacant.size());
			Cell toMove = vacant.get(randomInt);
			myUpdatedGrid[toMove.getRow()][toMove.getCol()].tempState = FISH;
			if (needReproduce(cell)) {
				myUpdatedGrid[toMove.getRow()][toMove.getCol()].tempReproduce = FISHREPRODUCERATE;
			} else {
				myUpdatedGrid[toMove.getRow()][toMove.getCol()].tempReproduce = ((Animal) cell).getReproduce() - 1;
			}
		} else {
			if (needReproduce(cell)) { // if it needs to reproduce, reset its
				// reproduce rate
				myUpdatedGrid[cell.getRow()][cell.getCol()].tempReproduce = FISHREPRODUCERATE;
			} else { // if not, --1
				myUpdatedGrid[cell.getRow()][cell.getCol()].tempReproduce = ((Animal) cell).getReproduce() - 1;
			}
			return;
		}
		// if reproduce, leave a child
		myUpdatedGrid[cell.getRow()][cell.getCol()].tempState = needReproduce(cell) ? FISH : WATER;
		// if reproduce, reset reproduce rate
		myUpdatedGrid[cell.getRow()][cell.getCol()].tempReproduce = needReproduce(cell) ? FISHREPRODUCERATE
				: Integer.MAX_VALUE;
	}

	private boolean needReproduce(Cell cell) {
		if (cell instanceof Animal) {
			cell = (Animal) cell;
			return ((Animal) cell).getReproduce() == 0;
		}
		return false;
	}

	public void setFishReproduce(int fishReproduce) {
		FISHREPRODUCERATE = fishReproduce;
	}

	public void setSharkReproduce(int sharkReproduce) {
		SHARKREPRODUCERATE = sharkReproduce;
	}

	public void setSharkDeath(int sharkDeath) {
		SHARKDEATHRATE = sharkDeath;
	}

	public void setPercentageWater(double percentageWater) {
		PERCENTAGEWATER = percentageWater;
	}

	public void setPercentageFish(double percentageFish) {
		PERCENTAGEFISH = percentageFish;
	}
}
