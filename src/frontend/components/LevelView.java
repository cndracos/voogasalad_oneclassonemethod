package frontend.components;

import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
/**
 *  
 * @author Hemanth Yakkali((hy115)
 *
 */
public class LevelView extends ScrollPane {
	
	private GridPane pane;
	private Level level;
	
	public LevelView(Level level, int levelNum) {
		this.getStyleClass().add("level-view");
		this.level = level;
		this.setContent(pane);
		this.getStyleClass().add("level-view-wrapper");
		this.setOnMouseClicked(e-> {		
			if(e.getButton().equals(MouseButton.SECONDARY)) {
				LevelPropertiesView lView = new LevelPropertiesView(level, levelNum);
				lView.open();
			} else if(e.getClickCount()==2) {
				HUDPropertiesView hView = new HUDPropertiesView();
				hView.open();
			}
		});
	}

}
