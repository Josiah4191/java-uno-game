package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;

public class LoginController {

    private SceneManager sceneManager;

    private AudioClip click1 = new AudioClip(getClass().getResource("/audio/click1.wav").toString());
    private AudioClip click2 = new AudioClip(getClass().getResource("/audio/click3.wav").toString());
    private AudioClip error1 = new AudioClip(getClass().getResource("/audio/retro.wav").toString());
    private AudioClip confirm1 = new AudioClip(getClass().getResource("/audio/confirm.wav").toString());

    @FXML
    private HBox centerBox;
    @FXML
    private VBox welcomeBox;
    @FXML
    private Label titleLbl;
    @FXML
    private Label loginLbl;
    @FXML
    private GridPane loginBox;
    @FXML
    private HBox usernameBox;
    @FXML
    private HBox passwordBox;
    @FXML
    private Label usernameLbl;
    @FXML
    private Label passwordLbl;
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private Label forgotPasswordLbl;
    @FXML
    private Button loginBtn;
    @FXML
    private Label createLbl;
    @FXML
    private Button createBtn;
    @FXML
    private VBox settingsBox;
    @FXML
    private Button settingsBtn;

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

    public void initialize() {
        initializeMenu();
    }

    public void login() {
        sceneManager.loadGameSelectionScene();
        sceneManager.switchScene("gameSelection");
    }

    public SceneManager getSceneManager() {
        return sceneManager;
    }

    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    public void initializeMenu() {
        settingsBox.toFront();
    }

}
