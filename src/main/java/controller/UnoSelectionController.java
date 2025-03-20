package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import model.cardgames.cards.unocards.UnoCardTheme;
import model.cardgames.cards.unocards.UnoEdition;
import model.cardgames.unogame.Difficulty;
import model.cardgames.unogame.UnoGameManager;
import model.cardgames.unogame.UnoGameState;
import model.images.playerimages.PlayerImage;
import model.images.playerimages.PlayerImageManager;

public class UnoSelectionController {

    private UnoGameManager gameManager;
    private UnoGameState gameState;

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
    private HBox playerImagesBox;
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

    public void setGameManager(UnoGameManager gameManager) {
        this.gameManager = gameManager;
    }

    public void setGameState(UnoGameState gameState) {
        this.gameState = gameState;
        setupSelectionView();
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

            playerImagesBox.getChildren().add(toggleButton);
        }
    }

    public void setSelectedAvatar() {
        ToggleButton selectedPlayerImage = (ToggleButton) playerImageButtonGroup.getSelectedToggle();
        if (selectedPlayerImage != null) {
            PlayerImage playerImage = (PlayerImage) selectedPlayerImage.getUserData();
            gameState.getMainPlayer().setImage(playerImage);
            System.out.println(gameState.getMainPlayer().getImage());
        }
    }

    public void setSelectedTheme() {
        ToggleButton selectedTheme = (ToggleButton) themeButtonGroup.getSelectedToggle();
        if (selectedTheme != null) {
            String text = (String) selectedTheme.getUserData();
            UnoCardTheme theme = UnoCardTheme.valueOf(text);
            gameState.setTheme(theme);
            System.out.println(gameState.getTheme());
        }
    }

    public void setSelectedEdition() {
        ToggleButton selectedEdition = (ToggleButton) editionButtonGroup.getSelectedToggle();
        if (selectedEdition != null) {
            String text = (String) selectedEdition.getUserData();
            UnoEdition edition = UnoEdition.valueOf(text);
            gameState.setEdition(edition);
            System.out.println(gameState.getEdition());
        }
    }

    public void setSelectedDifficulty() {
        ToggleButton selectedDifficulty = (ToggleButton) difficultyButtonGroup.getSelectedToggle();
        if (selectedDifficulty != null) {
            String text = (String) selectedDifficulty.getUserData();
            Difficulty difficulty = Difficulty.valueOf(text);
            gameState.setDifficulty(difficulty);
            System.out.println(gameState.getDifficulty());
        }
    }

    public void setupSelectionView() {
        if (!(gameState == null)) {
            generatePlayerImages();
        }

        playerImagesBox.setMaxWidth(Region.USE_COMPUTED_SIZE);
        editionBox.setMaxWidth(Region.USE_COMPUTED_SIZE);
        cardThemeSelection.setMaxWidth(Region.USE_COMPUTED_SIZE);
        difficultySelection.setMaxWidth(Region.USE_COMPUTED_SIZE);
        selectionScreen.setMaxWidth(Region.USE_PREF_SIZE);

        easyBtn.setToggleGroup(difficultyButtonGroup);
        mediumBtn.setToggleGroup(difficultyButtonGroup);
        hardBtn.setToggleGroup(difficultyButtonGroup);

        classicThemeBtn.setToggleGroup(themeButtonGroup);
        classicEditionBtn.setToggleGroup(editionButtonGroup);

    }

}
