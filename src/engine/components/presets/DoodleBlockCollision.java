package engine.components.presets;

import engine.actions.Actions;
import engine.components.Collidable;
import engine.components.YVelocity;
import engine.systems.collisions.CollisionDirection;

public class DoodleBlockCollision extends Collidable{


    public DoodleBlockCollision(int pid) {
        super(pid);

        this.setOnDirection(CollisionDirection.Bot, (e1, e2) -> {
            YVelocity yv = (YVelocity) e2.get(YVelocity.KEY);
            if (yv.getData() <= 0) {
                Actions.bounce(CollisionDirection.Bot, 40).accept(e1, e2);
            }
        });
    }
}
