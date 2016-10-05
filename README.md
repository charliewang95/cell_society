README
=======

Joy Kim, Noah Over, Charlie Wang
September 15 - October 2 

* Each person’s role in developing the project: 
 * Charlie Wang - Simulation
 * Joy Kim - XML and configuration
 * Noah Over - UI

### Resources Used

* http://www.w3schools.com/xml/ for XML
* https://docs.oracle.com/javase/tutorial/jaxp/dom/index.html
* Javadocs on LineChart, Slider, TextField, NumberAxis, etc.

### File Used to Start Program

* SocietyMain.java

### Files Used to Test Project

Within the data/xml folder:
* FireRule.xml
* FireRuleOutput.xml (the output file after testing the XMLWriter)
* FireRuleTry.xml
* LifeRule.xml
* LifeRuleOutput.xml
* SchellingRule.xml
* SchellingRuleOutput.xml
* SugarRule.xml
* WatorRule.xml

### Data/Resource Files Required by the Program

* resources/English.properties (package is resources, properties file is English.properties)
* xml.properties/Rule.properties (package is xml.properties, properties file is Rule.properties)

### Information About Using the Program

* Directly after running the program, the StartScreen will pop up. In it, there will be a text box that asks for the name of the file you wish to run. The “.xml” extension is optional, it will add it for you if it is not detected. There is also the choice of using the file browser to find your own file by clicking the “Browse” button. 
* When the simulation initially pops up, it will be paused so you have to hit the play button to start it.
* You may change the parameters of certain simulations using the sliders.
* In the rules, clicking on a cell with a mouse changes the state of that cell. It rotates. For example, in WatorRule, if you click on an empty cell, it will change into a fish; if you click on a fish, it will change into a shark; if you click on a shark, it will change into an empty cell.
* When you want to do a SugarRule simulation, a button appears that says Agent Control On/Off. This controls mouse input for this simulation. Initially, the mouse input changes the state of the patch that you click on by adding 1 sugar to it. If you click the button though, then clicking on a patch will either add an agent if one is not already present or remove the current agent. This will revert to changing the amount of sugar in the patch if you press the button again.
* You can change simulations by typing in the TextField or hitting the Browse button under “Replace Current Simulation:”, which will replace the current simulation in the same window, or by typing in the TextField or hitting the Browse button under “New Window”, which will open the new simulation in a new window. 

### Explanation of Parameters 
NOTE: Items with "*" at the front can be changed using sliders after the simulation begins
##### Schelling's Segregation
* nameRule (String): String to input at start screen (restriction: SchellingRule)
* title (String): The title of the screen
* cellLength (double): a cell’s side length
* xSize (int): number of columns (restriction: >0)
* ySize (int): number of rows (restriction: >0)
* numNeighbor (int): number of neighbors around each cell (restriction: {3, 4, 6, 8}; default: triangle: 3; rectangle: 4 or 8; hexagon: 6)
* numSide (int): number of each cell’s sides (restriction: triangle: 3; rectangle: 4; hexagon: 6)
* toroidal (boolean): whether the neighbor setup use the toroidal rule
* rateA (double): percentage of Group A cells among all the non-empty cells (restriction: [0,1])
* rateEmpty (double): percentage of empty cells of all cells (restriction: [0,1])
* *satisfied (double): the ratio between the number of a cell’s neighbors of the same group and the number of all non-empty neighbors, above which the cell won’t move. (restriction: [0,1])
* emptyColor (Color) : the color of empty cells
* aaaColor (Color) : the color of Group A cells
* bbbColor (Color) : the color of Group B cells

##### Spreading of Fire
* nameRule (String): String to input at start screen (restriction: FireRule)
* title (String): The title of the screen
* cellLength (double): a cell’s side length
* xSize (int): number of columns (restriction: >0)
* ySize (int): number of rows (restriction: >0)
* numNeighbor (int): number of neighbors around each cell (restriction: {3, 4, 6, 8}; default: triangle: 3; rectangle: 4 or 8; hexagon: 6)
* numSide (int): number of each cell’s sides (restriction: triangle: 3; rectangle: 4; hexagon: 6)
* toroidal (boolean): whether the neighbor setup use the toroidal rule
* *probCatch (double): the probability that a tree cell beside a fire cell catches fire (restriction: [0,1])
* emptyColor (Color) : the color of empty cells
* treeColor (Color) : the color of tree cells
* burnColor (Color) : the color of cells on fire

