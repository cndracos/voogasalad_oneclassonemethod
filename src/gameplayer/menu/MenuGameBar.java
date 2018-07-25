package gameplayer.menu;

import java.util.Map;
import java.util.function.Consumer;

import engine.components.Component;
import gameplayer.controller.Controller;
import javafx.scene.control.MenuBar;

/**
 * ComboBox Class that allows user to select the corresponding level
 * @author Ryan
 *
 */
public class MenuGameBar extends MenuBar{
		
	private LevelSelector levelMenu;

	public MenuGameBar(Consumer<Integer> levelChanger) {
				levelMenu = new LevelSelector(levelChanger);
				//levelList = createLevelList(levelMap); //levelList now is a list of each level as a combobox.
				this.getMenus().add(levelMenu);		
	}
	
}
