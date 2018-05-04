package engine.components.presets;

import engine.actions.Actions;
import engine.components.Component;
import engine.components.KeyInput;
import engine.components.Sprite;
import javafx.scene.input.KeyCode;

import java.io.Serializable;
import java.util.Map;
import java.util.function.Consumer;

public class DoodleMovement extends KeyInput {

    private static final int MOVE_SPEED = 50;
    private double timing;

    public DoodleMovement(int pid, KeyCode left, KeyCode right) {
        super(pid);

        this.addCode(left, (Serializable & Consumer<Map<String,Component>>) map -> {
            Actions.moveLeft(MOVE_SPEED).accept(map);
        });

        this.addCode(right, (Serializable & Consumer<Map<String,Component>>) map -> {
            Actions.moveRight(MOVE_SPEED).accept(map);
        });

        this.addCode(KeyCode.SPACE, (Serializable & Consumer<Map<String,Component>>) e1 -> {
            long time = System.currentTimeMillis();
            if(time - timing > 100) {
                Actions.fireballUp().accept(e1);
                timing = time;
            }
        });
    }
}
