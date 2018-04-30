package authoring.gamestate;

import java.util.List;
import java.util.logging.Logger;

import data.DataWrite;

import java.util.ArrayList;

/**
 * Keeps track of the current state of the authoring environment, so that the author 
 * can save/load games dynamically. 
 * @author Dylan Powers
 * @author Hemanth Yakkali (hy115)
 */
public class GameState implements IGameState {

	/**
	 * This object should only be constructed once, upon initialization of the authoring environment.
	 * It will then continue to keep track of the current state of the game by using the update method below.
	 */
	private List<Level> state;
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private static String name;

	public GameState() {
		state = new ArrayList<>();
	}

	/**
	 * Saves the GameState into an XML file
	 */
	@Override
	public void save() {
		try {
			DataWrite.saveFile(this, name);
		} catch (Exception e) {
			LOGGER.log(java.util.logging.Level.SEVERE, e.toString(), e);
		}
	}

	/**
	 * Updates the current state by adding a new level object to the list of levels.
	 * @param level The level object to add
	 */
	@Override
	public void addLevel(Level level) {
		if(!state.contains(level)) {
			state.add(level);
		}
	}

	/**
	 * Updates the current state my removing a level object.
	 * param level The level object to remove
	 */
	@Override
	public void removeLevel(Level level) {
		if(state.contains(level)) {
			state.remove(level);
		}
	}

	/**
	 * @return GameState, which is a list of levels
	 */
	public List<Level> getLevels() {
		return state;
	}
	
	/**
	 * Set the name of the current game.
	 * @param name the name of the game to set
	 */
	public void setName(String name) {
		GameState.name = name;
	}
	
	/**
	 * Get the current name of the game. Useful for finding certain objects in directories.
	 * @return the name of the current game represented by this state object
	 */
	public static String getName() {
		return GameState.name;
	}
}
