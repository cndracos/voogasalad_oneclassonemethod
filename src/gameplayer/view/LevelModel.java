package gameplayer.view;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import authoring.gamestate.Level;
import data.DataGameState;
import engine.components.*;
import engine.setup.GameInitializer;
import engine.setup.Position;
import engine.setup.RenderManager;
import gameplayer.controller.GameManager;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * Class that controls how the entity objects are displayed on the gameplayer
 * @authors Ryan Fu & Scott Pledger
 *
 */
public class LevelModel implements EntityManager{
	private Map<Integer, Map<Integer,Map<String,Component>>> intLevels = new HashMap<>();
	private Map<Integer, Pane> gameLevelDisplays = new HashMap<>();
	private Map<Integer, Map<String, Boolean>> hudPropMap = new HashMap<>();

	private static final double PANE_HEIGHT = 442;
	private static final double PANE_WIDTH = 800;
	private static final int TOP_BOUND = 100;
	private static final int BOTTOM_BOUND = 200;
	private static final int LEFT_BOUND = 100;
	private static final int RIGHT_BOUND = 400;
	private static final int INVERT = -1;

	/**
	 * Constructor when given the gameState
	 * @param gameState
	 */
	public LevelModel(DataGameState gameState) {
		Map<Level,Map<Integer,Map<String,Component>>> levelMap = gameState.getGameState();
		obtainHudProps(levelMap.keySet());
		levelToInt(levelMap);
		createLevelDisplays(levelMap);

		initializeLevelView();
	}

	/**
	 * Method to obtain the map of heads-up display properties for each level.
	 * @param levels
	 * @return Map<Integer, Map<String,Boolean>>
	 */
	private void obtainHudProps(Set<Level> levels) {
		for (Level l: levels) {
			hudPropMap.put(l.getLevelNum(), l.getHUDprops());
		}
	}

	/**
	 * Obtains heads-up display status map for each level in a game.
	 * @return Map<Integer, Map<String, Boolean>> 
	 */
	public Map<Integer, Map<String, Boolean>> getHudPropMap(){
		return hudPropMap;
	}

	/**
	 * Converts Map of levelMap to its Entities to Integers to Entities to make calling a particular level easier
	 * @return Map<Integer, Map<Integer,Map<String,Component>>> 
	 */
	public void levelToInt(Map<Level, Map<Integer,Map<String,Component>>> levelMap) {
		levelMap.forEach((key, value) -> {
			intLevels.put(key.getLevelNum(), value);
		});
	}

	/**
	 * Method that builds the entire map of level with groups of sprite images
	 * @param levelMap
	 *
	 */
	private void createLevelDisplays(Map<Level, Map<Integer, Map<String, Component>>> levelMap){
		levelMap.forEach((key, value) -> {
			gameLevelDisplays.put(key.getLevelNum(), createIndividualEntityGroup(value));
		});
	}


	/**
	 * returns the display panes for each level
	 * @return
	 */
	public Map<Integer, Pane> getGameLevelDisplays(){
		return gameLevelDisplays;
	}


	/**
	 * Method that creates all the groups for each level in a levelMap.
	 * @param entityMap
	 * @return
	 */
	private Pane createIndividualEntityGroup(Map<Integer, Map<String, Component>> entityMap) {
		Pane entityRoot = new Pane();
		entityMap.forEach((key, value) -> {
			if(value.containsKey(Sprite.KEY)) {
				Sprite spriteComponent = (Sprite) value.get(Sprite.KEY);
				ImageView image = spriteComponent.getImage();

				if (value.containsKey(XPosition.KEY) && value.containsKey(YPosition.KEY)) {
					setSpritePosition((XPosition) value.get(XPosition.KEY), (YPosition) value.get(YPosition.KEY), image);
				}

				if(value.containsKey(Width.KEY) && value.containsKey(Height.KEY)) {
					setSpriteSize((Width) value.get(Width.KEY), (Height) value.get(Height.KEY), image);
				}

				if (value.containsKey(Type.KEY)) {
					SingleStringComponent entityTypeComponent = (SingleStringComponent) value.get(Type.KEY);
					if (entityTypeComponent.getData().equals("Background")) {
						entityRoot.getChildren().add(0, image);
					}
					else {
						entityRoot.getChildren().add(image);
						spriteComponent.setOnImageRemoved((e) -> entityRoot.getChildren().remove(image));
					}
				}
			}
		});
		return entityRoot;
	}

