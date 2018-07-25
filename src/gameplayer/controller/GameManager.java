package gameplayer.controller;

import authoring.gamestate.Level;
import data.DataGameState;
import engine.components.*;

import java.util.HashMap;
import java.util.Map;

/**
 * GameManager extracts all of the information needed to manage updating the state of the game by Controller and LevelModel
 * @author Scott Pledger
 */
public class GameManager {
	private static Map<Integer, Map<String, Component>> playerKeys;
	private static Map<Win, Integer> winKeys;

	private static int activeLevel;
	private static XPosition activePlayerPosX;
	private static YPosition activePlayerPosY;

	private static int numOfLevels;
	private static Map<Integer, Integer> livesMap;

	private static Map<Level, Map<Integer, Map<String, Component>>> levelMap;

	private static Map<Integer, Double> startingXPositionMap;
	private static Map<Integer, Double> startingYPositionMap;

    private static final int FIRST_LEVEL = 1;

    public static void initialize(DataGameState gameState){
        levelMap = gameState.getGameState();

		startingXPositionMap = new HashMap<>();
		startingYPositionMap = new HashMap<>();
		livesMap = new HashMap<>();
        playerKeys = new HashMap<>();
        winKeys = new HashMap<>();

        levelMap.forEach((key, value) -> {
            extractInfo(value, key.getLevelNum());
        });

        setActiveLevel(FIRST_LEVEL);
        numOfLevels = levelMap.keySet().size();
    }
    
	/**
	 * Extracts player and win components from each level
	 * @param entities
	 * @param levelNum
	 */
	private static void extractInfo(Map<Integer, Map<String, Component>> entities, int levelNum){
		entities.forEach((key, value) -> {
            if (value.containsKey(Player.KEY)) {
                playerKeys.put(levelNum, value);
                livesMap.put(levelNum, (int) ((Lives) value.get(Lives.KEY)).getData());
                //we know player must have x/y coordinates (for now)
                startingXPositionMap.put(levelNum, ((XPosition) value.get(XPosition.KEY)).getData());
                startingYPositionMap.put(levelNum, ((YPosition) value.get(YPosition.KEY)).getData());
            }

            if (value.containsKey(Win.KEY)) {
                winKeys.put((Win) value.get(Win.KEY), levelNum);
            }
		});
	}

    /**
     * Returns the lives
     * @return
     */
   	public static Integer getLives() {
   		return livesMap.get(activeLevel);
   	}
 
   	/**
   	 * Repositions the player to the starting position.
   	 */
	public static void respawnPlayer() {
		((XPosition) playerKeys.get(activeLevel).get(XPosition.KEY)).setData(startingXPositionMap.get(activeLevel));
		((YPosition) playerKeys.get(activeLevel).get(YPosition.KEY)).setData(startingYPositionMap.get(activeLevel));
	}


    public static Map<Win, Integer> getWinKeys() { return winKeys; }

    /**
     * Get a map of Level Number to their corresponding Player entity
     * @return
     */
    public static Map<Integer, Map<String, Component>> getPlayerKeys() {
        return playerKeys;
    }

    /**
     * Get the value of the level currently being played
     * @return
     */
    public static int getActiveLevel() {
        return activeLevel;
    }

    /**
     * Get the XPosition of the Player in the current active level as a double
     * @return
     */
    public static double getActivePlayerPosX() {
        return activePlayerPosX.getData();
    }

    /**
     * Get the YPosition of the Player in the current active level as a double
     * @return
     */
    public static double getActivePlayerPosY() {
        return activePlayerPosY.getData();
    }

    /**
     * Get the total number of levels
     * @return
     */
    public static int getNumOfLevels() {
        return numOfLevels;
    }

    /**
     * Set the value of activeLevel
     * @param level
     */
    public static void setActiveLevel(int level){
        activeLevel = level;
        if(level <= numOfLevels){
            activePlayerPosX = (XPosition) playerKeys.get(level).get(XPosition.KEY);
            activePlayerPosY = (YPosition) playerKeys.get(level).get(YPosition.KEY);
        }
    }

    public static double getScore(){
        double score = 0;
        for(Integer i : playerKeys.keySet()){
            if(playerKeys.get(i).containsKey(Score.KEY)){
                Score s = ((Score) playerKeys.get(i).get(Score.KEY));
                score += s.getData();
            }
        }
        return score;
    }
    
	public static void setLives(Integer lives) {
		livesMap.put(activeLevel, lives);
	}

}
