package gameplayer.hud;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import engine.components.Component;
import gameplayer.labels.IGameStatusLabel;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * Reflectively creates a Heads up Displays based on a string.
 * @author Ryan
 *
 */

public class HUDFactory {
	
	public HUDFactory() {}
	
	/**
	 * Reflectively creates and returns a heads up display with the correct the correct labels.
	 * @param listOfStates
	 * @return
	 */
	public List<IGameStatusLabel> create(List<String> listOfStates) {
		IGameStatusLabel gameStatus = null;
		List<IGameStatusLabel> listOfLabels = new ArrayList<IGameStatusLabel>();
		System.out.println(listOfLabels.size());
		for (String temp: listOfStates) {
			try {
				System.out.println(temp);
				gameStatus = (IGameStatusLabel) Class.forName("gameplayer.labels."+temp+"Label").newInstance();
			}catch (InstantiationException e) {
				System.out.println("Error");
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			listOfLabels.add(gameStatus);
		}
		return listOfLabels;
	}
}
