package engine.components.presets;

import engine.actions.Actions;
import engine.components.Collidable;
import engine.components.Component;
import engine.systems.collisions.CollisionDirection;

import java.io.Serializable;
import java.util.Map;
import java.util.function.BiConsumer;

public class DoodleCollision extends Collidable {

    public DoodleCollision(int pid) {
        super(pid);

        this.setOnDirection(CollisionDirection.Top, (Serializable & BiConsumer<Map<String,Component>,Map<String,Component>>)(e1, e2) -> {
            Actions.damage().accept(e1, e2);
            Actions.transferScore().accept(e1, e2);
        });

    }
}
