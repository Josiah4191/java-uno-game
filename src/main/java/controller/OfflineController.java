package controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.AudioClip;
import multiplayer.client.Client;
import model.cardgame.unogame.ClientUnoGameManager;
import model.cardgame.card.unocard.UnoCardTheme;
import model.cardgame.card.unocard.UnoEdition;
import model.cardgame.unogame.Difficulty;
import model.image.playerimage.PlayerImage;
import model.image.playerimage.PlayerImageManager;
import multiplayer.client.clientmessage.GameAction;

public class OfflineController {

    private Client client;
    private SceneManager sceneManager;
    private ClientUnoGameManager gameManager;
    private PlayerImageManager playerImageManager = new PlayerImageManager();

    private UnoEdition selectedEdition;
    private UnoCardTheme selectedTheme;
    private Difficulty selectedDifficulty;
    private PlayerImage selectedAvatar;
    private String selectedName;
    private int numberOfOpponents;

    private AudioClip click1 = new AudioClip(getClass().getResource("/audio/click1.wav").toString());
    private AudioClip click2 = new AudioClip(getClass().getResource("/audio/click3.wav").toString());
    private AudioClip error1 = new AudioClip(getClass().getResource("/audio/retro.wav").toString());
    private AudioClip confirm1 = new AudioClip(getClass().getResource("/audio/confirm.wav").toString());

    @FXML
    private AnchorPane centerBox;
    @FXML
    private VBox avatarImageBox;
    @FXML
    private VBox editionBox;
    @FXML
    private StackPane centerPane;
    @FXML
    private GridPane centerGrid;
    @FXML
    private VBox playBtnBox;
    @FXML
    private HBox row2Box;
    @FXML
    private VBox difficultyVBox;
    @FXML
    private HBox difficultyHBox;
    @FXML
    private VBox themeBox;
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
    private FlowPane avatarImagePane;
    @FXML
    private Label avatarImageLbl;
    @FXML
    private HBox playerNameBox;
    @FXML
    private Label playerNameLbl;
    @FXML
    private TextField playerNameTextField;
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
    @FXML
    private VBox numberOfOpponentsBox;
    @FXML
    private Label numberOfOpponentsLbl;
    @FXML
    SpinnerValueFactory<Integer> spinnerFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 9, 1);
    @FXML
    private Spinner<Integer> numberOfOpponentsSpinner;
    @FXML
    private VBox separationBox1;
    @FXML
    private VBox separationBox2;

    public void setGameManager(ClientUnoGameManager gameManager) {
        this.gameManager = gameManager;
    }

    public SceneManager getSceneManager() {
        return sceneManager;
    }

    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    public void initialize() {
        generatePlayerImages();
        initializeMenu();
        initializeToggleGroup();
        initializeSpinner();
        createClient();
    }

    public void initializeMenu() {
        backBox.toFront();
        settingsBox.toFront();
    }

    public void initializeToggleGroup() {
        easyBtn.setToggleGroup(difficultyButtonGroup);
        mediumBtn.setToggleGroup(difficultyButtonGroup);
        hardBtn.setToggleGroup(difficultyButtonGroup);

        classicThemeBtn.setToggleGroup(themeButtonGroup);
        classicEditionBtn.setToggleGroup(editionButtonGroup);
    }

    public void initializeSpinner() {
        numberOfOpponentsSpinner.setValueFactory(spinnerFactory);
    }

    public void createClient() {
        gameManager = new ClientUnoGameManager();

        client = new Client();
        client.setGameManager(gameManager);

        client.createServer();
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
        avatarImagePane.getChildren().clear();

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

            avatarImagePane.getChildren().add(toggleButton);
        }
    }

    public void setSelectedAvatar() {
        ToggleButton selectedAvatarButton = (ToggleButton) playerImageButtonGroup.getSelectedToggle();
        if (selectedAvatarButton != null) {
            playClick2();
            selectedAvatar = (PlayerImage) selectedAvatarButton.getUserData();
            playerNameTextField.setText(selectedAvatar.getName());

            System.out.println(selectedAvatar);
        } else {
            selectedAvatar = null;
        }
    }

    public void setSelectedTheme() {
        ToggleButton selectedThemeButton = (ToggleButton) themeButtonGroup.getSelectedToggle();
        if (selectedThemeButton != null) {
            playClick2();
            String text = (String) selectedThemeButton.getUserData();
            selectedTheme = UnoCardTheme.valueOf(text);
            System.out.println(selectedTheme);
        } else {
            selectedTheme = null;
        }
    }

    public void setSelectedEdition() {
        ToggleButton selectedEditionButton = (ToggleButton) editionButtonGroup.getSelectedToggle();
        if (selectedEditionButton != null) {
            playClick2();
            String text = (String) selectedEditionButton.getUserData();
            selectedEdition = UnoEdition.valueOf(text);
            System.out.println(selectedEdition);
        } else {
            selectedEdition = null;
        }
    }

    public void setSelectedDifficulty() {
        ToggleButton selectedDifficultyButton = (ToggleButton) difficultyButtonGroup.getSelectedToggle();
        if (selectedDifficultyButton != null) {
            playClick2();
            String text = (String) selectedDifficultyButton.getUserData();
            selectedDifficulty = Difficulty.valueOf(text);
            System.out.println(selectedDifficulty);
        } else {
            selectedDifficulty = null;
        }
    }

    public void setSelectedName() {
        selectedName = playerNameTextField.getText();
    }

    public void setNumberOfOpponents() {
        numberOfOpponents = numberOfOpponentsSpinner.getValue();
    }

    public void goBack() {
        sceneManager.switchScene("gameSelection");
    }

    public void quit() {
        Platform.exit();
    }

    public void setupGame() {
        playClick2();
        setSelectedName();
        setNumberOfOpponents();

        if (!(
                selectedEdition == null ||
                selectedTheme == null ||
                selectedDifficulty == null ||
                selectedAvatar == null ||
                selectedName == null
        )) {

        GameAction joinAction = gameManager.joinGame(selectedName, selectedAvatar);
        GameAction setupAction = gameManager.setupGame(selectedEdition, selectedTheme, selectedDifficulty, numberOfOpponents);

        String joinActionMessage = joinAction.toJson();
        String setupActionMessage = setupAction.toJson();

        client.sendMessage(joinActionMessage);
        client.sendMessage(setupActionMessage);

        sceneManager.loadGameAreaScene(gameManager, client);
        sceneManager.switchScene("gameArea");

        } else {
            playError1();
        }

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