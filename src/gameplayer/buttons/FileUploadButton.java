package gameplayer.buttons;


import java.io.File;

import data.DataGameState;
import data.DataRead;
import gameplayer.controller.Controller;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

public class FileUploadButton extends Button implements IGamePlayerButton {
	
		private Stage myStage;
		private final String BUTTON_NAME = "Upload Game";
		private DataGameState gameState;
		private Controller gameController;
		
		public FileUploadButton(Stage stage) {
			myStage = stage;
			setText(BUTTON_NAME);
			setEvent();
		}

		public void setEvent() {
			setOnAction(e -> {
				fileUpload();
				gameController = new Controller(myStage, gameState);
			});
		}

		private void fileUpload() {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Open Resource File");
			fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML", "*.xml"));
			Window mainStage = this.getScene().getWindow();
			File file = fileChooser.showOpenDialog(mainStage);
	        if (file != null) {
	        	gameState = DataRead.loadPlayerFile(file);
	        }
	       
		}
		
}	
