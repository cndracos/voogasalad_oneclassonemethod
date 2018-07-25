package gameplayer.buttons;

import java.util.Map;
import java.util.function.Consumer;

import authoring.gamestate.Level;
import data.DataGameState;
import engine.components.Component;
import gameplayer.controller.Controller;
import gameplayer.menu.PauseMenu;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class RestartButton extends Button implements IGamePlayerButton{

	private final String BUTTON_NAME = "Restart Game";

	private Consumer hide;
	private Consumer restartGame;

	public RestartButton(Consumer hide, Consumer restartGame) {
		this.setText(BUTTON_NAME);
		this.setEvent();
		this.hide = hide;
		this.restartGame = restartGame;
	}


	public void setEvent() {
		this.setOnAction(e->{
			hide.accept(null);
			restartGame.accept(null);
		});
	}
}