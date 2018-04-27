package HUD;

import java.util.List;
import java.util.Map;

import GamePlayer.GamePlayerController;
import Menu.LevelSelector;
import engine.components.Component;
import engine.components.Health;
import engine.components.Player;
import engine.components.Score;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.HBox;
import labels.HealthLabel;
import labels.LivesLabel;
import labels.ScoreLabel;
import labels.TimeLabel;

public class SampleToolBar extends ToolBar{

	private HBox toolbarLayout;
	private HUDFactory gameStatusFactory;
	private int ActiveLevel;
	private Map<String, Component> playerComponentsforLevel;

	/**
	 * Builds a Sample Tool Bar that acts as the HUD for the game
	 */
	public SampleToolBar(int activeLevel, Map<Integer, Map<String, Component>> PlayerKeys) {
//		ActiveLevel = activeLevel;
//		playerComponentsforLevel = PlayerKeys.get(ActiveLevel);
//		gameStatusFactory = new HUDFactory(playerComponentsforLevel, listOfStates); //factory for all the labels
//		toolbarLayout = gameStatusFactory.create(listOfStates);
		//constructor to create a Sample Tool Bar
		toolbarLayout = new HBox(250); //adding spacing by 40 units
		Health health = (Health) PlayerKeys.get(activeLevel).get(Health.KEY);
		HealthLabel label2 = new HealthLabel(health.getHealth());
		Player player = (Player) PlayerKeys.get(activeLevel).get(Player.KEY);
		LivesLabel label3 = new LivesLabel(player.getLives());
//		Score score = (Score) PlayerKeys.get(activeLevel).get(Score.KEY);
//		ScoreLabel label4 = new ScoreLabel(score.getScore());
	
		toolbarLayout.getChildren().addAll(label2, label3);
		this.getItems().add(toolbarLayout);
	}	
	
	

}
