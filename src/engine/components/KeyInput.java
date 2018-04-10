package engine.components;

import javafx.scene.input.KeyCode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import javafx.scene.input.KeyCode;

public class KeyInput extends Component {

	private Map<KeyCode, Consumer> codes = new HashMap<>();

	public static String KEY = "KeyInput";
	
	public KeyInput(int pid, KeyCode code, Consumer con) {
		super(pid);
		codes.put(code, con);
	}

	public boolean containsCode (KeyCode key) {
		return codes.containsKey(key);
	}

	public void addCode (KeyCode code, Consumer con) {
		codes.put(code, con);
	}

	public void action(KeyCode key) {
		codes.get(key).accept(null);
	}

	public static String getKey() { return KEY; }

}
