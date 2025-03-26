package controller;

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
import model.cardgame.CardGame;

public class GameSelectionController {

    private SceneManager sceneManager;
    private String selectedGameMode;
    private CardGame selectedGame;
    private AudioClip click1 = new AudioClip(getClass().getResource("/audio/click1.wav").toString());
    private AudioClip click2 = new AudioClip(getClass().getResource("/audio/click3.wav").toString());
    private AudioClip error1 = new AudioClip(getClass().getResource("/audio/retro.wav").toString());
    private AudioClip confirm1 = new AudioClip(getClass().getResource("/audio/confirm.wav").toString());

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

    public void initialize() {
        initializeMenu();
        initializeToggleBtns();
    }

    public void initializeMenu() {
       settingsBox.toFront();
    }

    public void initializeToggleBtns() {
        unoBtn.setUserData(CardGame.UNO);
        unoBtn.setToggleGroup(gameBtnGroup);
        unoBtn.setOnMouseClicked(e -> {
            playClick2();
            selectGame();
        });

        EventHandler<MouseEvent> gameModeEvent = e -> {
            playClick2();
            selectGameMode();
        };

        onlineBtn.setToggleGroup(gameModeBtnGroup);
        onlineBtn.setOnMouseClicked(gameModeEvent);

        offlineBtn.setToggleGroup(gameModeBtnGroup);
        offlineBtn.setOnMouseClicked(gameModeEvent);

    }

    public void selectGameMode() {
        ToggleButton selection = (ToggleButton) gameModeBtnGroup.getSelectedToggle();
        if (selection != null) {
            selectedGameMode = (String) selection.getUserData();
            System.out.println(selectedGameMode);
        } else {
            selectedGameMode = null;
        }
    }

    public void selectGame() {
        ToggleButton selection = (ToggleButton) gameBtnGroup.getSelectedToggle();
        if (selection != null) {
            selectedGame = (CardGame) selection.getUserData();
            System.out.println(selectedGame);
        } else {
            selectedGame = null;
        }
    }

    public void playClick1() {
        click1.play();
    }

    public void playClick2() {
        click2.play();
    }

    public void playConfirm1() {
        confirm1.play();
    }

    public void playError1() {
        error1.play();
    }

    public void launchGame() {
        if (selectedGame != null && selectedGameMode != null) {
            switch (selectedGame) {
                case UNO:
                    if (selectedGameMode.equals("ONLINE")) {
                        playClick2();
                        loadOnlineView();
                        System.out.println("Launching UNO game ONLINE");
                    } else {
                        playClick2();
                        loadOfflineView();
                        System.out.println("Launching UNO game OFFLINE");
                    }
                    break;
                default:
                    break;
            }
        } else {
            playError1();
        }
    }

    public void loadOfflineView() {
        sceneManager.loadOfflineScene();
        sceneManager.switchScene("offline");
    }

    public void loadOnlineView() {
        sceneManager.loadOnlineScene();
        sceneManager.switchScene("online");
    }

    public void goBack() {
        sceneManager.switchScene("login");
    }

    public void quit() {
        Platform.exit();
    }

    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    public SceneManager getSceneManager() {
        return sceneManager;
    }
}
