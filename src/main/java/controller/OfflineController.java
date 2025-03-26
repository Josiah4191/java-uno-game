package controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.AudioClip;
import multiplayer.client.unoclient.ClientUnoGameManager;
import model.cardgame.card.unocard.UnoCardTheme;
import model.cardgame.card.unocard.UnoEdition;
import model.cardgame.unogame.Difficulty;
import model.cardgame.unogame.UnoGameState;
import model.image.playerimage.PlayerImage;
import model.image.playerimage.PlayerImageManager;

public class OfflineController {


    private SceneManager sceneManager;
    private ClientUnoGameManager gameManager;
    private UnoGameState gameState;
    private PlayerImageManager playerImageManager = new PlayerImageManager();

    private AudioClip click1 = new AudioClip(getClass().getResource("/audio/click1.wav").toString());
    private AudioClip click2 = new AudioClip(getClass().getResource("/audio/click3.wav").toString());
    private AudioClip error1 = new AudioClip(getClass().getResource("/audio/retro.wav").toString());
    private AudioClip confirm1 = new AudioClip(getClass().getResource("/audio/confirm.wav").toString());

    @FXML
    private AnchorPane centerBox;
    @FXML
    private VBox playerNameBox;
    @FXML
    private VBox editionBox;
    @FXML
    private VBox selectionScreen;
    @FXML
    private HBox playBtnBox;
    @FXML
    private HBox row2Box;
    @FXML
    private VBox difficultySelectionBox;
    @FXML
    private HBox difficultyBox;
    @FXML
    private VBox themeBox;
    @FXML
    private HBox difficultySelection;
    @FXML
    private ToggleButton easyBtn;
    @FXML
    private ToggleButton mediumBtn;
    @FXML
    private ToggleButton hardBtn;
    @FXML
    private ToggleButton classicThemeBtn;
    @FXML
    private ToggleButton classicEditionBtn;
    @FXML
    private FlowPane playerImagesBox;
    @FXML
    private Label playerNameLbl;
    @FXML
    private Label difficultyLbl;
    @FXML
    private Label editionLbl;
    @FXML
    private Label themeLbl;
    @FXML
    private HBox editionSelection;
    @FXML
    private HBox cardThemeSelection;
    @FXML
    private Button playBtn;
    @FXML
    private ToggleGroup playerImageButtonGroup = new ToggleGroup();
    @FXML
    private ToggleGroup themeButtonGroup = new ToggleGroup();
    @FXML
    private ToggleGroup editionButtonGroup = new ToggleGroup();
    @FXML
    private ToggleGroup difficultyButtonGroup = new ToggleGroup();
    @FXML
    private VBox backBox;
    @FXML
    private Button backBtn;
    @FXML
    private VBox settingsBox;
    @FXML
    private Button settingsBtn;

    public void setGameManager(ClientUnoGameManager gameManager) {
        this.gameManager = gameManager;
    }

    public void initialize() {
        initializeMenu();
        setupSelectionView();
    }

    public void setGameState(UnoGameState gameState) {
        this.gameState = gameState;
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
        playerImagesBox.getChildren().clear();

        for (PlayerImage playerImage : PlayerImage.values()) {
            ToggleButton toggleButton = new ToggleButton();
            Image image = playerImageManager.getImage(playerImage);
            ImageView imageView = new ImageView(image);

            imageView.setFitWidth(50);
            imageView.setFitHeight(50);

            toggleButton.setUserData(playerImage);
            toggleButton.setGraphic(imageView);

            toggleButton.setToggleGroup(playerImageButtonGroup);

            toggleButton.setOnAction(e -> setSelectedAvatar());
            toggleButton.isSelected();

            toggleButton.setOnMouseEntered(e -> playClick1());

            playerImagesBox.getChildren().add(toggleButton);
        }
    }

    public void setSelectedAvatar() {
        ToggleButton selectedPlayerImage = (ToggleButton) playerImageButtonGroup.getSelectedToggle();
        if (selectedPlayerImage != null) {
            PlayerImage playerImage = (PlayerImage) selectedPlayerImage.getUserData();
            //gameState.getLocalPlayer().setImage(playerImage);
            //System.out.println(gameState.getLocalPlayer().getImage());
            playClick2();
        }
    }

    public void setSelectedTheme() {
        ToggleButton selectedTheme = (ToggleButton) themeButtonGroup.getSelectedToggle();
        if (selectedTheme != null) {
            String text = (String) selectedTheme.getUserData();
            UnoCardTheme theme = UnoCardTheme.valueOf(text);
            //gameState.setTheme(theme);
            //System.out.println(gameState.getTheme());
            playClick2();
        }
    }

    public void setSelectedEdition() {
        ToggleButton selectedEdition = (ToggleButton) editionButtonGroup.getSelectedToggle();
        if (selectedEdition != null) {
            String text = (String) selectedEdition.getUserData();
            UnoEdition edition = UnoEdition.valueOf(text);
            //gameState.setEdition(edition);
            //System.out.println(gameState.getEdition());
            playClick2();
        }
    }

    public void setSelectedDifficulty() {
        ToggleButton selectedDifficulty = (ToggleButton) difficultyButtonGroup.getSelectedToggle();
        if (selectedDifficulty != null) {
            String text = (String) selectedDifficulty.getUserData();
            Difficulty difficulty = Difficulty.valueOf(text);
            //gameState.setDifficulty(difficulty);
            //System.out.println(gameState.getDifficulty());
            playClick2();
        }
    }

    public void setupSelectionView() {
        generatePlayerImages();

        playerImagesBox.setMaxWidth(Region.USE_PREF_SIZE);
        editionBox.setMaxWidth(Region.USE_PREF_SIZE);
        cardThemeSelection.setMaxWidth(Region.USE_COMPUTED_SIZE);
        difficultySelection.setMaxWidth(Region.USE_COMPUTED_SIZE);
        selectionScreen.setMaxWidth(Region.USE_PREF_SIZE);

        easyBtn.setToggleGroup(difficultyButtonGroup);
        mediumBtn.setToggleGroup(difficultyButtonGroup);
        hardBtn.setToggleGroup(difficultyButtonGroup);

        classicThemeBtn.setToggleGroup(themeButtonGroup);
        classicEditionBtn.setToggleGroup(editionButtonGroup);
    }

    public void switchToGameAreaView() {
        sceneManager.loadGameAreaScene();
        sceneManager.switchScene("gameArea");
    }

    public void initializeMenu() {
        backBox.toFront();
        settingsBox.toFront();
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



    /*
    public void hideMenu() {
        Platform.runLater(new Runnable() {
            public void run() {
                if (menuBtnVBox.getScene() != null) {
                    menuBtnVBox.getScene().setOnMouseClicked(new EventHandler<MouseEvent>() {
                        public void handle(MouseEvent mouseEvent) {
                            if (menuBtnVBox.isVisible() && !menuBtnVBox.contains(mouseEvent.getSceneX(), mouseEvent.getSceneY())) {
                                menuBtnVBox.setVisible(false);
                            }
                        }
                    });
                }
            }
        });
    }
     */