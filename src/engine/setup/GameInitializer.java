package engine.setup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import engine.components.Component;
import engine.components.XPosition;
import engine.components.YPosition;
import engine.systems.*;
import engine.systems.collisions.Collision;

/**
 * This is the class which is created when the player first decides to run a game. It creates the managers and loads
 * the components into the appropriate systems, setting up the game to be run smoothly.
 *
 * @author cndracos
 */
public class GameInitializer {

    /**
     * Creates the managers and systems, then reads in the entities. Sets up the rendering system and input handler
     *
     * @param entities
     */
    public static void initializeNewGame (Map <Integer, Map<String, Component>> entities, double renderCenterX, double renderCenterY) {
        SystemManager.addSystems(systems);

        entities.forEach((key, value) -> {
            if (value.containsKey(XPosition.KEY) && value.containsKey(YPosition.KEY)) {
                XPosition px = (XPosition) value.get(XPosition.KEY);
                YPosition py = (YPosition) value.get(YPosition.KEY);
                RenderManager.add(new Position(px.getPID(), px.getData(), py.getData()));
                
            }
            SystemManager.addEntity(key, value);
        });

        SystemManager.setActives(RenderManager.render(renderCenterX, renderCenterY));
    }
    
    private static List<ISystem> systems = new ArrayList<>() {{
        add(new Accelerate());
        add(new Motion());
        add(new ConditionChecker());
        add(new ArtificialIntelligence());
        add(new Collision());
        add(new HealthDamage());
        add(new Animate());
        add(new InputHandler());
    }};

}
