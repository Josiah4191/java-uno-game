package com.josiah.uno.controller;

import com.josiah.uno.model.cardgame.CardGame;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;

/*

    This controller is for the Game Selection screen. The game selection screen is where the user selects the
    game they want to play and if it's online or offline.

    As of right now, the only available game is UNO, but the idea is that they would select UNO from a list of other
    card games.

    As of right now, there is only an offline single player option. The user hosts their own server and their client
    is connected to their local area network.

     - SceneManager class loads and switches views
 */

public class GameSelectionController {

    private SceneManager sceneManager;
    private String selectedGameMode;
    private CardGame selectedGame;
    private AudioClip click1 = new AudioClip(getClass().getResource("/audio/click1.wav").toString());
    private AudioClip click2 = new AudioClip(getClass().getResource("/audio/click3.wav").toString());
    private AudioClip error1 = new AudioClip(getClass().getResource("/audio/retro.wav").toString());
    private AudioClip confirm1 = new AudioClip(getClass().getResource("/audio/confirm.wav").toString());

    private AudioClip GameMenuMusic = new AudioClip(getClass().getResource("/audio/Cuddle_clouds.mp3").toString());

    @FXML
    private ToggleGroup gameBtnGroup = new ToggleGroup();
    @FXML
    private ToggleButton unoBtn;
    @FXML
    private Label titleLbl;
    @FXML
    private HBox playBtnBox;
    @FXML
    private Button playBtn;
    @FXML
    private VBox gameModeBox;
    @FXML
    private HBox gameModeBtnBox;
    @FXML
    private ToggleGroup gameModeBtnGroup = new ToggleGroup();
    @FXML
    private ToggleButton onlineBtn;
    @FXML
    private ToggleButton offlineBtn;
    @FXML
    private Label gameModeLbl;
    @FXML
    private VBox settingsBox;
    @FXML
    private Button settingsBtn;

    // This method sets up the controller and runs when the class is loaded.
    public void initialize() {
        initializeMenu();
        initializeToggleBtns();
        //playGameMenuMusic();
    }

    // This method plays background music for the menu
    public void playGameMenuMusic() {
        GameMenuMusic.play();
    }

    // This method is for initializing the component for the menu
    public void initializeMenu() {
        settingsBox.toFront();
    }

    // This method is for initializing the components for the buttons for selecting a game
    public void initializeToggleBtns() {
        // set the user data for the button to CardGame.UNO enum. Used for getting the card game that is selected
        unoBtn.setUserData(CardGame.UNO);
        unoBtn.setToggleGroup(gameBtnGroup); // set the toggle group of the UNO button
        // set the mouse click event to select the game
        unoBtn.setOnMouseClicked(e -> {
            playClick2();
            selectGame();
        });

        /*
            Create eventhandler object which is set for the online and offline buttons below. Created in this way
            to prevent duplicating the same line of code.
        */
        EventHandler<MouseEvent> gameModeEvent = e -> {
            playClick2();
            selectGameMode();
        };

        onlineBtn.setToggleGroup(gameModeBtnGroup); // set the toggle group for the online button
        onlineBtn.setOnMouseClicked(gameModeEvent); // set the event handler for the online button

        offlineBtn.setToggleGroup(gameModeBtnGroup); // set the toggle group for the offline button
        offlineBtn.setOnMouseClicked(gameModeEvent); // set the event handler for the offline button

    }

    // This method gets the selected game mode and sets the selectedGameMode
    public void selectGameMode() {
        // Get the button that is selected in the toggle group
        ToggleButton selection = (ToggleButton) gameModeBtnGroup.getSelectedToggle();
        if (selection != null) { // check if there is a button selected
            selectedGameMode = (String) selection.getUserData(); // get the user data from the button that is selected
            System.out.println(selectedGameMode); // print out the selected game mode to console for debugging
        } else {
            selectedGameMode = null; // if nothing is selected, set the selectedGameMode to null
        }
    }

    // This method gets the selected game and sets the selectedGame
    public void selectGame() {
        // Get the button that is selected in the toggle group
        ToggleButton selection = (ToggleButton) gameBtnGroup.getSelectedToggle();
        if (selection != null) { // check if there is a button selected
            selectedGame = (CardGame) selection.getUserData(); // get the user data from the button that is selected
            System.out.println(selectedGame); // print out the selected game to console for debugging
        } else {
            selectedGame = null; // if nothing is selected, set the selectedGame to null
        }
    }

    // This method plays audio click1
    public void playClick1() {
        click1.play();
    }

    // This method plays audio click2
    public void playClick2() {
        click2.play();
    }

    // This method plays audio confirm1
    public void playConfirm1() {
        confirm1.play();
    }

    // This method plays audio error1
    public void playError1() {
        error1.play();
    }

    // This method loads the next view based on what the user selected
    public void launchGame() {
        // Check if the selectedGame and the selectedGameMode are not equal to null
        if (selectedGame != null && selectedGameMode != null) {
            switch (selectedGame) { // switch on the selected game
                case UNO: // if they select UNO
                    if (selectedGameMode.equals("ONLINE")) { // if they selected online
                        playClick2();
                        loadOnlineView(); // loads and switches to the online game view
                        System.out.println("Launching UNO game ONLINE"); // print message to console for debugging
                        // set the game mode group and game button group to null in case they return to this menu
                        gameModeBtnGroup.selectToggle(null);
                        gameBtnGroup.selectToggle(null);
                    } else { // if they selected offline
                        playClick2();
                        loadOfflineView(); // loads and switches to the offline game view
                        System.out.println("Launching UNO game OFFLINE"); // print message to console for debugging
                        // set the game mode group and game button group to null in case they return to this menu
                        gameModeBtnGroup.selectToggle(null);
                        gameBtnGroup.selectToggle(null);
                    }
                    break;
                default:
                    break;
            }
        } else {
            playError1(); // play error sound if selectedGame or selectedGameMode are null
        }
    }

    // This method loads the offline game screen and then switches to that screen.
    public void loadOfflineView() {
        sceneManager.loadOfflineScene();
        sceneManager.switchScene("offline");
    }

    // This method loads the offline game screen and then switches to that screen.
    public void loadOnlineView() {
        sceneManager.loadOnlineScene();
        sceneManager.switchScene("online");
    }

    // This method is for going back to the previous screen. It loads the login screen.
    public void goBack() {
        sceneManager.switchScene("login");
    }

    // This method is for quitting the game. The server/client hasn't been created at this point.
    public void quit() {
        Platform.exit();
    }

    // This is for setting the sceneManager so that different screens can be loaded and switched to.
    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    // This is for getting the scene manager. It isn't used and may be removed.
    public SceneManager getSceneManager() {
        return sceneManager;
    }
}
