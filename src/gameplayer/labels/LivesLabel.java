package gameplayer.labels;
import gameplayer.controller.GameManager;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;

public class LivesLabel extends Label implements IGameStatusLabel{

	private final String LIVES_LABEL_NAME = "Lives: ";
	private StringProperty livesProperty;
	
	
	public LivesLabel() {
		livesProperty = new SimpleStringProperty();
		this.textProperty().bind(livesProperty);
	}


	@Override
	public double extractGameStateValue() {
		return GameManager.getLives();
	}


	@Override
	public void update(double newValue) {
		// TODO Auto-generated method stub
		String newStringValue = Double.toString(newValue);
		livesProperty.set(LIVES_LABEL_NAME+newStringValue);
	}
	
	
}
