package controller;

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
import model.images.playerimages.PlayerImage;
import model.images.playerimages.PlayerImageManager;

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

    public void initialize() {
        backBox.toFront();
        generatePlayerImages();
        avatarImageBox.setMaxWidth(Region.USE_PREF_SIZE);
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

    public void generatePlayerImages() {
        avatarImageBox.getChildren().clear();

        for (PlayerImage playerImage : PlayerImage.values()) {
            ToggleButton toggleButton = new ToggleButton();
            Image image = playerImageManager.getImage(playerImage);
            ImageView imageView = new ImageView(image);

            imageView.setFitWidth(50);
            imageView.setFitHeight(50);

            toggleButton.setUserData(playerImage);
            toggleButton.setGraphic(imageView);

            toggleButton.setToggleGroup(avatarImageButtonGroup);

            toggleButton.setOnAction(e -> setSelectedAvatar());
            toggleButton.isSelected();

            toggleButton.setOnMouseEntered(e -> playClick1());

            avatarImageBox.getChildren().add(toggleButton);
        }
    }

    public void setSelectedAvatar() {
        ToggleButton selectedPlayerImage = (ToggleButton) avatarImageButtonGroup.getSelectedToggle();
        if (selectedPlayerImage != null) {
            PlayerImage playerImage = (PlayerImage) selectedPlayerImage.getUserData();
            //gameState.getLocalPlayer().setImage(playerImage);
            //System.out.println(gameState.getLocalPlayer().getImage());
            playClick2();
        }
    }

    public void goBack() {
        sceneManager.switchScene("gameSelection");
    }

    public void quit() {
        Platform.exit();
    }

    public SceneManager getSceneManager() {
        return sceneManager;
    }

    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }
}
