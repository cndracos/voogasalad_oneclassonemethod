package authoring.entities;

import engine.components.Passable;
import engine.components.presets.DoodleBlockCollision;
import engine.components.presets.DoodleBreakBlockCollision;

public class BreakBlock extends InteractableEntity {

    private final static String TYPE =  "BreakBlock";

    /**
     * Initialize
     * @param ID
     * @param name
     */
    public BreakBlock(int ID, String name) {
        super(ID);
        this.setName(name);
        addDefaultComponents();
    }

    private void addDefaultComponents() {
        this.add(new DoodleBreakBlockCollision(this.getID()));
        this.add(new Passable(this.getID()));
    }

}