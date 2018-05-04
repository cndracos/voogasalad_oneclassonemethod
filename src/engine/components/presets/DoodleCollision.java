package engine.components.presets;

import engine.actions.Actions;
import engine.components.Collidable;
import engine.systems.collisions.CollisionDirection;

public class DoodleCollision extends Collidable {

    public DoodleCollision(int pid) {
        super(pid);

        this.setOnDirection(CollisionDirection.Top, (e1, e2) -> {
            Actions.damage().accept(e1, e2);
            Actions.transferScore().accept(e1, e2);
        });

    }
}