##### Wa-tor
* nameRule (String): String to input at start screen (restriction: WatorRule)
* title (String): The title of the screen
* cellLength (double): a cell’s side length
* xSize (int): number of columns (restriction: >0)
* ySize (int): number of rows (restriction: >0)
* numNeighbor (int): number of neighbors around each cell (restriction: {3, 4, 6, 8}; default: triangle: 3; rectangle: 4 or 8; hexagon: 6)
* numSide (int): number of each cell’s sides (restriction: triangle: 3; rectangle: 4; hexagon: 6)
* toroidal (boolean): whether the neighbor setup use the toroidal rule
* percentWater (double): percentage of empty cells among all cells (restriction: [0,1])
* percentFish (double): percentage of fish among all non-empty cells (restriction: [0,1])
* *fishReproduce (int): steps to take before a fish reproduces (restriction: [1,20])
* *sharkReproduce (int): steps to take before a shark reproduces (restriction: [1,20])
* *sharkDeath (int): steps to take before a shark starves to death (restriction: [1,20])
* emptyColor (Color) : the color of empty cells
* fishColor (Color) : the color of fish cells
* sharkColor (Color) : the color of shark cells

##### Game of Life 
* NOTE: only rectangles with 4 neighbors make sense for this rule, but other shapes and # of neighbors can show up on screens
* nameRule (String): String to input at start screen (restriction: LifeRule)
* title (String): The title of the screen
* cellLength (double): a cell’s side length
* xSize (int): number of columns (restriction: >0)
* ySize (int): number of rows (restriction: >0)
* numNeighbor (int): number of neighbors around each cell (restriction: {3, 4, 6, 8}; default: triangle: 3; rectangle: 4 or 8; hexagon: 6)
* numSide (int): number of each cell’s sides (restriction: triangle: 3; rectangle: 4; hexagon: 6)
* toroidal (boolean): whether the neighbor setup use the toroidal rule
* emptyColor (Color) : the color of empty cells
* liveColor (Color) : the color of living cells
* typeLife (String): the model for Game of Life to run (restriction: {10Cell, Exploder, Gosper})

##### SugarScape
* nameRule (String): String to input at start screen (restriction: LifeRule)
* title (String): The title of the screen
* cellLength (double): a cell’s side length
* xSize (int): number of columns (restriction: >0)
* ySize (int): number of rows (restriction: >0)
* toroidal (boolean): whether the neighbor setup use the toroidal rule
* numNeighbor (int): number of neighbors around each cell (restriction: {3, 4, 6, 8}; default: triangle: 3; rectangle: 4 or 8; hexagon: 6)
* numSide (int): number of each cell’s sides (restriction: triangle: 3; rectangle: 4; hexagon: 6)
* myPercentage0 (double): percentage of sugar level 0 (lowest) among all grids (restriction: [0,1])
* myPercentage1 (double): percentage of sugar level 1 among all grids (restriction: [0,1])
* myPercentage2 (double): percentage of sugar level 2 among all grids (restriction: [0,1])
* myPercentage3 (double): percentage of sugar level 3 among all grids (restriction: [0,1])
* NOTE: myPercentage0+myPercentage1+myPercentage2+myPercentage3<=1 and myPercentage4 is calculated
* color0 (Color):  the color of level 0 sugar cells
* color1 (Color):  the color of level 1 sugar cells
* color2 (Color):  the color of level 2 sugar cells
* color3 (Color):  the color of level 3 sugar cells
* color4 (Color):  the color of level 4 sugar cells
* *vision (int): the maximum distance (in row/column, not coordinates) an agent can reach for sugar each step (restriction: preset 1: [1, 6]; preset 2: [1, 10])
* *metabolism (int): the number of sugar an agent has to lose for each step (restriction: [1, 4];)
* minSugar (int): the minimum number of sugar an agent can initially contain (default: 5)
* maxSugar (int): the minimum number of sugar an agent can initially contain (default: 25)
* *sugarGrowBackRate (int): number of sugar each cell gains at the end of each step (default: 1)
* preset (int): the preset number; each preset has slightly different rules and/or landscape set-up (restriction: [1,2])

### Any Known Bugs, Crashes, or Problems with Functionality

* We did not get the Output XML File Button to work in Wator Rule or Sugar Rule.
* We did not get triangles or hexagons to work for LifeRule because they do not really make sense for that simulation.
* Whenever you create an XML file from a simulation for a rule using the button, it overwrites any previous xml files for that rule you may have created because it does not create a new name for the new one.

### Impressions of the Assignment

* The assignment was pretty straightforward and that was good because it made it clear what was expected from us. 