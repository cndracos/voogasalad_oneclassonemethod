package engine.components.presets;

import engine.actions.Actions;
import engine.components.Collidable;
import engine.components.Component;
import engine.systems.collisions.CollisionDirection;

import java.io.Serializable;
import java.util.Map;
import java.util.function.BiConsumer;

public class DoodleEnemyCollision extends Collidable {

    public DoodleEnemyCollision(int pid) {
        super(pid);

        this.setOnDirection(CollisionDirection.Left, (Serializable & BiConsumer<Map<String,Component>,Map<String,Component>>) (e1, e2) -> {
            Actions.damage().accept(e1, e2);
        });

        this.setOnDirection(CollisionDirection.Bot, (Serializable & BiConsumer<Map<String,Component>,Map<String,Component>>) (e1, e2) -> {
            Actions.bounce(3, 30).accept(e1, e2);
        });

        this.setOnDirection(CollisionDirection.Right, (Serializable & BiConsumer<Map<String,Component>,Map<String,Component>>) (e1, e2) -> {
            Actions.damage().accept(e1, e2);
        });

        this.setOnDirection(CollisionDirection.Top, (Serializable & BiConsumer<Map<String,Component>,Map<String,Component>>) (e1, e2) -> {
            Actions.damage().accept(e1, e2);
        });
    }
}
