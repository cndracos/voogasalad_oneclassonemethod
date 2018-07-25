package gameplayer.menu;

import gameplayer.buttons.RestartButton;
import gameplayer.buttons.SaveGameButton;
import gameplayer.buttons.SwitchGameButton;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.util.function.Consumer;

public class PauseMenu extends Popup {

	private static final String PAUSE_LABEL = "Paused";
	private static final int BUTTON_SPACING = 10;

	/**
	 * Constructor for the Pause Menu Popup
	 */
	public PauseMenu(Stage stage, Consumer saveGame, Consumer restartGame) {
		VBox pane = new VBox();

		SaveGameButton saveBtn = new SaveGameButton(saveGame);
		SwitchGameButton switchBtn = new SwitchGameButton(stage, (e) -> this.hide());
		RestartButton  restartBtn = new RestartButton((e) -> this.hide(), restartGame);

		pane.setSpacing(BUTTON_SPACING);
		pane.setAlignment(Pos.CENTER);
		pane.getChildren().addAll(new Label(PAUSE_LABEL), restartBtn, switchBtn, saveBtn);

		this.getContent().add(pane);
		pane.getStylesheets().add("gameplayer/playstyle.css");
		pane.setFillWidth(true);
	}
}
