package user_interface;

import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class UIObjectPlacer {
	private static final Paint FONT_COLOR = Color.BLACK;
	private static final int TEXT_OFFSET = 50;
	private static final int FONT_SIZE = 15;
	private static final int Y_OFFSET = 30;
	
	private Group myRoot;
	private ResourceBundle myResources;
	
	public UIObjectPlacer(Group root, ResourceBundle resources){
		myRoot = root;
		myResources = resources;
	}
	
	public void addText(double x, double y, int fontSize, String message){
		Text text = new Text(message);
		text.setFont(new Font(myResources.getString("Font"), fontSize));
		text.setX(x - (text.getBoundsInLocal().getWidth()/2));
		text.setY(y + text.getBoundsInLocal().getHeight());
		text.setFill(FONT_COLOR);
		myRoot.getChildren().add(text);
	}
	
	public  TextField addTextField(String message, double x, double y){
		TextField textField = new TextField(message);
		textField.relocate(x, y);
		myRoot.getChildren().add(textField);
		return textField;
	}
	
	public Button addButton(double x, double y, String message, EventHandler<ActionEvent> handler){
		Button button = new Button(message);
		button.relocate(x, y);
		button.setOnAction(handler);
		myRoot.getChildren().add(button);
		return button;
	}
	
	public Slider addSlider(double x, double y, double min, double max, double value, String message){
		Slider slider = new Slider(min, max, value);
		slider.relocate(x, y);
		myRoot.getChildren().add(slider);
		Text text = new Text(x + TEXT_OFFSET, y + Y_OFFSET, message);
		text.setFont(new Font(FONT_SIZE));
		myRoot.getChildren().add(text);
		return slider;
	}
	
	public void showError (String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(myResources.getString("ErrorTitle"));
        alert.setContentText(message);
        alert.showAndWait();
    }
}
