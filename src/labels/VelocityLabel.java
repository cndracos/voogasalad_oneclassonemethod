package labels;
import java.text.MessageFormat;
import java.util.Map;

import engine.components.Component;
import engine.components.Velocity;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Label;

public class VelocityLabel extends Label implements IGameStatusLabel{

	private double currentVelocity;
	private final String VELOCITY_LABEL_NAME = "Velocity";
	private SimpleDoubleProperty velocityProperty;
	private Map<String, Component> playerStatusMap;
	
	
	public VelocityLabel(double velocity) {
		currentVelocity = velocity;
		velocityProperty = new SimpleDoubleProperty(currentVelocity);
		this.textProperty().bind(velocityProperty.asString());
	}
	
	//Interface method to extract the data given a playerStatus Map.
	public double extractGameStateValue(Map<String, Component> playerStatusMap) {
		Velocity velocity = (Velocity) playerStatusMap.get(Velocity.KEY);
		return velocity.getXVel();
	}
	
	public void update(double newValue) {
		velocityProperty.setValue(newValue);
	}
	
	
}
