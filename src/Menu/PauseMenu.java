package Menu;

import buttons.FileUploadButton;
import buttons.SaveGameButton;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Popup;

public class PauseMenu extends Popup {

	public FileUploadButton fileBtn;
	private SaveGameButton saveBtn;
	/**
	 * Constructor for the Pause Menu Popup
	 */
	public PauseMenu() {
		VBox pane = new VBox();
		fileBtn = new FileUploadButton();
		saveBtn = new SaveGameButton();
		pane.setSpacing(10);
		pane.setAlignment(Pos.CENTER);
		pane.getChildren().addAll(new Label("Paused"), new Button("Sound"), new Button("Difficulty"), new Button("Settings"), fileBtn, saveBtn);
		this.getContent().add(pane);
		pane.getStylesheets().add("./GamePlayer/playstyle.css");
		pane.setFillWidth(true);
	}
}
