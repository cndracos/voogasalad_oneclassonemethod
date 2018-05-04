package engine.components.presets;

import engine.actions.Actions;
import engine.components.Collidable;
import engine.systems.collisions.CollisionDirection;

public class DoodleEnemyCollision extends Collidable {

    public DoodleEnemyCollision(int pid) {
        super(pid);

        this.setOnDirection(CollisionDirection.Left, (e1, e2) -> {
            Actions.damage().accept(e1, e2);
        });

        this.setOnDirection(CollisionDirection.Bot, (e1, e2) -> {
            Actions.damage().accept(e1, e2);
        });

        this.setOnDirection(CollisionDirection.Right, (e1, e2) -> {
            Actions.damage().accept(e1, e2);
        });

        this.setOnDirection(CollisionDirection.Top, (e1, e2) -> {
            Actions.bounce(CollisionDirection.Top, 30).accept(e1, e2);
        });
    }
}
