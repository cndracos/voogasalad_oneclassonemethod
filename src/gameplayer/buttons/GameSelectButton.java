package gameplayer.buttons;
import data.DataGameState;
import data.DataUtils;
import gameplayer.controller.Controller;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class GameSelectButton extends Button implements IGamePlayerButton{
	
	private Stage myStage;
	private Controller gameController;
	private DataGameState myGameState;
	private static final int IMAGE_WIDTH = 100;
	private static final int IMAGE_HEIGHT = 100;

	/**
	 * @param name of the Game
	 * @param currentGameState of the GameData to be imported
	 * @param image of the Game to used for the button.
	 */
	public GameSelectButton(Stage stage, String name, DataGameState currentGameState, Image image) {
		myGameState = currentGameState;
		myStage = stage;
		setText(name);
		ImageView gameImage = new ImageView(image);
		gameImage.setFitHeight(IMAGE_HEIGHT);
		gameImage.setFitWidth(IMAGE_WIDTH);
		setGraphic(gameImage);
		setEvent();
	}

	public void setEvent() {
		setOnAction(e -> {
			DataUtils.setGame(myGameState.getGameName());
			gameController = new Controller(myStage, myGameState);
		});
	}
}
