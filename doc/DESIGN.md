Design
======

### Names

> Charlie Wang (qw42)  
Noah Over (nko2)  
Joy Kim (jmk77)  

### Introduction 

* The problem our team is trying to solve by writing this program is to be able to animate any two-dimensional grid cellular automata, CA, simulation.  

* The primary design goals of the project will be to make code that is easy to add new features to and work with a wide variety of different CA simulations. The primary architecture of the design will have some open and some closed architecture.  

* The open architecture will be mainly the classes dealing with different features that our simulation offers with an easy way to add a new feature as well as the code that implements the different type of neighbor rules to add more unique rules to the simulation that may not have been originally thought of.   

### Overview 

* The default package will contain the main class, _SocietyMain_. The main method will be in the _SocietyMain_. The layout package will contain the _Playground_ class and _Rule_ interface. The layout.rules package will contain the subclass files for the different simulations that extend the _Rule_ class.  

* The main class will have a start() method that calls the _Playground_ Class, which has an _init()_ method that will set up all the visual scenes needed for the program such as start screen, buttons to choose the simulation and user input boxes to take in the player’s choice of size of grid. The _Playground_ class will create a 2-D array of _Cell_ objects that will represent the cells containing states represented by squares with color. The _Playground_ class will be in charge of maintaining the states by calling a _Rule_ object.  

* The _Rule_ interface is used to keep track of the rules that each cell is to contain for each different simulation. So, each simulation will be able to extend Rule and create their own classes for the rules of their simulation. The _Rule_ class will determine the state of the current cells and determine the states of the next generation. The _Cell_ class will contain the visual object (square) and the cell's state.  

![Component Map](images/ComponentMap.jpg)

### User Interface

* The user interface will use JavaFx's Stage and Scene classes to create an interface for the user to enter their desired size and set of rules into. It will appear with the title of the interface at the top. Something like "Cell Society: A Cellular Automata Simulation" will work. It will also have two TextFields. One will be used for the user to enter the size they would like their simulation to be and the other will be for the user to enter the file name for the XML file that contains the rules they would like to use for their simulation. For the TextFields, the user will just have to type in their input and hit enter to submit. Also any errors with their input will be pointed out to them by text at the bottom of the screen. As the project goes, we will add new features to the program, so we might have to update our user interface correspondingly.  

* Here is a really rough sketch of what the user interface could look like:

![Here is my sketch](images/UserInterfaceDrawing.jpg)

### Design Details

* When the _SocietyMain.java start()_ method calls _Playground.play()_, a 2D array of Cells will be created. The width and height of the screen will be a fixed number but that will help determine the size of each displayed rectangle of a cell. That is to say, when the user chooses the size of the square board, 8x8 or 100x100, the actual height and width of each individual cell will be proportional with respect to that size and the size of the screen.  

* Then, an array containing all the possible _Rule_ objects will be created, and the program will select a rule based on user’s choice. The simulation is enacted based on the rules of neighboring cells using the _applyRule()_ method. The _applyRule()_ method should be able to check the states of the neighboring cells (up, down, left, right, every diagonal direction) based on the indices of the 2D array and through the _getState(row, column)_ method. Depending on the current states of the neighboring cells the cell will use the _changeState()_ method to change its state as well as keep track of any necessary information needed for the simulation state. The getState(row, column) method itself can return the cell’s current state, which would probably be used during the _applyRule()_ method because some of the simulations’ rules also depend on the current cell’s current state.  

* If the current cell happens to be an “edge” cell, meaning there are no neighboring cells on at least one side of the current cell, the nature of the 2D array will allow the program to ignore the non-existing neighbors and not consider them in the algorithm to calculate and apply the rule.  

* The _Playground_ class is in charge of updating all the states as well as redrawing it. Using a possible _Rule.endState()_ boolean method, a while loop will be sustained throughout the entire simulation until that end state has been reached, in which case the _endState()_ method that was initially false will become true. The above mentioned _applyRule()_ and _changeState()_ methods will be used in this while loop. The _redraw()_ method will redraw each Rectangle object that represents a _Cell_ within the 2D array.   

* The _Cell_ Class should be simple, and it will have some basic methods. To set a cell’s state using _Rule_’s changeState(), we have a corresponding method in _Cell_, _setState(), which changes the state according to the rule. The _setState()_ might call the _setColor()_ method which will changes the color of the square embodied in this _Cell_ object. The _getState()_ method returns the state that this cell is currently in. This is for the _Rule_ object to check the states a cell’s neighbors to decide whether to change this cell’s state. The _getRec()_ method returns the rectangle object for _Playground_  to redraw. Also, each _Cell_ object will hold an x-coordinate and a y-coordinate, which are calculated based on the size of the map and its indices.  

* Each _Rule_ object will also have a list of parameters. Since it's _Rule_ an interface, it can't know exactly how many parameters each object has, so each rule will contain and control its own parameters. When the user wants to change a parameter, find the rule object and apply several setParameter() methods to change the required rule parameter. When the user wants to change the rule, completely, after receiving the request, the _Playground_ object will replace the current _Rule_ object with a new Rule object, and then use Rule.init() to reset the board.

### Design Considerations

1. How to compute the locations of the cell. Suppose the user inputs a size of 500x500 and a size for the grids 20x30. Then the height for each cell should be 500/20=25, and the length of each cell should be 500/30=16. For instance, the location of the third cell (the rectangle) on the top row is then (32, 0), and the location of the fifth cell (the rectangle) on the fourth row is then (64, 75).
2. Leads to the computing of the locations of the neighbors into the ArrayList (may want to discuss more on the structure used for containing neighbors). Take the first example above (the (64, 75) cell) whose indices are (4,5). Its neighbors are (3,4), (3,5), (3,6), (4,4), (4,6), (5,4), (5,5), (5,6). Generally, for a cell (m,n), its neighbors are (m-1,n-1), (m-1,n), (m-1,n+1), (m,n-1), (m,n+1), (m+1,n-1), (m+1,n), (m+1,n+1). If the cell itself is on the border, we can check if the indices are out of bound as we go along.
3. The algorithms of the rules of the different simulations. Determine the flexibility in the number of total states each simulation can have (whether they can be different or if they all happen to have the same amount). 
4. The format we want the XML file to be.
5. Buttons - parameters, location, organization, which structure they will be contained in.
6. One button in particular: the changing of the simulations in the middle of a simulation, without having to exit or restart the program. 
7. The addition/flexibility of allowing users to manipulate the simulation through parameters. For example, the use of sliders in the WaTor simulation in determining the starting number of fish and sharks, the breeding and survival time period for the fish and sharks, the speed of the animation. 

### Team Responsibilities

The project can be roughly divided into the following sections:  
UI (buttons), XML reading, Main Class, Playground (Game) class -- workflow, Cell, Rule interface, Rule 1 (Schelling), Rule 2 (Wator), Rule 3 (fire). The rough division of labor is as follows: 
  
* Charlie: Rule 3, Cell, Rule interface

* Joy: Rule 2, Main Class, Playground

* Noah: Rule 1, UI, XML reading


