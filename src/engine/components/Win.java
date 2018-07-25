package engine.components;

import java.io.Serializable;
import java.util.Map;
import java.util.function.BiConsumer;

import engine.actions.Actions;
import engine.systems.collisions.CollisionDirection;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;


public class Win extends FlagComponent {
	public static final String KEY = "Win";

	
	private BooleanProperty winStatus = new SimpleBooleanProperty();
	
	
	public Win(int pid) {
		super(pid);
		winStatus.setValue(false);   
		
	}

	public String getKey() {
		return KEY;
	}

	public void win() {
		winStatus.setValue(true);
		System.out.println(winStatus.getValue());

	}

	public BooleanProperty getWinStatus () {
		return winStatus;
	}

	

}
