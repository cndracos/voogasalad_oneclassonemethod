package engine.setup;

import engine.systems.Animate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Iterator;
import java.util.function.Supplier;

/**
 * Class which renders entities in and out of being acted upon based in a center location and rendering distance
 *
 * @author cndracos
 */
public class RenderManager implements Runnable {

    private static int renderDistance;
    private static double centerX, centerY;

    private static List<Integer> swapList = new ArrayList<>();
    private static Map<Integer, Position> withinRender = new HashMap<>(), outsideRender = new HashMap<>();

    public static Supplier<Position> coordinatesCallBack;

    private double renderInterval;
    private double renderTime;

    public RenderManager (double renderInterval, int renderDistance) {
        this.renderInterval = renderInterval;
        this.renderDistance = renderDistance;
        this.renderTime = 0;
    }

    /**
     * Adds a new position to the system and loads it into outsideRender or insideRender
     * @param p new entity's position component
     */
    public static void add (Position p) {
        if (withinRenderDistance(p.getXPos(), p.getYPos())) withinRender.put(p.getPID(), p);
        else outsideRender.put(p.getPID(), p);
    }


    /**
     * The main method called by the game loop, renders&garbage collects
     * @param newCenterX the player's current x center
     * @param newCenterY the player's current y center
     * @return set of id's which are within the render
     */
    public static Set<Integer> render(double newCenterX, double newCenterY) {
        centerX = newCenterX; centerY = newCenterY;
        garbageCollect(); renderObjects();
        return withinRender.keySet();
    }

    public static Set<Integer> render() {
        return render(centerX, centerY);
    }

    private static void garbageCollect() {
        updateNodes(withinRender, outsideRender, false);
    }

    private static void renderObjects() {
        updateNodes(outsideRender, withinRender, true);
    }

    /**
     * Swaps out nodes to/from within/outside render, uses an iterator to search positions then checks
     * the boolean with withinRenderDistance helper method
     *
     * @param origin The render group (outside or inside) that the positions currently reside
     * @param destination The (potential) new render group if it needs to be swapped
     * @param intoRender dictates whether or not we are looking to go into or out of render
     */
    private static void updateNodes (Map<Integer, Position> origin, Map<Integer, Position> destination, boolean intoRender) {
        for (Iterator<Position> iterator = origin.values().iterator(); iterator.hasNext(); ) {
            Position p = iterator.next();
            if (withinRenderDistance(p.getXPos(), p.getYPos()) == intoRender) {
                destination.put(p.getPID(), p);
                swapList.add(p.getPID()); //has to store nodes to get listed, cannot do dynamically
            }
        }
        removeOldNodes(origin);
    }

    private static void removeOldNodes (Map<Integer, Position> updated) {
        for (int i : swapList) {
            updated.remove(i);
        }
        swapList.clear();
    }

    private static boolean withinRenderDistance(double x, double y) {
        return Math.abs(centerX - x) < renderDistance && Math.abs(centerY - y) < renderDistance;
    }

    @Override
    public void run() {
        //Animate.animateComponents();
        if (renderTime >= 6) {
            Position p = coordinatesCallBack.get();
            SystemManager.setActives(render(p.getXPos(), p.getYPos()));
            renderTime = 0;
        }
        else renderTime+=renderInterval;
    }
}
