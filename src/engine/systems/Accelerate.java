package engine.systems;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import engine.components.Component;
import engine.components.XAcceleration;
import engine.components.XVelocity;
import engine.components.YAcceleration;
import engine.components.YVelocity;

/**
 * **
 * System to apply changes in velocities
 * Required component: Velocity
 *
 * @author Yameng
 */

public class Accelerate implements ISystem{
	private Map<Integer, Map<String, Component>> handledComponents;
	private Set<Integer> activeComponents;
	
	public Accelerate() {
		handledComponents = new HashMap<>();
		activeComponents = new HashSet<>();
	}

	/**
	 * Adds acceleration and velocity components from <String, Component> Map
	 * 
	 * @param pid	Parent ID of components
	 * @param components	Map of components for given parent
	 */
    public void addComponent(int pid, Map<String, Component> components) {
		if (components.containsKey(XAcceleration.KEY) && 
			components.containsKey(YAcceleration.KEY) &&
			components.containsKey(XVelocity.KEY) &&
			components.containsKey(YVelocity.KEY)) {
			
			handledComponents.put(pid, components);
		}
    	
    }
    
    /**
     * Removes components for given ID
     * 
     * @param pid	Parent whos components will be removed
     */
    public void removeComponent(int pid) {
	    	if(handledComponents.containsKey(pid)) {
	    		handledComponents.remove(pid);
	    	}
    }


	@Override
	public void setActives(Set<Integer> actives) {
		Set<Integer> myActives = new HashSet<>(actives);
		myActives.retainAll(handledComponents.keySet());
		activeComponents = myActives;
	}

	/**
     * Updates velocity values based on Acceleration component
     * 
     *  @param time	Update time for game loop
     */
	public void execute(double time) {
		for (int pid : activeComponents) {
			Map<String,Component> activeComponents = handledComponents.get(pid);

			XAcceleration ax = (XAcceleration) activeComponents.get(XAcceleration.KEY);
			YAcceleration ay = (YAcceleration) activeComponents.get(YAcceleration.KEY);
			XVelocity vx = (XVelocity) activeComponents.get(XVelocity.KEY);
			YVelocity vy = (YVelocity) activeComponents.get(YVelocity.KEY);

			vx.setData(vx.getData() + ax.getData()*time);
			vy.setData(vy.getData() + ay.getData()*time);

		}
	}


}

