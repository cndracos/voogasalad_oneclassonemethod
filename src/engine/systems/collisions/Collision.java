package engine.systems.collisions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import engine.components.Component;
import engine.components.Dimension;
import engine.components.Position;
import engine.components.Velocity;
import engine.systems.ISystem;

public class Collision implements ISystem {
	private Map<Integer, List<Component>> handledComponents;
	private Map<Integer, Velocity> colliders;
	private CollisionHandler handler;
	
	public Collision() {
		handledComponents = new HashMap<>();
		colliders = new HashMap<>();
		handler = new CollisionHandler(handledComponents);
	}

	public void execute(double time) {
		colliders.forEach((key1, vel) -> {
			handledComponents.forEach((key2, list) -> {				// Hooooorrible code, refactor
				if(key1 != key2) {
					Dimension d1 = (Dimension) handledComponents.get(key1).get(Index.DIMENSION_INDEX);
					Dimension d2 = (Dimension) handledComponents.get(key2).get(Index.DIMENSION_INDEX);
					Position p1 = (Position) handledComponents.get(key1).get(Index.POSITION_INDEX);
					Position p2 = (Position) handledComponents.get(key2).get(Index.POSITION_INDEX);
					
					double topOverlap = p1.getYPos() + d1.getHeight() - p2.getYPos();
					double leftOverlap = p1.getXPos() + d1.getWidth() - p2.getXPos();
					double botOverlap = p2.getYPos() + d2.getHeight() - p1.getYPos();
					double rightOverlap = p2.getXPos() + d2.getWidth() - p1.getXPos();
					
					boolean top = (topOverlap > 0) && (p1.getYPos() < p2.getYPos());
					boolean left = (leftOverlap > 0) && (p1.getXPos() < p2.getXPos());
					boolean bot = (botOverlap > 0) && (p1.getYPos() > p2.getYPos());
					boolean right = (rightOverlap > 0) && (p1.getXPos() > p2.getXPos());
					
					if(top || bot || left || right) {
						handler.handle(handledComponents, key1, key2);// Signal collision
					}
						
					List<Double> lengths = new ArrayList<>();
					lengths.add(topOverlap);
					lengths.add(botOverlap);
					lengths.add(leftOverlap);
					lengths.add(rightOverlap);
					
					Collections.sort(lengths);
					
					for(int i = 0; i < lengths.size(); i++) {
						if(lengths.get(i) > 0) {
							if(lengths.get(i) == topOverlap && top) {
								p1.setYPos(p2.getYPos() - d1.getHeight()); 		// Change velocity
							} else if(lengths.get(i) == botOverlap && bot) {
								p1.setYPos(p2.getYPos() + d2.getHeight());
							} else if(lengths.get(i) == leftOverlap && left) {
								p1.setXPos(p2.getXPos() - d1.getWidth());
							} else if(right) {
								p1.setXPos(p2.getXPos() + d2.getWidth());
							}
						}
					}
					
				}
			});
		});
	}

	public void removeComponent(int pid) {
		if(handledComponents.containsKey(pid)) {
			handledComponents.remove(pid);
		}
		if(colliders.containsKey(pid)) {
			colliders.remove(pid);
		}
	}

	public void addComponent(int pid, Map<String, Component> components) {
		if (components.containsKey(Position.getKey()) && components.containsKey(Dimension.getKey())) {
			List<Component> newComponents = new ArrayList<>();
			newComponents.add(components.get(Dimension.getKey()));
			newComponents.add(components.get(Position.getKey()));
			handledComponents.put(pid, newComponents);
			if(components.containsKey(Velocity.getKey())) {
				colliders.put(pid, (Velocity) components.get(Velocity.getKey()));
			}
		}
	}
	
}