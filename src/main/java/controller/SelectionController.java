package controller;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import main.Main;
import model.cardgames.cards.unocards.UnoCardTheme;
import model.cardgames.cards.unocards.UnoEdition;
import model.cardgames.unogame.Difficulty;
import model.cardgames.unogame.UnoGameManager;
import model.cardgames.unogame.UnoGameState;
import model.images.playerimages.PlayerImage;
import model.images.playerimages.PlayerImageManager;

import java.io.IOException;

public class SelectionController {

    private UnoGameManager gameManager;
    private UnoGameState gameState;

    private AudioClip click1 = new AudioClip(getClass().getResource("/audio/click.wav").toString());
    private AudioClip click2 = new AudioClip(getClass().getResource("/audio/click3.wav").toString());
    private AudioClip error1 = new AudioClip(getClass().getResource("/audio/Retro12.wav").toString());
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
    private VBox menuVBox;
    @FXML
    private Label menuBtnLbl;
    @FXML
    private VBox menuBtnVBox = new VBox();
    @FXML
    private Button backBtn;
    @FXML
    private Button quitBtn;

    public void setGameManager(UnoGameManager gameManager) {
        this.gameManager = gameManager;
    }

    public void initialize() {
        initializeMenu();
    }

    public void setGameState(UnoGameState gameState) {
        this.gameState = gameState;
        setupSelectionView();
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

        PlayerImageManager imageManager = gameState.getPlayerImageManager();
        for (PlayerImage playerImage : PlayerImage.values()) {
            ToggleButton toggleButton = new ToggleButton();
            Image image = imageManager.getImage(playerImage);
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
            gameState.getMainPlayer().setImage(playerImage);
            System.out.println(gameState.getMainPlayer().getImage());
            playClick2();
        }
    }

    public void setSelectedTheme() {
        ToggleButton selectedTheme = (ToggleButton) themeButtonGroup.getSelectedToggle();
        if (selectedTheme != null) {
            String text = (String) selectedTheme.getUserData();
            UnoCardTheme theme = UnoCardTheme.valueOf(text);
            gameState.setTheme(theme);
            System.out.println(gameState.getTheme());
            playClick2();
        }
    }

    public void setSelectedEdition() {
        ToggleButton selectedEdition = (ToggleButton) editionButtonGroup.getSelectedToggle();
        if (selectedEdition != null) {
            String text = (String) selectedEdition.getUserData();
            UnoEdition edition = UnoEdition.valueOf(text);
            gameState.setEdition(edition);
            System.out.println(gameState.getEdition());
            playClick2();
        }
    }

    public void setSelectedDifficulty() {
        ToggleButton selectedDifficulty = (ToggleButton) difficultyButtonGroup.getSelectedToggle();
        if (selectedDifficulty != null) {
            String text = (String) selectedDifficulty.getUserData();
            Difficulty difficulty = Difficulty.valueOf(text);
            gameState.setDifficulty(difficulty);
            System.out.println(gameState.getDifficulty());
            playClick2();
        }
    }

    public void setupSelectionView() {
        if (!(gameState == null)) {
            generatePlayerImages();
        }

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

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GameAreaView.fxml"));
            Parent root = loader.load();
            GameAreaController gameAreaControllerFXML = loader.getController();

            gameManager.initialize();
            gameAreaControllerFXML.setGameManager(gameManager);
            gameAreaControllerFXML.setGameState(gameState);

            playConfirm1();
            Stage stage = Main.getPrimaryStage();
            Scene scene = new Scene(root, 900, 800);
            stage.setScene(scene);
            stage.show();
        } catch (NullPointerException e) {
            playError1();
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Error loading FXML file: /Resources/GameAreaView.fxml");
        }
    }

    public void initializeMenu() {
        menuBtnVBox.setVisible(false);
        menuVBox.toFront();
        hideMenu();
    }

    public void toggleMenu() {
        menuBtnVBox.setVisible(!menuBtnVBox.isVisible());
    }

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
}
