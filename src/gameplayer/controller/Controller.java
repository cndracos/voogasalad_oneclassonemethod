package gameplayer.controller;

import java.util.Map;
import java.util.function.Consumer;

import data.DataGameState;
import data.DataRead;
import data.DataWrite;
import engine.exceptions.EngineException;
import engine.setup.RenderManager;
import engine.setup.SystemManager;
import engine.systems.InputHandler;
import gameplayer.hud.SampleToolBar;
import gameplayer.levelunlock.SelectLevel;
import gameplayer.menu.MenuGameBar;
import gameplayer.menu.PauseMenu;
import gameplayer.view.LevelModel;
import gameplayer.view.HighScoreView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Controller implements LevelController, PlayerController {
    private static final int WIDTH_SIZE = 800;
    private static final int HEIGHT_SIZE = 500;
    private static final int LEVEL_ONE = 1;
    public static final int FRAMES_PER_SECOND = 60;
    public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;

    private Stage myStage;
    private Pane gameRoot;
    private BorderPane myPane;
    private PauseMenu pauseMenu;

    private Map<Integer, Pane> gameLevelDisplays;
    private SampleToolBar sampleBar;
    private Timeline animation;
    private String currentGameName;

    private Consumer<Pane> updateScroll;
    private Consumer<Stage> openLevelSelector;
    private Consumer<Integer> updateLevel;
    private Consumer initializeGameView;

    public Controller(Stage stage, DataGameState currentGame) {
        currentGameName = "Some game"; //TODO have naming a game

        myStage = stage;
        myStage.setWidth(WIDTH_SIZE);
        myStage.setHeight(HEIGHT_SIZE);
        myStage.setResizable(false);

        GameManager.initialize(currentGame);
        setupWinKeys();

        myPane = new BorderPane();

        Consumer<String> saveGame = (gameName) -> DataWrite.saveGame(currentGame, gameName);
        initializePauseMenu(saveGame);

        SelectLevel levelSelector = new SelectLevel(GameManager.getNumOfLevels(), myStage.widthProperty(),
                myStage.heightProperty(), this::changeGameLevel);

        setLevelView(currentGame);
        updateLevel = levelSelector::updateLevelProgress;
        openLevelSelector = (e) -> e.setScene(levelSelector.getMyScene());
        openLevelSelector.accept(myStage);
    }

    private void setupWinKeys() {
        GameManager.getWinKeys().forEach((key, value) -> {
            key.getWinStatus().addListener((o, oldVal, newVal) -> {
                if (newVal) {
                    levelWon(value);
                }
            });
        });
    }


    private void initializePauseMenu(Consumer<String> saveGame) {
        pauseMenu = new PauseMenu(myStage, saveGame, (e) -> {
            DataGameState restart = DataRead.copyGame();
            GameManager.initialize(restart);
            setLevelView(restart);
        });
        pauseMenu.setOnShowing((e) -> {
            try {
                this.wait();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        });
        pauseMenu.setOnHidden((e) -> {
            this.notifyAll();
        });
    }

    /**
     * Method to call when a level has been won and the level selector needs to appear again
     *
     * @param level - level won
     */
    public void levelWon(int level) {
        if (level >= GameManager.getNumOfLevels()) {
            gameOver();
        } else {
            updateLevel.accept(level + 1);
            openLevelSelector.accept(myStage);
        }
    }


    /**
     * Method that sets the current scene of the game
     */
    private void setLevelView(DataGameState gameState) {
        LevelModel levelModel = new LevelModel(gameState);
        gameLevelDisplays = levelModel.getGameLevelDisplays();

        gameRoot = gameLevelDisplays.get(LEVEL_ONE);
        myPane.setCenter(this.gameRoot);

        updateScroll = levelModel::updateScroll;
        initializeGameView = (e) -> levelModel.initializeLevelView();

        MenuGameBar menuBar = new MenuGameBar(this::changeGameLevel);
        myPane.setBottom(menuBar);

        sampleBar = new SampleToolBar(levelModel.getHudPropMap());
        myPane.setTop(sampleBar);
    }


    /**
     * Changes the display of the gave.
     *
     * @param level to be loaded
     */
    public void changeGameLevel(int level) {
        gameRoot = gameLevelDisplays.get(level);
        myPane.setCenter(gameRoot);

        GameManager.setActiveLevel(level);
        initializeGameView.accept(null);
        sampleBar.setActiveLevel(level);

        Scene gameScene = new Scene(myPane, WIDTH_SIZE, HEIGHT_SIZE);

        initializeGameAnimation();
        assignKeyInputs(gameScene);
        myStage.setScene(gameScene);
    }

    /**
     * Begins the animation cycle count of the animation after game has started
     */
    private void initializeGameAnimation() {
        KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY),
                e -> step(SECOND_DELAY));
        animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        startRendering(SECOND_DELAY);
        animation.play();
    }

    /**
     * Step method that repeats the animation by checking entities using render and system Manager
     *
     * @param elapsedTime
     */
    private void step(double elapsedTime) {
        try {
            SystemManager.execute(elapsedTime);
        } catch (EngineException e) {
            e.printStackTrace();
        }
        updateScroll.accept(gameRoot);
        sampleBar.updateGameStatusLabels();
    }

    private void startRendering(double timeStep) {
        Thread t = new Thread(new RenderManager(timeStep, Math.max(WIDTH_SIZE, HEIGHT_SIZE)));
        EventHandler event = pauseMenu.getOnShowing();
        pauseMenu.setOnShowing((e) -> {
            event.handle(e);
            try {
                t.wait();
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        });
        t.start();
    }


    /**
     * Passes keys to engine and assigns escape key to pause menu
     */
    private void assignKeyInputs(Scene gameScene) {
        gameScene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ESCAPE) {
                pauseMenu.show(myStage);
            } else if (e.getCode() == KeyCode.H) {
                gameOver();
            } else {
                {
                    InputHandler.addCode(e.getCode());
                }
            }
        });
        gameScene.setOnKeyReleased(e -> {
            if (e.getCode() != KeyCode.ESCAPE) {
                InputHandler.removeCode(e.getCode());
            }
        });
    }

    /**
     * Shows the high score screen
     */
    private void gameOver() {
        HighScoreView highScoreScreen = new HighScoreView(myStage);
        highScoreScreen.setScore(GameManager.getScore());
        highScoreScreen.setGameName(currentGameName);
        myStage.setScene(highScoreScreen.getScene());
    }


    //TODO find a place to implement this
    public void lifeChange(Integer livesLeft) {
        if (livesLeft > 0) {
            GameManager.setLives(livesLeft);
            GameManager.respawnPlayer();
        }
    }

}
