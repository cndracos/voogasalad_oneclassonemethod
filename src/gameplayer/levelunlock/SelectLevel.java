package gameplayer.levelunlock;

import gameplayer.controller.LevelController;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.function.Consumer;

public class SelectLevel extends ScrollPane {
    private int numOfLevels;
    private int currentLevel;
    private ArrayList<LevelItem> levels;
    private VBox levelColumn;
    private Scene myScene;
    private Consumer<Integer> changeLevelAction;

    private static final int PANE_WIDTH = 400;
    private static final int PANE_HEIGHT = 400;

    public SelectLevel(int numOfLevels, ReadOnlyDoubleProperty width,
            ReadOnlyDoubleProperty height, Consumer<Integer> changeLevelAction){
        super();

        this.changeLevelAction = changeLevelAction;
        this.numOfLevels = numOfLevels;
        levels = new ArrayList<>();
        currentLevel = 1;

        levelColumn = new VBox();
        levelColumn.prefWidthProperty().bind(width);
        levelColumn.prefHeightProperty().bind(height);
        levelColumn.setMinWidth(PANE_WIDTH);
        levelColumn.setMinHeight(PANE_HEIGHT);
        levelColumn.setSpacing(40);
        levelColumn.setPadding(new Insets(40, 0, 40, 0));
        levelColumn.getStyleClass().add("vbox");

        createLevels();

        this.setContent(levelColumn);
        this.setHbarPolicy(ScrollBarPolicy.NEVER);
        this.setVbarPolicy(ScrollBarPolicy.NEVER);

        myScene = new Scene(this, PANE_WIDTH, PANE_HEIGHT);
        myScene.getStylesheets().add(getClass().getResource("LevelStyle.css").toExternalForm());
    }

    private void createLevels(){
        for(int i = 1; i < numOfLevels + 1; i++){
            LevelItem temp = new LevelItem(i, i > currentLevel, changeLevelAction);
            temp.prefWidthProperty().bind(levelColumn.widthProperty());
            levels.add(temp);
        }

        levelColumn.getChildren().addAll(levels);
    }

    public void updateLevelProgress(int currentLevel){
        this.currentLevel = currentLevel;
        for(LevelItem l : levels){
            // if level is less than level progress, set level to unlocked
            l.setLocked(!(l.getLevel() <= currentLevel));
        }
    }

    public Scene getMyScene(){
        return myScene;
    }
}