	/**
	 * Repositions the sprite image on the screen based on its position component
	 * @param image - sprite
	 */
	private void setSpritePosition(XPosition px, YPosition py, ImageView image){
		image.setX(px.getData());
		image.setY(py.getData());
	}

	/**
	 * Resizes the sprite image on the screen based on its width and height components
	 * @param image
	 */
	private void setSpriteSize(Width w, Height h, ImageView image){
		image.setFitHeight(h.getData());
		image.setFitWidth(w.getData());
	}

	/**
	 * Connects View to Engine Systems (GameInitializer, RenderManager)
	 */
	public void initializeLevelView() {
		GameInitializer.initializeNewGame(intLevels.get(GameManager.getActiveLevel()),
				GameManager.getActivePlayerPosX(), GameManager.getActivePlayerPosY());
		RenderManager.coordinatesCallBack = () -> getPlayerPosition();
	}

	/**
	 * Renders all changes to the objects sprite
	 */
	public Position getPlayerPosition() {
		double newCenterX = GameManager.getActivePlayerPosX();
		double newCenterY = GameManager.getActivePlayerPosY();
		return new Position(-1, newCenterX, newCenterY);
	}

	/**
	 * Updates the view of the Pane so that it scrolls with the player's movement. Allows for some free movement without scrolling
	 * @param gameRoot
	 */
	public void updateScroll(Pane gameRoot){
		double minX = gameRoot.getTranslateX() * INVERT;
		double maxX = gameRoot.getTranslateX() * INVERT + PANE_WIDTH;
		double minY = gameRoot.getTranslateY() * INVERT;
		double maxY = gameRoot.getTranslateY() * INVERT + PANE_HEIGHT;

		double activePlayerX = GameManager.getActivePlayerPosX();
		double activePlayerY = GameManager.getActivePlayerPosY();

		if(activePlayerY - TOP_BOUND < minY){
			gameRoot.setTranslateY((activePlayerY - TOP_BOUND) * INVERT);
		}

		if(activePlayerY + BOTTOM_BOUND > maxY){
			gameRoot.setTranslateY(((activePlayerY + BOTTOM_BOUND) - PANE_HEIGHT) * INVERT);
		}

		if(activePlayerX - LEFT_BOUND < minX){
			gameRoot.setTranslateX((activePlayerX - LEFT_BOUND) * INVERT);
		}

		if(activePlayerX + RIGHT_BOUND > maxX){
			gameRoot.setTranslateX(((activePlayerX + RIGHT_BOUND) - PANE_WIDTH) * INVERT);
		}
	}

	@Override
	public void addEntity(int pid, Map<String, Component> components) {
		if(gameLevelDisplays != null && gameLevelDisplays.get(GameManager.getActiveLevel()) != null) {
			Pane p = gameLevelDisplays.get(GameManager.getActiveLevel());
			if(components.containsKey(Sprite.KEY)) {
				Sprite s = (Sprite) components.get(Sprite.KEY);
				if(!p.getChildren().contains(s.getImage())) {
					p.getChildren().add(s.getImage());
				}
			}
		}
	}

	@Override
	public void removeEntity(int pid, Map<String,Component> components) {
		if(gameLevelDisplays != null && gameLevelDisplays.get(GameManager.getActiveLevel()) != null) {
			Pane p = gameLevelDisplays.get(GameManager.getActiveLevel());
			if(components.containsKey(Sprite.KEY)) {
				Sprite s = (Sprite) components.get(Sprite.KEY);
				if(p.getChildren().contains(s.getImage())) {
					p.getChildren().remove(s.getImage());
				}
			}
		}
	}

}
