package user_interface;

import javafx.scene.Group;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class UserInterface {
	private static final String TITLE = "Cell Society:";
	private static final String SUBTITLE = "A Cellular Automata Simulation";
	private static final String FONT = "Times New Roman";
	
	private Group myRoot;
	
	public UserInterface(Group root){
		myRoot = root;
	}
	
	public void addText(int x, int y, int fontSize, String words){
		Text text = new Text(x, y, words);
		text.setFont(new Font(FONT, fontSize));
		myRoot.getChildren().add(text);
	}

}
