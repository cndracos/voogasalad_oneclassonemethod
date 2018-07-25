package gameplayer.labels;

import gameplayer.controller.GameManager;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;

public class ScoreLabel extends Label implements IGameStatusLabel{

	private final String SCORE_LABEL_NAME = "Score: ";
	private StringProperty scoreProperty;
	
	
	public ScoreLabel() {
		scoreProperty = new SimpleStringProperty();
		this.textProperty().bind(scoreProperty);
	}


	@Override
	public double extractGameStateValue() {
		return GameManager.getScore();
	}

	@Override
	public void update(double newValue) {
		String newStringValue = Double.toString(newValue);
		scoreProperty.set(SCORE_LABEL_NAME + newStringValue);
		
	}

	
}
