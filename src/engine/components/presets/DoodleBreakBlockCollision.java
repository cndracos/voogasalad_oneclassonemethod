package engine.components.presets;

import engine.actions.Actions;
import engine.components.Collidable;
import engine.components.Component;
import engine.components.YVelocity;
import engine.systems.collisions.CollisionDirection;

import java.io.Serializable;
import java.util.Map;
import java.util.function.BiConsumer;

public class DoodleBreakBlockCollision extends Collidable {


    public DoodleBreakBlockCollision(int pid) {
        super(pid);

        this.setOnDirection(CollisionDirection.Bot, (Serializable & BiConsumer<Map<String,Component>,Map<String,Component>>) (e1, e2) -> {
            YVelocity yv = (YVelocity) e2.get(YVelocity.KEY);
            if (yv.getData() >= 0) {
                Actions.bounce(3, 150).accept(e1, e2);
                Actions.damage().accept(e2, e1);
            }
        });
    }
}
