package com.josiah.uno.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;

/*
    This controller is connected to the login game screen. The login screen is launched at the start of the program and
    is the placeholder for logging in users. Currently, there aren't any users implemented yet. The user can click
    login to get to the next screen.
 */

public class LoginController {

    // The sceneManager object is used to load and switch to other views in the program.
    private SceneManager sceneManager;

    // AudioClip objects for subtle sound effects (errors, confirmations, clicks)
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

    // This method runs when the class is loaded and initializes components.
    public void initialize() {
        initializeMenu();
    }

    // This method uses the scene manager to load the game selection view and switches to it
    public void login() {
        sceneManager.loadGameSelectionScene();
        sceneManager.switchScene("gameSelection");
    }

    // This method gets the scene manager. It isn't used and may be removed.
    public SceneManager getSceneManager() {
        return sceneManager;
    }

    // This method sets the scene manager which is used to load and switch to other views
    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    // This method initializes components for the settings menu
    public void initializeMenu() {
        settingsBox.toFront();
    }

}
