package gameplayer.view;

import java.util.Map;
import data.DataGameState;
import data.DataRead;
import gameplayer.buttons.FileUploadButton;
import gameplayer.buttons.GameSelectButton;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

/**
 * Splash Screen for Selecting Games and Uploading Games
 * @author Ryan Fu
 */
public class SplashScreenView extends BranchScreenView{
	private static final int ROW_NUM = 4;
	private static final int COL_NUM = 3;

	private Stage myStage;

	private GridPane myGridPane;
	private BorderPane myBorderPane;
	private ScrollPane myScrollPane;

	private Map<Image, DataGameState> imageGameStateMap;
	public FileUploadButton fileBtn;

	public SplashScreenView(Stage stage) {
		myStage = stage;
		imageGameStateMap = DataRead.getAllGames();
	}

	/**
	 * Returns the current Scene of the SplashScreenView
	 */
	@Override
	public Scene getScene() {
		return initializeScreen();
	}

	/**
	 * Initializes the Splash Screen with GameSelectButtons to select File
	 * @return
	 */
	@Override
	public Scene initializeScreen() {
		myGridPane = new GridPane();
		setupGridSpacing();
		myGridPane.setGridLinesVisible(true);
		myGridPane.getStyleClass().add("grid-pane");

		fileBtn = new FileUploadButton(myStage);
		fileBtn.getStyleClass().add("Button");
		assignGameSelectButtons();

		myScrollPane = new ScrollPane(myGridPane);
		myBorderPane = new BorderPane(myScrollPane);
		myBorderPane.setBottom(fileBtn);
		myBorderPane.setStyle("-fx-background-color: secondary-color");
		myScrollPane.setVbarPolicy(ScrollBarPolicy.NEVER);
		BorderPane.setAlignment(fileBtn, Pos.CENTER);

		Scene currentScene= new Scene(myBorderPane, WIDTH_SIZE, HEIGHT_SIZE);
		currentScene.getStylesheets().add(SplashScreenView.class.getResource("/resources/styles/PlayerStyles.css").toExternalForm());
		return currentScene;
	}

	/**
	 * Method to dynamically create Game Select Buttons to display all games available in games file
	 */
	private void assignGameSelectButtons() {
		int row = 0;
		int col = 0;
		for (Image image: imageGameStateMap.keySet()) {
			DataGameState currentDataGameState = imageGameStateMap.get(image);
			String nameOfGame = currentDataGameState.getGameName();
			GameSelectButton currentButton = new GameSelectButton(myStage, nameOfGame, currentDataGameState, image);
			currentButton.setMaxSize(WIDTH_SIZE/COL_NUM, HEIGHT_SIZE/ROW_NUM);
			currentButton.getStyleClass().add("Button");
			myGridPane.add(currentButton, col, row);
			if (col == (COL_NUM-1)) {
				col=0; 	
				row++;	
			}else {
				col++; 	
			}
		}
	}

	/**
	 * Method that reorganizes the spacing between gridPanes
	 */
	private void setupGridSpacing() {
		for (int i = 0; i<COL_NUM; i++) {
			myGridPane.getColumnConstraints().add(new ColumnConstraints(WIDTH_SIZE/COL_NUM));
		}
		for (int i = 0; i<ROW_NUM; i++) {
			myGridPane.getRowConstraints().add(new RowConstraints(HEIGHT_SIZE/ROW_NUM));
		}
	}


}
