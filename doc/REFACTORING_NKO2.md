Refactoring
===========

### Author

* Noah Over
* nko2

### The Design Issue

* The design issue I decided to refactor was in UIObjectPlacer. It was specifically within the addSlider and addText methods. In the addSlider method, I added some text to label my slider but I did it without calling addText, which resulted in some duplicated code. I did this because originally the addText method automatically centered the text by subtracting half of its width from its x value, but I did not want that for my slider labels.

### Why the New Design is Better

* I decided to place the text using the addText method to get rid of this duplicated code, but first I had to significantly alter addText so it would not always center the text. I realized I could not just center the text while calling the method by changing the parameter because I did not have the width of the text yet, so instead I added another parameter to addText, a boolean called center that is true when you want to center the text and false otherwise. I think this is better because now I can use my addText method in more scenarios and have already actually used it again which I probably would not have done without the centered boolean. Overall, I think this is better because it reduces duplicated code and makes addText more flexible.

### Commit that I Refactored the Code In

* ["Refactored UIObjectPlacer, StartScreen, and Playground"](https://git.cs.duke.edu/CompSci308_2016Fall/cellsociety_team14/commit/06d9726ac081d7c2ca7c68b8ff028c04887b5bcc)