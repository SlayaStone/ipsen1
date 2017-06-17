package Controllers;

import Views.*;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

/**
 * Deze class zorgt voor de functies in het menu van het spel, en is verantwoordelijk voor het switchen van scenes
 */
public class MenuController {

	Stage gameStage;

	private EndGameScene endGameScene;
	private GameScene gameScene;
	private LobbyScene lobbyScene;
	private MenuViewScene menuViewScene;
	private PreLobbyScene preLobbyScene;
	private SettingsScene settingsScene;
	private CreditsScene creditsScene;
	private InGameMenuStage inGameMenuStage;

	private String spelernaam;

	public File loadedFile;

	/**
	 * Maak een menucontroller aan met de stage gameStage
	 * @param gameStage
	 * gameStage is de primaire stage die gebruikt wordt
	 */
	public MenuController(Stage gameStage) {

		LobbyController lobbyController = new LobbyController();

		this.gameStage = gameStage;
		endGameScene = new EndGameScene(this);
		gameScene = new GameScene(this);
		lobbyScene = new LobbyScene(this, lobbyController);
		menuViewScene = new MenuViewScene(this);
		preLobbyScene = new PreLobbyScene(this, lobbyController);
		settingsScene = new SettingsScene(this);
		creditsScene = new CreditsScene(this);

		setMenuViewScene();
		//setGameScene();
		gameStage.setTitle("Carcassonne");
		gameStage.getIcons().add(new Image("Afbeeldingen/gameIcon.png"));
		gameStage.show();
		gameStage.setOnCloseRequest(e -> {
			System.exit(0);
		});
	}


	/**
	 * Button action method om de speler terug in het hoofdmenu te krijgen
	 */
	public void backToMainMenu(){
		getGameStage().setScene(getMenuViewScene());
	}

	/**
	 * Deze functie laat de inGameMenu zien op het scherm.
	 */
	public void showInGameMenu(){
		inGameMenuStage.getMenuStage().show();
		gameScene.setSceneBlur();
		//inGameMenuStage.getMenuStage().toFront();
		//inGameMenuStage.initGui();
	}

	/**
	 * Deze functie zorgt dat de IngameMenu weg gaat.
	 */
	public void hideInGameMenu(){
		inGameMenuStage.getMenuStage().hide();
		gameScene.hideSceneBlur();
	}

	/**
	 * Buttion action method om de speler naar het spel einde scherm te laten stellen
	 */
	public void setEndGameScene(){
		gameStage.setScene(endGameScene);
	}

	/**
	 * Deze functie switcht de scene naar de gameScene
	 */
	public void setGameScene(){
		gameStage.setScene(gameScene);

	//	gameStage.setFullScreen(SettingsScene.fullScreen);
	}

	/**
	 * Deze functie switcht de scene naar de lobbyscene
	 */
	public void setLobbyScene(){
		gameStage.setScene(lobbyScene);
		getLobbyScene().Join();
	}

	/**
	 * Deze functie switcht de scene naar de menuviewscene
	 */
	public void setMenuViewScene(){
		gameStage.setScene(menuViewScene);
	}

	/**
	 * Deze functie switcht de scene naar de prelobbyscene
	 */
	public void setPreLobbyScene(){
		gameStage.setScene(preLobbyScene);
	}

	public void setPreLobbySceneSaveGame(){
		openFileBrowser();
		gameStage.setScene(preLobbyScene);
	}

	/**
	 * Deze functie switcht de scene naar de settingsscene
	 */
	public void setSettingsScene(){

		gameStage.setScene(settingsScene);
	}

	/**
	 * Deze functie switcht de scene naar de creditsscene
	 */
	public void setCreditsScene(){

		gameStage.setScene(creditsScene);
	}

	/**
	 * Deze functie haalt de gamestage op
	 * @return gameStage
	 */
	public Stage getGameStage() {
		return gameStage;
	}

	/**
	 * Deze functie switcht de scene naar de endgamescene
	 * @return endGameScene
	 */
	public EndGameScene getEndGameScene() {
		return endGameScene;
	}

	/**
	 * Deze functie haalt de gamescene op
	 * @return gameScene
	 */
	public GameScene getGameScene() {
		return gameScene;
	}

	/**
	 * Deze functie haalt de lobbyScene op
	 * @return lobbyScene
	 */
	public LobbyScene getLobbyScene() {
		return lobbyScene;
	}

	/**
	 * Deze functie haalt de menuViewScene op
	 * @return menuViewScene
	 */
	public MenuViewScene getMenuViewScene() {
		return menuViewScene;
	}

	/**
	 * Deze functie haalt de preLobbyScene op
	 * @return preLobbyScene
	 */
	public PreLobbyScene getPreLobbyScene() {
		return preLobbyScene;
	}

	/**
	 * Deze functie haalt de settingsScene op
	 * @return settingsScene
	 */
	public SettingsScene getSettingsScene() {
		return settingsScene;
	}

	/**
	 * Deze functie haalt de creditsScene op
	 * @return creditsScene
	 */
	public CreditsScene getCreditsScene() {
		return creditsScene;
	}

	public void putIngameMenuInController(InGameMenuStage stage) {this.inGameMenuStage = stage;}
	/**
	 * Deze functie haalt de spelernaam op
	 * @return spelernaam
	 */
	public String getSpelernaam() {
		return spelernaam;
	}

	/**
	 * Deze functie stelt de spelernaam in
	 * @param spelernaam
	 * Geef de spelernaam mee in de vorm van een String
	 */
	public void setSpelernaam(String spelernaam) {
		this.spelernaam = spelernaam;
	}

	/**
	 * Deze functie zorgt ervoor dat de filebrowser geopend wordt
	 * @return geselecteerde savefile
	 */
	public File openFileBrowser(){
		FileChooser fileChooser = new FileChooser();
		//title of window
		fileChooser.setTitle("Laadgame");
		//extension filter json
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Carcassonne", "*.json"));

		// return file
		return fileChooser.showOpenDialog(gameStage);
	}

}
