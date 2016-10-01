Refactoring
===========

### Author

* Charlie Wang
* qw42

### The Design Issue
The design issue I met was in _Rule_ Class and its subclasses. Before, I made the _Rule_ class an interface by making its methods abstract because I think each rule would have separate initialization approaches. Therefore, when initializing each rule, I wrote code for its _initGrid_ separately. As a result, the code for each rule is somewhat duplicated. 

### Why the New Design is Better
Later, I found out that each _initGrid_ method is similar, regardless of the different shapes and different neighbors. Therefore, in the _Rule_ parent class, I added _initGrid()_ method taking an input indicating which kind of cell shape the user is inquiring and the number of neighbors (in the Fire and Schelling's segregation case). In this way, to change the cell shape and the neighbor arrays, the only thing that I have to change is a instance variable in each specific rule, but not writing a whole new yet similar method. This greatly reduces the size of each rule and make the logic clearer.
 
### Link to one or more commits you made that refactored the problem code
["Refactored Rule, FireRule, SchellingRule, and WatorRule's initGrid()"](https://git.cs.duke.edu/CompSci308_2016Fall/cellsociety_team14/commit/f230666c56253da783c785ab0a04ae7ad7802934) 
["use _Rule_'s initBoard() method to select cell shape, but not in each rule"]
https://git.cs.duke.edu/CompSci308_2016Fall/cellsociety_team14/commit/cd90a1465e01755969f40d6ccab46c9cdf521278