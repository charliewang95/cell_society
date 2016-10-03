package layout.rule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import javafx.scene.paint.Color;
import layout.Cell;
import layout.Rule;
import layout.rule.agents.Agent;

/**
 * Back-end algorithm class for SugarScape CA
 * 
 * @author Charlie Wang
 *
 */
public class SugarRule extends Rule {
	private static final double RADIUS = 3;
	private static final int[] LEVEL = { 0, 1, 2, 3, 4 };
	private double myPercentage0;
	private double myPercentage1;
	private double myPercentage2;
	private double myPercentage3;
	private double myPercentageAgent;
	private Parameter vision;
	private Parameter metabolism = new Parameter(3, myResources.getString("MetabolismSlider"), 1, 4);
	private int minsugar = 5;
	private int maxsugar = 25;
	private Parameter sugarGrowBackRate = new Parameter(1, myResources.getString("SugarRateSlider"), 1, 4);
	private int sugarGrowBackInterval;
	private int preset;
	private int myNum0;
	private int myNum1;
	private int myNum2;
	private int myNum3;
	private int myNum4;
	private ArrayList<Agent> myAgents;
	private double radius;
	private int myCounter = 0;

	public SugarRule(double cellLength, int row, int column, int neighbor, int side, boolean toro, double[] percent,
			Color[] color, int[] misc) {
		super(cellLength, row, column);
		myNumNeighbor = neighbor;
		mySide = side;
		myToroidal = toro;
		myCounters = new int[1];
		myLegend = new String[1];
		myLegend[0] = myResources.getString("SugarLegendAgent");
		myPercentage0 = percent[0];
		myPercentage1 = percent[1];
		myPercentage2 = percent[2];
		myPercentage3 = percent[3];
		myPercentageAgent = percent[4];
		preset = misc[5];
		if (preset == 1) {
			sugarGrowBackInterval = 1;
			vision = new Parameter(4, myResources.getString("VisionSlider"), 1, 6);
		} else if (preset == 2) {
			sugarGrowBackInterval = 2;
			vision = new Parameter(4, myResources.getString("VisionSlider"), 1, 10);
		} else { // default
			sugarGrowBackInterval = 1;
			vision = new Parameter(4, myResources.getString("VisionSlider"), 1, 6);
		}
		vision.setValue(misc[0]);
		metabolism.setValue(misc[1]);
		minsugar = misc[2];
		maxsugar = misc[3];
		sugarGrowBackRate.setValue(misc[4]);
		parameters.add(vision);
		parameters.add(metabolism);
		parameters.add(sugarGrowBackRate);
		myColors = new Color[] { color[0], color[1], color[2], color[3], color[4] };
		radius = Math.min(RADIUS, cellLength / 4);
	}

	@Override
	public void initGrid() {
		myNum0 = (int) (myRow * myColumn * myPercentage0);
		myNum1 = (int) (myRow * myColumn * myPercentage1);
		myNum2 = (int) (myRow * myColumn * myPercentage2);
		myNum3 = (int) (myRow * myColumn * myPercentage3);
		myNum4 = myRow * myColumn - myNum0 - myNum1 - myNum2 - myNum3;

		myGrid = new Cell[myRow][myColumn];
		myAgents = new ArrayList<Agent>();
		initBoard(mySide);
		initState();
		if (preset == 1) {
			initAgent();
		} else if (preset == 2) {
			initAgent2();
		} else {
			initAgent(); // default
		}
		initNeighbor(myNumNeighbor, myToroidal);
	}

	@Override
	public void initState() {
		ArrayList<Integer> list = makeRandomList(myRow * myColumn);

		for (int k = 0; k < myRow * myColumn; k++) {
			int index = list.get(k);
			int i = index / myColumn;
			int j = index - i * myColumn;

			if (k < myNum0) {
				myGrid[i][j].init(LEVEL[0], myColors[LEVEL[0]]);
				myUpdatedGrid[i][j] = LEVEL[0];
			} else if (k >= myNum0 && k < myNum0 + myNum1) {
				myGrid[i][j].init(LEVEL[1], myColors[LEVEL[1]]);
				myUpdatedGrid[i][j] = LEVEL[1];
			} else if (k >= myNum0 + myNum1 && k < myNum0 + myNum1 + myNum2) {
				myGrid[i][j].init(LEVEL[2], myColors[LEVEL[2]]);
				myUpdatedGrid[i][j] = LEVEL[2];
			} else if (k >= myNum0 + myNum1 + myNum2 && k < myNum0 + myNum1 + myNum2 + myNum3) {
				myGrid[i][j].init(LEVEL[3], myColors[LEVEL[3]]);
				myUpdatedGrid[i][j] = LEVEL[3];
			} else {
				myGrid[i][j].init(LEVEL[4], myColors[LEVEL[4]]);
				myUpdatedGrid[i][j] = LEVEL[4];
			}
		}
	}

