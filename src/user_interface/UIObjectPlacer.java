package user_interface;

import java.io.File;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import layout.Playground;
import xml.XMLParserException;
import xml.factory.XMLFactoryException;

public class UIObjectPlacer {
	private static final Paint FONT_COLOR = Color.BLACK;
	private static final int TEXT_OFFSET = 75;
	private static final int SLIDER_FONT_SIZE = 10;
	private static final int Y_OFFSET = 15;
	
	private Group myRoot;
	private ResourceBundle myResources;

	public UIObjectPlacer(Group root, ResourceBundle resources){
		myRoot = root;
		myResources = resources;
	}
	
	public Text addText(double x, double y, int fontSize, String message, boolean centered){
		Text text = new Text(message);
		text.setFont(new Font(myResources.getString("Font"), fontSize));
		if (centered)
			text.relocate(x - (text.getBoundsInLocal().getWidth()/2), y);
		else
			text.relocate(x, y);
		text.setFill(FONT_COLOR);
		myRoot.getChildren().add(text);
		return text;
	}
	
	public TextField addTextField(String message, double x, double y){
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
		slider.setShowTickLabels(true);
		myRoot.getChildren().add(slider);
		addText(x + TEXT_OFFSET, y + Y_OFFSET, SLIDER_FONT_SIZE, message, true);
		return slider;
	}
	
	public void showError (String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(myResources.getString("ErrorTitle"));
        alert.setContentText(message);
        alert.showAndWait();
    }
	
	public Button addBrowseButton(double x, double y, Stage stage, Playground playground) {
		Button button = addButton(x, y, myResources.getString("ChooseFile"), new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				FileChooser fileChooser = new FileChooser();
				fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML", "*.xml"));
				File file = fileChooser.showOpenDialog(new Stage());
				if (file != null){
					try {
						Playground newPlayground = new Playground(stage, file);
						if (playground != null){
							playground.getAnimation().stop();
							newPlayground.setSliderValue(playground.getSlider().getValue());
						}
						newPlayground.init();
					} catch (XMLFactoryException | XMLParserException e){
						showError(e.getMessage());
					}
				}
				else {
					showError(myResources.getString("NullFileError"));
				}
			}
		});
		return button;
	}
	
	public TextField addNewSimulationTextField(double x, double y, Stage stage, Playground playground){
		TextField textField = addTextField(myResources.getString("TextFieldText"), x, y);
		textField.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				String inText = textField.getCharacters().toString();
				try {
					Playground newPlayground = new Playground(stage, inText);
					if (playground != null){
						playground.getAnimation().stop();
						newPlayground.setSliderValue(playground.getSlider().getValue());
					}
					newPlayground.init();
				} catch (XMLFactoryException | XMLParserException e) {
					showError(e.getMessage());
				}
			}
		});
		return textField;
	}
}
