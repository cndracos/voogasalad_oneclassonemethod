package authoring.entities;

import engine.actions.Actions;
import engine.components.Collidable;
import engine.components.Passable;
import engine.components.presets.DoodleBlockCollision;
import engine.systems.collisions.CollisionDirection;

/**
 * Block class that acts as a preset. Makes it easier to users to create an enemy without needing 
 * to manually add components.
 * @author Hemanth Yakkali(hy115)
 *
 */
public class Block extends InteractableEntity {

	private final static String TYPE =  "Block";

	/**
	 * Initialize 
	 * @param ID
	 * @param name
	 */
	public Block(int ID, String name) {
		super(ID);
		this.setName(name);
		addDefaultComponents();
	}

	private void addDefaultComponents() {
		this.add(new DoodleBlockCollision(this.getID()));
		this.add(new Passable(this.getID()));
	}

}
