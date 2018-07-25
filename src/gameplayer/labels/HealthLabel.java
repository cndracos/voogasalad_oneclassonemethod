package gameplayer.labels;
import java.util.Map;

import engine.components.Component;
import engine.components.Health;
import gameplayer.controller.GameManager;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;

public class HealthLabel extends Label implements IGameStatusLabel{

	private final String HEALTH_LABEL_NAME = "Health: ";
	private StringProperty healthProperty;
	
	
	public HealthLabel() {
		healthProperty = new SimpleStringProperty();
		this.textProperty().bind(healthProperty);
	}

	@Override
	public double extractGameStateValue() {
		Health health = (Health) GameManager.getPlayerKeys().get(GameManager.getActiveLevel()).get(Health.KEY);
		return health.getData();
	}

	@Override
	public void update(double newValue) {
		String newStringValue = Double.toString(newValue);
		healthProperty.setValue(HEALTH_LABEL_NAME + newStringValue);
	}

	
}