	public void initAgent() {
		ArrayList<Integer> list = makeRandomList(myRow * myColumn);
		Random r = new Random();

		for (int k = 0; k < myRow * myColumn * myPercentageAgent; k++) {
			int index = list.get(k);
			int i = index / myColumn;
			int j = index - i * myColumn;
			createAgent(r, i, j);
		}
	}

	public void initAgent2() {
		ArrayList<Integer> list = makeRandomList(myRow * myColumn);
		Random r = new Random();
		int count = 0;
		for (int k = 0; k < list.size() && count <= myRow * myColumn * myPercentageAgent; k++) {
			int index = list.get(k);
			int i = index / myColumn;
			int j = index - i * myColumn;

			if (i >= myRow * (2.0 / 5) && j <= myColumn * (3.0 / 5)) {
				createAgent(r, i, j);
				count++;
			}
		}

	}

	public Agent createAgent(Random r, int i, int j) {
		Agent newAgent = new Agent(myGrid[i][j].getCenterX(), myGrid[i][j].getCenterY(), radius, i, j);
		newAgent.setSugar(r.nextInt(maxsugar - minsugar) + minsugar);
		myAgents.add(newAgent);
		myCounters[0]++;
		return newAgent;
	}

	@Override
	public void changeState() {
		myCounter++;
		moveAgents();
		incrementGround();
	}

	private void incrementGround() {
		if (myCounter >= sugarGrowBackInterval) {
			for (int i = 0; i < myRow; i++) {
				for (int j = 0; j < myColumn; j++) {
					int newState = myGrid[i][j].getState() + (int) sugarGrowBackRate.getValue();
					if (newState > LEVEL[LEVEL.length - 1]) {
						newState = LEVEL[LEVEL.length - 1];
					}
					myGrid[i][j].setState(newState, myColors[newState]);
				}
			}
			myCounter = 0;
		}
	}

	private void moveAgents() {
		myCounters[0] = 0;
		Collections.shuffle(myAgents);
		ArrayList<Agent> toRemove = new ArrayList<Agent>();
		if (myAgents.isEmpty())
			return;
		for (Agent agent : myAgents) {
			myCounters[0]++;
			int row = agent.getRow();
			int col = agent.getCol();
			Cell tempcell = myGrid[row][col];
			int max = tempcell.getState();
			int maxrow = row;
			int maxcol = col;
			Collections.shuffle(tempcell.getNeighbors());
			for (Cell c : tempcell.getNeighbors()) {
				if (c.getState() > max || (c.getState() == max && Math.abs(c.getCol() - agent.getCol()) + Math.abs(c.getRow()
						- agent.getRow()) > Math.abs(maxcol - agent.getCol()) + Math.abs(maxrow - agent.getRow()))) {
					max = c.getState();
					maxrow = c.getRow();
					maxcol = c.getCol();
				}
			}
			agent.getCircle().setCenterX(myGrid[maxrow][maxcol].getCenterX());
			agent.getCircle().setCenterY(myGrid[maxrow][maxcol].getCenterY());
			agent.setSugar(agent.getSugar() + max - (int) metabolism.getValue());
			agent.setRow(maxrow);
			agent.setCol(maxcol);
			myGrid[maxrow][maxcol].setState(LEVEL[0], myColors[LEVEL[0]]);
			if (agent.getSugar() <= 0) {
				toRemove.add(agent);
			}
		}
		for (Agent agent : toRemove) {
			removeAgent(agent);
		}
	}

	public void removeAgent(Agent agent) {
		myCounters[0]--;
		myAgents.remove(agent);
		agent.getCircle().setVisible(false);
	}

	public ArrayList<Agent> getAgent() {
		return myAgents;
	}

	public int getVision() {
		return (int) vision.getValue();
	}
	
	public int getMetabolism() {
		return (int) metabolism.getValue();
	}

	public int getMinSugar() {
		return minsugar;
	}
	
	public int getMaxSugar() {
		return maxsugar;
	}
	
	public int getSugarGrow() {
		return (int) sugarGrowBackRate.getValue();
	}
	
	public int getPreset() {
		return preset;
	}
	
	public boolean getToroidal() {
		return myToroidal;
	}
	
	public double getPercent0() {
		return myPercentage0;
	}
	public double getPercent1() {
		return myPercentage1;
	}
	public double getPercent2() {
		return myPercentage2;
	}
	public double getPercent3() {
		return myPercentage3;
	}
	public double getPercentAgent() {
		return myPercentageAgent;
	}

	
}
