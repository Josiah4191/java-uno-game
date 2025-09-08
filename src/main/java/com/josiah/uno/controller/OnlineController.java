package com.josiah.uno.controller;

import com.josiah.uno.model.image.playerimage.PlayerImage;
import com.josiah.uno.model.image.playerimage.PlayerImageManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
/*
    The online controller is connected to the online view. Currently, the online view is disabled and can't be selected
    by the user. This is meant for multiplayer, but it isn't implemented yet.

     - PlayerImageManager class loads the player images
     - SceneManager class loads and switches views
 */

public class OnlineController {

    private SceneManager sceneManager;
    private PlayerImageManager playerImageManager = new PlayerImageManager();

    private AudioClip click1 = new AudioClip(getClass().getResource("/audio/click1.wav").toString());
    private AudioClip click2 = new AudioClip(getClass().getResource("/audio/click3.wav").toString());
    private AudioClip error1 = new AudioClip(getClass().getResource("/audio/retro.wav").toString());
    private AudioClip confirm1 = new AudioClip(getClass().getResource("/audio/confirm.wav").toString());

    @FXML
    private VBox centerBox;
    @FXML
    private VBox avatarBox;
    @FXML
    private Label avatarLbl;
    @FXML
    private FlowPane avatarImageBox;
    @FXML
    private ToggleGroup avatarImageButtonGroup = new ToggleGroup();
    @FXML
    private VBox backBox;
    @FXML
    private Button backBtn;
    @FXML
    private VBox settingsBox;
    @FXML
    private Button settingsBtn;

    // This method runs when the class is loaded and initializes components
    public void initialize() {
        initializeMenu();
        generatePlayerImages();
        avatarImageBox.setMaxWidth(Region.USE_PREF_SIZE);
    }

    // This method initializes the menu components
    public void initializeMenu() {
        backBox.toFront();
        settingsBox.toFront();
    }

    // Play audio click1
    public void playClick1() {
        click1.play();
    }

    // Play audio click2
    public void playClick2() {
        click2.play();
    }

    // Play audio confirm1
    public void playConfirm1() {
        confirm1.play();
    }

    // Play audio error1
    public void playError1() {
        error1.play();
    }

    // This method sets all the images to the buttons that the user can select
    public void generatePlayerImages() {
        /*
            clear the image pane each time this method is called. If this method is called again, like going
            back and forth in the views, then this prevents the avatarImagePane from duplicating the images and
            making a mess
         */
        avatarImageBox.getChildren().clear();

        // loop through all the values in the PlayerImage enum
        for (PlayerImage playerImage : PlayerImage.values()) {
            ToggleButton toggleButton = new ToggleButton(); // create a ToggleButton
            Image image = playerImageManager.getImage(playerImage); // get the image of that playerImage enum
            ImageView imageView = new ImageView(image); // create ImageView with the player image

            imageView.setFitWidth(50); // adjust the image width
            imageView.setFitHeight(50); // adjust the image height

            // set the user data for the button to its image. this is used to get the selected player image.
            toggleButton.setUserData(playerImage);
            toggleButton.setGraphic(imageView); // set the image for the button

            toggleButton.setToggleGroup(avatarImageButtonGroup); // set toggle group for the button

            // set the event action for when the button is clicked
            toggleButton.setOnAction(e -> setSelectedAvatar());
            // toggleButton.isSelected(); doesn't do anything usefl. may be removed.

            // set the sound effect to playClick1 when the user hovers over the toggle button
            toggleButton.setOnMouseEntered(e -> playClick1());

            // add the button to the avatarImagePane
            avatarImageBox.getChildren().add(toggleButton);
        }
    }

    // This method sets the avatar that the user selects
    public void setSelectedAvatar() {
        ToggleButton selectedPlayerImage = (ToggleButton) avatarImageButtonGroup.getSelectedToggle();
        if (selectedPlayerImage != null) {
            PlayerImage playerImage = (PlayerImage) selectedPlayerImage.getUserData();
            //gameState.getLocalPlayer().setImage(playerImage);
            //System.out.println(gameState.getLocalPlayer().getImage());
            playClick2();
        }
    }

    // This method returns to the previous selection view
    public void goBack() {
        sceneManager.switchScene("gameSelection");
    }

    // This method quits the program.
    public void quit() {
        Platform.exit();
    }

    // This method gets the scene manager. It isn't used and may be removed.
    public SceneManager getSceneManager() {
        return sceneManager;
    }

    // This method sets the sceneManager
    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }
}
