package gameplayer.buttons;
import gameplayer.menu.PauseMenu;
import javafx.scene.control.Button;

import java.util.function.Consumer;

/**
 * Button that saves the current state of the game
 * @author Ryan
 *
 */
public class SaveGameButton extends Button implements IGamePlayerButton{
	
	private final String BUTTON_NAME = "Save Game";
	private Consumer saveGame;
	
	public SaveGameButton(Consumer saveGame) {
		this.saveGame = saveGame;
		this.setText(BUTTON_NAME);
		this.setOnAction(e -> setEvent());
	}

	/**
	 * Saves the Current State of the Game
	 */
	public void setEvent() {
		 saveGame.accept(null);
	}
}
