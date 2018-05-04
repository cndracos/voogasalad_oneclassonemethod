package authoring.entities;

import engine.components.presets.DoodleEnemyCollision;

public class DoodleEnemy extends InteractableEntity {

    private final static String TYPE = "DoodleEnemy";

    /**
     * The constructor simply sets the ID of the entity and initializes its list of components
     *
     * @param ID which identifies an entity
     **/
    public DoodleEnemy(int ID, String name) {
        super(ID);
        addDefaultComponents();
        this.setName(name);
        this.setPresetType(TYPE);
    }

    private void addDefaultComponents() {
        this.setEntityType(TYPE);
        this.add(new DoodleEnemyCollision(this.getID()));
    }
}
