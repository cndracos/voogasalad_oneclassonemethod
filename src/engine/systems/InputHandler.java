package engine.systems;

import java.security.Key;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import engine.components.Component;
import engine.components.KeyInput;
import javafx.scene.input.KeyCode;


public class InputHandler implements ISystem {
	private Map<Integer, Map<String, Component>> handledComponents = new HashMap<>();

	private Set<KeyCode> activeCodes = new HashSet<>();


	@Override
	public void addComponent(int pid, Map<String, Component> components) {
		if (components.containsKey(KeyInput.getKey())) {
			Map<String, Component> newComponents = new HashMap<>();
			newComponents.put(KeyInput.getKey(), components.get(KeyInput.getKey()));
			handledComponents.put(pid, newComponents);
		}
	}

	@Override
	public void removeComponent(int pid) {
		if (handledComponents.containsKey(pid)) {
			handledComponents.remove(pid);
		}
	}

	@Override
	public void setActives(Set<Integer> actives) {
		//will get around to this
	}

	@Override
	public void execute(double time) {
		for (int id : handledComponents.keySet()) {
			Map<String, Component> components = handledComponents.get(id);
			KeyInput k = (KeyInput) components.get(KeyInput.getKey());
			for (KeyCode key : activeCodes) {
				if (k.containsCode(key)) {
					k.action(key);
				}
			}
		}
		activeCodes.clear();
	}

	public void addComponent(int pid, String componentName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeComponent(int pid, String componentName) {
		// TODO Auto-generated method stub
	}

	public void removeCode(KeyCode code) {
		if(activeCodes.contains(code)) {
			activeCodes.remove(code);
		}
	}

	public void addCode(KeyCode code) {
		activeCodes.add(code);
		//System.out.println(code);
	}

	@Override
	public Map<Integer, Map<String, Component>> getHandledComponent() {
		return handledComponents;
	}
}
