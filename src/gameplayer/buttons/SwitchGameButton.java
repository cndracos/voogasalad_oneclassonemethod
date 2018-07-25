package gameplayer.buttons;

import gameplayer.view.SplashScreenView;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.util.function.Consumer;

public class SwitchGameButton extends Button implements IGamePlayerButton{
	private final String BUTTON_NAME = "Switch Games";
	private Stage myStage;
	private Consumer hide;
	
	public SwitchGameButton(Stage stage) {
		myStage = stage;
		setText(BUTTON_NAME);
		setEvent();
	}
	
	public SwitchGameButton(Stage stage, Consumer hide) {
		this.hide = hide;
		myStage = stage;
		setText(BUTTON_NAME);
		setEvent();
	}

	public void setEvent() {
		this.setOnAction(e-> {
			SplashScreenView splashScreen = new SplashScreenView(myStage);
			myStage.setScene(splashScreen.getScene());
			hide.accept(null);
		});	
	}
	
}
