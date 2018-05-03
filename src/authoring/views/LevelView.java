package authoring.views;

import java.util.Map;
import java.util.Properties;
import java.util.function.Consumer;

import authoring.entities.BlankEntity;
import authoring.entities.Entity;
import authoring.gamestate.Level;
import authoring.grid.Grid;
import authoring.languages.AuthoringLanguage;
import data.DataGameState;
import engine.components.Component;
import engine.components.Sprite;
import engine.components.XPosition;
import engine.components.YPosition;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

/**
 * @author Hemanth Yakkali((hy115)
 * @author Collin Brown(cdb55)
 */
public class LevelView extends ScrollPane implements AuthoringLanguage{

	private Grid content;
	private Level level;
	Consumer<MouseEvent> addEntity;
	boolean drag = false; 
	private DataGameState gameState;
	
	private Map<Level,Map<Integer,Map<String,Component>>> levels;

	Properties language = new Properties();
	
	public LevelView(Level level, int levelNum) {
		this.levels = gameState.getGameState();
		this.getStyleClass().add("level-view");
		this.level = level;
		this.content = new Grid(level);
		this.content.getStyleClass().add("level-view-content");
		this.setHbarPolicy(ScrollBarPolicy.ALWAYS);
		this.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		this.setContent((content));
		this.setupMouseDrag();
	}

	public LevelView(Level level, int levelNum, Consumer<MouseEvent> aE) {
		this(level,levelNum);
		this.addEntity = aE;
	}
	
	public void loadGameState(Map<Integer,Map<String,Component>> levelMap) {
		
		Map<String, Component> entityComponents;
		for(Integer i:levelMap.keySet()) {
			entityComponents = levels.get(level).get(i);
			Entity entity = new BlankEntity(i);
			if(entityComponents.containsKey(Sprite.KEY)) {
				Sprite spriteComponent = (Sprite) entityComponents.get(Sprite.KEY);
				Image image = spriteComponent.getImage().getImage();
				entity.setImage(image);
				if (entityComponents.containsKey(XPosition.KEY) && entityComponents.containsKey(YPosition.KEY)) {
					XPosition xComp = (XPosition) entityComponents.get(XPosition.KEY);
					YPosition yComp = (YPosition) entityComponents.get(YPosition.KEY);
					double row = (xComp.getData()/Entity.ENTITY_WIDTH)-1;
					double col = (yComp.getData()/Entity.ENTITY_HEIGHT)-1;
					this.content.addToCell(entity, (int) row, (int) col);
				}
			} 
			
			for(Component c: entityComponents.values()) {
				entity.add(c);
			}
		}
	}

	/**
	 * Sets the onMouseReleased method for the content to handle dragging.
	 */
	private void setupMouseDrag() {
		content.setOnDragDetected(e -> this.drag = true);
	}

	/**
	 * Adds entity to the level view both to be seen graphically and to the specific 
	 * level object
	 * @param e Entity to be added to the LevelView
	 */
	public void addEntity(Entity e) {
		this.content.getChildren().add(e);
		level.addEntity(e);
	}

	/**
	 * Retrieves the level attributed to this levelView
	 * @return the level in this levelView
	 */
	public Level getLevel() {
		return this.level;
	}

	/**
	 * Sets the language of the levelview
	 */
	@Override
	public void setLanguage(Properties lang) {
		language = lang;
	}
}
