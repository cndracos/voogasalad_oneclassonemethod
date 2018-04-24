package engine.systems;

import engine.components.Component;
import engine.components.Conditional;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ConditionChecker implements ISystem {
    private Map<Integer, Map<String, Component>> handledComponents = new HashMap<>();
    private Set<Integer> activeComponents = new HashSet<>();

    @Override
    public void addComponent(int pid, Map<String, Component> components) {
        if (components.containsKey(Conditional.KEY)) {
            Map<String, Component> newComponents = new HashMap<>();
            newComponents.put(Conditional.KEY, components.get(Conditional.KEY));
            handledComponents.put(pid, newComponents);
        }
    }

    @Override
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

    @Override
    public void execute(double time) {
        for (int id : activeComponents) {
            Map<String, Component> components = handledComponents.get(id);
            Conditional c = (Conditional) components.get(Conditional.KEY);
            c.evaluate();
        }
    }

    @Override
    public void addComponent(int pid, String componentName) {
        //do something ya?
    }

    @Override
    public void removeComponent(int pid, String componentName) {
        //do something ya?
    }

    @Override
    public Map<Integer, Map<String, Component>> getHandledComponent() {
        return null;
    }
}
