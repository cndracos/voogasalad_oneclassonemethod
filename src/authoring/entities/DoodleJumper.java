package authoring.entities;

import engine.components.Lives;
import engine.components.Player;
import engine.components.Score;
import engine.components.presets.DoodleCollision;
import engine.components.presets.DoodleMovement;
import javafx.scene.input.KeyCode;

public class DoodleJumper extends InteractableEntity {

    private final static String TYPE = "DoodleJumper";
    private final static double INITIAL_HEALTH = 1;
    private final static int INITIAL_LIVES = 1;

    /**
     * The constructor simply sets the ID of the entity and initializes its list of components
     *
     * @param ID which identifies an entity
     **/
    public DoodleJumper(int ID, String name) {
        super(ID);
        addDefaultComponents();
        this.setName(name);
        this.setPresetType(TYPE);
    }

    private void addDefaultComponents() {
        this.setHealth(INITIAL_HEALTH);
        this.setEntityType(TYPE);
        this.add(new Player(this.getID()));
        this.add(new Lives(this.getID(), INITIAL_LIVES));
        this.add(new DoodleCollision(this.getID()));
        this.add(new DoodleMovement(this.getID(), KeyCode.LEFT, KeyCode.RIGHT));
        this.add(new Score(this.getID(), 0));
    }
}
