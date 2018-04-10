package frontend.entities;

import java.util.Arrays;

import engine.support.ComponentBuilder;

/**
 * 
 * @author Hemanth Yakkali
 *
 */
public class Block extends Entity{
	
	private final String TYPE =  "Block";
	private final double BLOCK_HEALTH = 50;

	public Block(int ID) {
		super(ID);
		addDefaultComponents();
	}

	@Override
	public void addDefaultComponents() {
		this.setHealth(BLOCK_HEALTH);
		this.add(ComponentBuilder.buildComponent(this.getID(), "EntityType", Arrays.asList(new String[] {TYPE})));
	}
	
}