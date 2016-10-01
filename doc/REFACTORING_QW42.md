Refactoring
===========

### Author

* Charlie Wang
* qw42

### The Design Issue
The design issue I met was in _Rule_ Class and its subclasses. Before, I made the _Rule_ class an interface by making its methods abstract because I think each rule would have separate initialization approaches. Therefore, when initializing each rule, I wrote code for its _initGrid_ separately. As a result, the code for 

### Why the New Design is Better
Later, I found out that each _initGrid_ method is similar, regardless of the different shapes and different neighbors. 
 
### Link to one or more commits you made that refactored the problem code