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
	
	private int myFishReproduceRate = 8;
	private int mySharkReproduceRate = 12;
	private int mySharkDeathRate = 10;
	private double myPercentageWater = 0.98;
	private double myPercentageFish = 0.95;
	
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
		initRec();
		initState();
		initNeighbor4();
	}

	@Override
	public void initRec() {
		super.initRec();
		
	}
	
	@Override
	public void initState() {
		ArrayList<Integer> list = makeRandomList(myRow * myColumn);
		int initWater = (int) (myRow * myColumn * myPercentageWater);
		int initFish = (int) ((myRow * myColumn - initWater) * myPercentageFish);
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
				int randomInt = random.nextInt(myFishReproduceRate);
				
				myGrid[i][j] = new Animal(x, y, cellWidth, cellLength, i, j);
				myGrid[i][j].init(FISH, myColors[FISH], NUMNEIGHBOR);
				if (myGrid[i][j] instanceof Animal) {
					((Animal) myGrid[i][j]).setReproduce(randomInt);
				}
				myUpdatedGrid[i][j].tempState = FISH;
				myUpdatedGrid[i][j].tempReproduce = randomInt;
			} else {
				Random random = new Random();
				int randomInt = random.nextInt(myFishReproduceRate);
				
				myGrid[i][j] = new Animal(x, y, cellWidth, cellLength, i, j);
				myGrid[i][j].init(SHARK, myColors[SHARK], NUMNEIGHBOR);
				if (myGrid[i][j] instanceof Animal) {
					((Animal) myGrid[i][j]).setReproduce(randomInt);
					((Animal) myGrid[i][j]).setHealth(mySharkDeathRate);
				}
				myUpdatedGrid[i][j].tempState = SHARK;
				myUpdatedGrid[i][j].tempReproduce = randomInt;
				myUpdatedGrid[i][j].tempHealth = mySharkDeathRate;
			}
		}
	}

	//neighbors can roll over across board
	public void initNeighborUp(int i, int j) {
		if (i != 0) {
			myGrid[i][j].addNeighbor(myGrid[i - 1][j]);
		} else {
			myGrid[i][j].addNeighbor(myGrid[myRow - 1][j]);
		}
	}

	public void initNeighborLeft(int i, int j) {
		if (j != 0) {
			myGrid[i][j].addNeighbor(myGrid[i][j - 1]);
		} else {
			myGrid[i][j].addNeighbor(myGrid[i][myColumn - 1]);
		}
	}

	public void initNeighborRight(int i, int j) {
		if (j != myColumn - 1) {
			myGrid[i][j].addNeighbor(myGrid[i][j + 1]);
		} else {
			myGrid[i][j].addNeighbor(myGrid[i][0]);
		}
	}

	public void initNeighborDown(int i, int j) {
		if (i != myRow - 1) {
			myGrid[i][j].addNeighbor(myGrid[i + 1][j]);
		} else {
			myGrid[i][j].addNeighbor(myGrid[0][j]);
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
			updateReproduce(cell, fishGetEaten);
			// since it eats a fish, its health resets
			myUpdatedGrid[fishGetEaten.getRow()][fishGetEaten.getCol()].tempHealth = mySharkDeathRate;

		} else if (vacant.size() != 0) {
			Random random = new Random();
			int randomInt = random.nextInt(vacant.size());
			Cell toMove = vacant.get(randomInt);

			// shark moves to new location
			myUpdatedGrid[toMove.getRow()][toMove.getCol()].tempState = SHARK;
			// one less round for them to reproduce
			updateReproduce(cell, toMove);
			// one more day to their death
			myUpdatedGrid[toMove.getRow()][toMove.getCol()].tempHealth = ((Animal) cell).getHealth() - 1;
		} else {
			myUpdatedGrid[cell.getRow()][cell.getCol()].tempHealth--;
			updateReproduce(cell, cell);
			return;
		}
		// if reproduce, leave a child
		myUpdatedGrid[cell.getRow()][cell.getCol()].tempState = needReproduce(cell) ? SHARK : WATER;
		// if reproduce, reset reproduce rate
		myUpdatedGrid[cell.getRow()][cell.getCol()].tempReproduce = needReproduce(cell) ? mySharkReproduceRate
				: Integer.MAX_VALUE;
		// if reproduce, reset health
		myUpdatedGrid[cell.getRow()][cell.getCol()].tempHealth = needReproduce(cell) ? mySharkDeathRate
				: Integer.MAX_VALUE;
	}

	private void updateReproduce(Cell celltocheck, Cell celltooperate) {
		if (needReproduce(celltocheck)) { // if it needs to reproduce, reset its
			// reproduce rate
			myUpdatedGrid[celltooperate.getRow()][celltooperate.getCol()].tempReproduce = mySharkReproduceRate;
		} else { // if not, --1
			myUpdatedGrid[celltooperate.getRow()][celltooperate.getCol()].tempReproduce = ((Animal) celltocheck).getReproduce() - 1;
		}
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
				myUpdatedGrid[toMove.getRow()][toMove.getCol()].tempReproduce = myFishReproduceRate;
			} else {
				myUpdatedGrid[toMove.getRow()][toMove.getCol()].tempReproduce = ((Animal) cell).getReproduce() - 1;
			}
		} else {
			if (needReproduce(cell)) { // if it needs to reproduce, reset its
				// reproduce rate
				myUpdatedGrid[cell.getRow()][cell.getCol()].tempReproduce = myFishReproduceRate;
			} else { // if not, --1
				myUpdatedGrid[cell.getRow()][cell.getCol()].tempReproduce = ((Animal) cell).getReproduce() - 1;
			}
			return;
		}
		// if reproduce, leave a child
		myUpdatedGrid[cell.getRow()][cell.getCol()].tempState = needReproduce(cell) ? FISH : WATER;
		// if reproduce, reset reproduce rate
		myUpdatedGrid[cell.getRow()][cell.getCol()].tempReproduce = needReproduce(cell) ? myFishReproduceRate
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
		myFishReproduceRate = fishReproduce;
	}

	public void setSharkReproduce(int sharkReproduce) {
		mySharkReproduceRate = sharkReproduce;
	}

	public void setSharkDeath(int sharkDeath) {
		mySharkDeathRate = sharkDeath;
	}

	public void setPercentageWater(double percentageWater) {
		myPercentageWater = percentageWater;
	}

	public void setPercentageFish(double percentageFish) {
		myPercentageFish = percentageFish;
	}
}
