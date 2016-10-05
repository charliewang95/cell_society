DESIGN
======

### High Level Design Goals

* Our highest level goal is to use Cellular Automata to simulate the movement of cells under certain rules. Rules are represented in different classes that all extends the same super class _Rule_. Each rule will have its own algorithm to initialize states and change states. The parameters necessary for running the simulations are imported by reading XML files. Certain XML factory classes and parser classes are used to pick out the parameters from the XML file and pass them into the _Rule_ object. To show the users how the cell moves, a great visualization and UI must be created. Besides the grid representing all the cells, sliders, buttons, input boxes are also added into the UI screen to enhance user experience and the flexibility of the simulations.

### Details on How To Add New Features to the Project

* To add anything new to the UI, you just need to call the method in _UIObjectPlacer_ that would add that thing, whether it is a Button, TextField, Slider, etc., to the screen in either _StartScreen_ or _Playground_, depending on where you want the new feature to show up, in that class’s init method or one of the methods called by init that would be appropriate, like _setUpButtons_ in Playground for example. For example, if you wanted to add a new Button that controls some new feature, you would write _myPlacer.addButton(double x, double y, String message, EventHandler<ActionEvent>);_ except you would replace the parameters I wrote above with the values that you want.

* To add new simulations:

 * First add your new Rule java class under the package “layout.rule”. Due to the nature of the interface, the rule constructor must include setting up the use of super constructor with parameters _cellLength_, _row_, and _column_. _myColors_, _myNumNeighbor_, _mySide_, _myToroidal_ must be initialized and a _myCounters_, _myLegend_ arrays declared. The new rule will also have to have the required methods listed under the abstract class _Rule.java_. The class should have a method to default initialize the grid and grid states. This is to be in the case where the XML does not provide specific states. 

 * Then, the XML for the rule to be created for the Rule. The parameters of element names would be found under the properties file xml.properties/Rule.properties, the right hand side being the key and the left hand side being the values that are found within the XML. All parameter elements are children of the root. To specify certain states that are to be set by the XML, use element nodes called <rows> under the root. The children of the <rows> will be the <index> node and all the <column> nodes that would be in the same row. 

 * If you wish to use the XMLWriter.java to save XML versions of the current simulation, you must add your desired Rule into the specificElements() method, calling another method that will contain the specifics of your rule. Each element would have to be added one-by-one but the general parameters should already be taken care of by _generalElements()_ method. 

 * Next, the RuleXMLFactory.java for the new Rule is to be created. The factory should include a static variable XML_TAG_NAME that will contain the string of the name of the simulation. Constructor for this class is using the super class constructor with the XML_TAG_NAME as its parameter. The main necessary method is the _getRule()_ method. Use the RuleXMLFactory.java methods _parseXMLDouble_, _parseXMLInteger_, _parseXMLBoolean_, parseXMLString_, _parseXMLColor_ for ease of parsing by putting in the parameters of the root element, the string to be used in the resource file (do not need to call myResource.getString() because that is included in the RuleXMLFactory abstract class), and the default value in String form you wish to set for that parameter in case it wasn’t added in the XML file already. Use the _initialize_ boolean to check if there are specific states stated in the XML file. If they are, the Rule.java class initializer will take care of that as long as the desired paramters for _initSpecific()_ are properly taken care of. These parameters are the current rule being created in the factory, root element, row int, column int, neighbor int, side int, color array, boolean of toroidal, and the integer that indicates the desired default state (in WatorRule, the default empty board is 0 - water. In FireRule, the default desired state is a board of all trees of state 1). 

 * Add additional required parameter and their string representations inside the xml.properties/Rule.properties file. 

 * Go into Playground.java, located in the layout package. Go to the method _setUpFactoryMap()_ in line 184 and add the line
```java
	myFactoryMap.put(“NameOfRule”, new NewRuleXMLFactory());
```

 * If there are any additional buttons that need to be added to the window of the specific simulation, add another portion under the method _setUpButtons()_ in line 192 using the if statement
	If (myRule instanceof YourRule) 

### Justifying Major Design Choices

* Justification of Playground: Our playground class became the main hub of the activity for the separate classes. Here, the JavaFX portions of stage and scene were set up and animated through TimeLine. The initialization of the stage and scene occurred by reaching into a desired _Rule_ class and factory and grabbing the entire rule object instead of merely a representation of it, such as a grid. This way, there would be fluidity of one structure of a rule object holding many different parts of the Rule that were necessary. It was more than just the grid, it was the grid, counter, _updatedGrid_ 2D array and method of checking for the correct _Rule_. Though we recognize it is a long class, the direct use of rule and its attributes allowed for integration between the JavaFX and the UI buttons. 

* Justification of _UIObjectPlacer: _UIObjectPlacer_ was added as a way of accessing methods used by both _StartScreen_ and _Playground_, basically the two classes that deal with user interface. It was a good idea because it cuts back on having to have the same methods that do the exact same things in multiple classes thereby cutting back on duplicated code and making the classes shorter. One downside to doing this is now to add a button, slider, etc., you need to have a _UIObjectPlacer_ object with which to do so, but this is not too complicated to add and you really only need one for the class if you just make it a private instance variable.’

* Justification of _NeighborManager_/_ShapeManager_: At the beginning, there's no _NeighborManager_ and _ShapeManager_. All the shape initializations and neighbor initializations are in the _Rule_ class. This is easy to think and call, but it makes the class extremely long and messy for a parent class functioning as interface. This triggered our thought of refactoring these methods into different classes and use the manager instance to do operations and call methods inside the manager classes.

* Justification of having a cell and 2D array grid decision: The reason I used a 2-D array to represent cells is that this was the first idea that came up. It is convenient because the find time for each cell is O(1). However, it is inefficient for representing triangle grids and hexagon grids, but after some (messy?) calculations, I can fit different cell shapes into this 2-D array representation. If there is a better way of representing cells in multiple shapes, I would love to learn it.

* Justification of splitting the Rules up into superclass and subclasses: For the sake of hierarchy. They all have similar components of grid and cell as well as _cellLength_ and that it requires values for row and column. But the simulations themselves are very different and required different parameters. With the abstract class that can be extended, you get the best of both worlds of each rule simulation being similar and different. 

### Assumptions or Decisions Made to Simplify or Resolve Ambiguities in Project’s Functionality

* Assume the use of XML format and _RuleXMLFactory_ java class format.
* Following that, assume XML compiles correctly, there are no mistakes in terms of syntax. 