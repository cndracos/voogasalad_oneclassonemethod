package gameplayer.controller;

import authoring.gamestate.Level;
import data.DataGameState;
import engine.components.*;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

import java.util.HashMap;
import java.util.Map;

/**
 * GameManager extracts all of the information needed to manage updating the state of the game by Controller and GameView
 * @author Scott Pledger
 */
public class GameManager {
    private Map<Integer, Map<String, Component>> playerKeys;
    private Map<Win, Integer> winKeys;
    private int activeLevel;
    private XPosition activePlayerPosX;
    private YPosition activePlayerPosY;
    private int numOfLevels;
    private double lifeCount;
    private double levelProgress;
    private PlayerController myController;
	private Integer lifeEntityID;
	private Map<Level, Map<Integer, Map<String, Component>>> levelMap;
	private Map<Integer, Integer> levelToPlayer;
	private int levelCount;
	private Double lives;

    private static final int FIRST_LEVEL = 1;

    public GameManager(DataGameState gameState, PlayerController controller){
        levelMap = gameState.getGameState();
        levelToPlayer = new HashMap<>();
        playerKeys = new HashMap<>();
        winKeys = new HashMap<>();
        levelProgress = gameState.getLevelProgress();
        myController = controller;
        levelCount = 1;
        for(Level level : levelMap.keySet()){
            extractInfo(levelMap.get(level), level.getLevelNum());
        }

        numOfLevels = levelMap.keySet().size();

        if(winKeys.keySet().size() > 0){
            for(Win w : winKeys.keySet()){
                w.getWinStatus().addListener((o, oldVal, newVal) -> {
                    if(newVal){
                        System.out.println("Won!");
                    }
                });
            }
        }

        setActiveLevel(FIRST_LEVEL);
    }

    /**
     * Extracts player and win components from each level
     * @param entities
     * @param levelNum
     */
    private void extractInfo(Map<Integer, Map<String, Component>> entities, int levelNum){
        Map<String, Component> entityComponents;
        for(Integer i : entities.keySet()) {
            entityComponents = entities.get(i);
            if(entityComponents.containsKey(Sprite.KEY)) {
                if(entityComponents.containsKey(Player.KEY)){
                    playerKeys.put(levelNum, entityComponents);
                }
                if (entityComponents.containsKey(Win.KEY)) {
                    winKeys.put((Win) entityComponents.get(Win.KEY), levelNum);
                }
                if(entityComponents.containsKey(Lives.KEY)) {
              
               		lifeEntityID = i;
                		levelToPlayer.put(levelNum, lifeEntityID);
                		//lifeCount = ((Lives) entityComponents.get(Lives.KEY)).getData();
                }
            }
        }
    }
    
   	public Double getLives() {
   		int count = 1;
   		for (Level l: levelMap.keySet()) {
   			if (count==activeLevel) {
   				lifeCount = ((Lives)levelMap.get(l).get(levelToPlayer.get(activeLevel)).get(Lives.KEY)).getData();
   			}
   			count++;
   		}
   		System.out.print(lifeCount);
   		return lifeCount;	
   	}
 

    /**
     * Get a map of Level Number to their corresponding Player entity
     * @return
     */
    public Map<Integer, Map<String, Component>> getPlayerKeys() {
        return playerKeys;
    }

    /**
     * Get a map of Win components to their corresponding levels
     * @return
     */
    public Map<Win, Integer> getWinKeys() {
        return winKeys;
    }

    /**
     * Get the value of the level currently being played
     * @return
     */
    public int getActiveLevel() {
        return activeLevel;
    }

    /**
     * Get the XPosition of the Player in the current active level as a double
     * @return
     */
    public double getActivePlayerPosX() {
        return activePlayerPosX.getData();
    }

    /**
     * Get the YPosition of the Player in the current active level as a double
     * @return
     */
    public double getActivePlayerPosY() {
        return activePlayerPosY.getData();
    }

    /**
     * Get the total number of levels
     * @return
     */
    public int getNumOfLevels() {
        return numOfLevels;
    }

    /**
     * Set the value of activeLevel
     * @param level
     */
    public void setActiveLevel(int level){
        this.activeLevel = level;
        if(level <= this.numOfLevels){

            this.activePlayerPosX = (XPosition) playerKeys.get(level).get(XPosition.KEY);
            this.activePlayerPosY = (YPosition) playerKeys.get(level).get(YPosition.KEY);
        }
    }

    public double getScore(){
        double score = 0;
        for(Integer i : playerKeys.keySet()){
            if(playerKeys.get(i).containsKey(Score.KEY)){
                Score s = ((Score) playerKeys.get(i).get(Score.KEY));
                score += s.getData();
            }
        }
        return score;
    }
    
	public void setLives(Double numOfLives) {
		lives = numOfLives;
	}

}
