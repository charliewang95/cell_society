package layout.rule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import javafx.scene.paint.Color;
import layout.Cell;
import layout.Rule;
import layout.rule.watoranimals.Animal;

/**
 * Backend Class for Wa-tor Simulation Rule
 * 
 * @author Charlie Wang
 *
 */
public class WatorRule extends Rule {
	private static final int WATER = 0;
	private static final int FISH = 1;
	private static final int SHARK = 2;
	private static final double LIFEMIN = 1;
	private static final double LIFEMAX = 20;
	private final boolean myToroidal; // new
	private int myNumNeighbor;

	private Parameter myFishReproduceRate;
	private Parameter mySharkReproduceRate;
	private Parameter mySharkDeathRate;

	private double myPercentageWater;
	private double myPercentageFish;

	private TempGrid[][] myUpdatedGrid;

	public WatorRule(double cellLength, int sizeX, int sizeY, int neighbor, Color water, Color fish, Color shark,
			double fishBirth, double sharkBirth, double sharkDeath, double percentWater, double percentFish,
			boolean toro) {
		super(cellLength, sizeX, sizeY);
		myColors = new Color[] { water, fish, shark };
		myToroidal = toro;
		myFishReproduceRate = new Parameter(fishBirth, myResources.getString("FishReproductionSlider"), LIFEMIN,
				LIFEMAX);
		mySharkReproduceRate = new Parameter(sharkBirth, myResources.getString("SharkReproductionSlider"), LIFEMIN,
				LIFEMAX);
		mySharkDeathRate = new Parameter(sharkDeath, myResources.getString("SharkDeathSlider"), LIFEMIN, LIFEMAX);

		myNumNeighbor = neighbor;

		myPercentageWater = percentWater;
		myPercentageFish = percentFish;
		parameters.add(myFishReproduceRate);
		parameters.add(mySharkDeathRate);
		parameters.add(mySharkReproduceRate);
		myCounters = new int[2];
		myLegend = new String[2];
		myLegend[0] = "Fish";
		myLegend[1] = "Shark";
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
		myGrid = new Animal[myRow][myColumn];
		initBoard(myNumNeighbor);
		myUpdatedGrid = new TempGrid[myRow][myColumn];

		for (int i = 0; i < myRow; i++) {
			for (int j = 0; j < myColumn; j++) {
				myUpdatedGrid[i][j] = new TempGrid();
			}
		}
		initState();
		initNeighbor(myNumNeighbor, myToroidal);
	}

	@Override
	public void initState() {
		ArrayList<Integer> list = makeRandomList(myRow * myColumn);
		int initWater = (int) (myRow * myColumn * myPercentageWater);
		int initFish = (int) ((myRow * myColumn - initWater) * myPercentageFish);

		for (int k = 0; k < myRow * myColumn; k++) {
			int index = list.get(k);
			int i = index / myColumn;
			int j = index - i * myColumn;

			if (k < initWater) {
				myGrid[i][j].init(WATER, myColors[WATER]);
				myUpdatedGrid[i][j].tempState = WATER;
			} else if (k >= initWater && k < initWater + initFish) {
				Random random = new Random();
				int randomInt = random.nextInt((int) myFishReproduceRate.getValue());

				myGrid[i][j].init(FISH, myColors[FISH]);
				if (myGrid[i][j] instanceof Animal) {
					((Animal) myGrid[i][j]).setReproduce(randomInt);
				}
				myUpdatedGrid[i][j].tempState = FISH;
				myUpdatedGrid[i][j].tempReproduce = randomInt;
				myCounters[0]++;
			} else {
				Random random = new Random();
				int randomInt = random.nextInt((int) myFishReproduceRate.getValue());

				myGrid[i][j].init(SHARK, myColors[SHARK]);
				if (myGrid[i][j] instanceof Animal) {
					((Animal) myGrid[i][j]).setReproduce(randomInt);
					((Animal) myGrid[i][j]).setHealth((int) mySharkDeathRate.getValue());
				}
				myUpdatedGrid[i][j].tempState = SHARK;
				myUpdatedGrid[i][j].tempReproduce = randomInt;
				myUpdatedGrid[i][j].tempHealth = (int) mySharkDeathRate.getValue();
				myCounters[1]++;
			}
		}
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
		myCounters[0] = 0;
		myCounters[1] = 0;
		for (int i = 0; i < myRow; i++) {
			for (int j = 0; j < myColumn; j++) {
				int stateNum = myUpdatedGrid[i][j].tempState;
				myGrid[i][j].setState(stateNum, myColors[stateNum]);
				if (stateNum == FISH)
					myCounters[0]++;
				else if (stateNum == SHARK)
					myCounters[1]++;
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
			myUpdatedGrid[fishGetEaten.getRow()][fishGetEaten.getCol()].tempHealth = (int) mySharkDeathRate.getValue();

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
		myUpdatedGrid[cell.getRow()][cell.getCol()].tempReproduce = needReproduce(cell)
				? (int) mySharkReproduceRate.getValue() : Integer.MAX_VALUE;
		// if reproduce, reset health
		myUpdatedGrid[cell.getRow()][cell.getCol()].tempHealth = needReproduce(cell) ? (int) mySharkDeathRate.getValue()
				: Integer.MAX_VALUE;
	}

	private void updateReproduce(Cell celltocheck, Cell celltooperate) {
		if (needReproduce(celltocheck)) { // if it needs to reproduce, reset its
			// reproduce rate
			myUpdatedGrid[celltooperate.getRow()][celltooperate.getCol()].tempReproduce = (int) mySharkReproduceRate
					.getValue();
		} else { // if not, --1
			myUpdatedGrid[celltooperate.getRow()][celltooperate.getCol()].tempReproduce = ((Animal) celltocheck)
					.getReproduce() - 1;
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
				myUpdatedGrid[toMove.getRow()][toMove.getCol()].tempReproduce = (int) myFishReproduceRate.getValue();
			} else {
				myUpdatedGrid[toMove.getRow()][toMove.getCol()].tempReproduce = ((Animal) cell).getReproduce() - 1;
			}
		} else {
			if (needReproduce(cell)) { // if it needs to reproduce, reset its
				// reproduce rate
				myUpdatedGrid[cell.getRow()][cell.getCol()].tempReproduce = (int) myFishReproduceRate.getValue();
			} else { // if not, --1
				myUpdatedGrid[cell.getRow()][cell.getCol()].tempReproduce = ((Animal) cell).getReproduce() - 1;
			}
			return;
		}
		// if reproduce, leave a child
		myUpdatedGrid[cell.getRow()][cell.getCol()].tempState = needReproduce(cell) ? FISH : WATER;
		// if reproduce, reset reproduce rate
		myUpdatedGrid[cell.getRow()][cell.getCol()].tempReproduce = needReproduce(cell)
				? (int) myFishReproduceRate.getValue() : Integer.MAX_VALUE;
	}

	private boolean needReproduce(Cell cell) {
		if (cell instanceof Animal) {
			cell = (Animal) cell;
			return ((Animal) cell).getReproduce() == 0;
		}
		return false;
	}

	public void setFishReproduce(int fishReproduce) {
		myFishReproduceRate.setValue(fishReproduce);
	}

	public void setSharkReproduce(int sharkReproduce) {
		mySharkReproduceRate.setValue(sharkReproduce);
	}

	public void setSharkDeath(int sharkDeath) {
		mySharkDeathRate.setValue(sharkDeath);
	}

	public void setPercentageWater(double percentageWater) {
		myPercentageWater = percentageWater;
	}

	public void setPercentageFish(double percentageFish) {
		myPercentageFish = percentageFish;
	}

}
