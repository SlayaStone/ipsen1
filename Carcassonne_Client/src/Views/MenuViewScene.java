package Views;

import commonFunctions.SceneInitialiser;
import javafx.scene.Scene;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import Controllers.MenuController;
import commonFunctions.SmartButton;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.media.AudioClip;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MenuViewScene extends Scene implements SceneInitialiser{

	private AudioClip clickSound = new AudioClip(Paths.get("Sounds/Snd_Click.wav").toUri().toString());
	private BorderPane mainPane;
	private Label titel = new Label("Carcassonne");
	private VBox buttonVBox = new VBox();
	private MenuController controller;
	// alle hoofdmenu buttons (smartButton = button met spraak)
	private SmartButton[] knoppen = new SmartButton[6];


	public MenuViewScene(MenuController controller){
		super(new BorderPane(), 1280, 720);
		mainPane = (BorderPane) this.getRoot();
		this.controller = controller;

		init();
	}

	public void buttonInit(){
		/*
		 * 0 = New game 1 = Laden game
		 * 2 = Gebruiksaanwijzing
		 * 3 = About
		 * 4 = Instellingen
		 * 5 = Spel verlaten
		 */
		for (int i = 0; i < knoppen.length; i++) {
			knoppen[i] = new SmartButton();
			knoppen[i].setId("menuKnoppen");
			buttonVBox.getChildren().add(knoppen[i]);
		}
	}

	public void init() {
		buttonVBox.setId("schild");
		mainPane.getStylesheets().add("style.css");
		mainPane.setId("mainBackground");
		titel.setId("titel");

		buttonVBox.getChildren().add(titel);

		buttonInit();

		//mainPane.getChildren().add(buttonPane);
		mainPane.setCenter(buttonVBox);
		buttonVBox.setAlignment(Pos.CENTER);

		knoppen[0].setText("Nieuw spel");
		knoppen[0].setOnAction(e -> {
			clickSound.play();
			controller.setPreLobbyScene();
		});

		knoppen[1].setText("Laden spel");
		knoppen[1].setOnAction(e -> {
			clickSound.play();
			controller.setGameScene();
		});



		// Maakt een variabele aan die naar het handleiding document verwijst,
		// wanneer je op de handleiding knop drukt wordt het html doc geopend
		// in het default programma voor het openen van .html

		File handleidingDoc = new File("Handleiding.html");
		knoppen[2].setText("Spelregels");
		knoppen[2].setOnAction(e -> {
			try {
				Desktop.getDesktop().browse(handleidingDoc.toURI());
			} catch (IOException e1) {
				System.out.println(e1);
			}
			clickSound.play();
		});

		knoppen[3].setText("Credits");
		knoppen[3].setOnAction(e -> {
			clickSound.play();
			controller.setCreditsScene();

		});

		knoppen[4].setText("Instellingen");
		knoppen[4].setOnAction(e -> {
			clickSound.play();
			controller.setSettingsScene();

		});

		//Try Catch blok is hier nodig omdat anders het geluid niet af speelt voordat het programma wordt gesloten
		//Er word precies zo lang gewacht als het geluid lang is
		knoppen[5].setText("Spel afsluiten");
		knoppen[5].setOnAction(e -> {
			clickSound.play();
			try {
				Thread.sleep(142);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			System.exit(0);
		});
	}

}