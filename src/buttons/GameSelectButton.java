package buttons;

import java.io.File;

import GamePlayer.SplashScreenController;
import data.DataGameState;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class GameSelectButton extends Button {

	private DataGameState gameState;
	private SplashScreenController splash;
	private BooleanProperty gameSelectBoolean;
	
	/**
	 * 
	 * @param name of the Game
	 * @param file of the GameData to be imported
	 * @param image of the Game to used for the button.
	 */
	public GameSelectButton(SplashScreenController g, String name, DataGameState currentGameState, Image image) {
		gameSelectBoolean = new SimpleBooleanProperty(false);
		splash = g;
		this.setText(name);
		ImageView gameImage = new ImageView(image);
		gameImage.setFitHeight(100);
		gameImage.setFitWidth(100);
		this.setGraphic(gameImage);
		gameState = currentGameState;
		this.setGameSelectEvent();
	}
	
	private void setGameSelectEvent() {
		this.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				setGameSelectBoolean();
			}
		});
	
	
	}
	
	public void setGameSelectBoolean() {
		gameSelectBoolean.setValue(!gameSelectBoolean.getValue());
	}
	
	public BooleanProperty getGameSelectBooleanProperty() {
		return gameSelectBoolean;
	}
	
	public DataGameState getGameState() {
		return gameState;
	}
}
