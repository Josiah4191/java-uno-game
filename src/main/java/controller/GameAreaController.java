package controller;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.AudioClip;
import model.cardgame.card.unocard.UnoCard;
import model.cardgame.card.unocard.UnoSuit;
import model.cardgame.unogame.PlayDirection;
import model.image.cardimage.UnoCardImageManager;
import model.player.cardplayer.unoplayer.UnoPlayer;
import model.cardgame.unogame.ClientUnoGameManager;
import model.cardgame.unogame.UnoGameState;
import multiplayer.client.Client;
import multiplayer.server.servermessage.*;

import java.util.Optional;

public class GameAreaController implements GameAreaListener {

    private Client client;
    private SceneManager sceneManager;
    private ClientUnoGameManager gameManager;
    private UnoGameState gameState;
    private boolean drawCard = false;

    private AudioClip click1 = new AudioClip(getClass().getResource("/audio/click1.wav").toString());
    private AudioClip click2 = new AudioClip(getClass().getResource("/audio/double_click.wav").toString());
    private AudioClip error1 = new AudioClip(getClass().getResource("/audio/retro.wav").toString());
    private AudioClip confirm1 = new AudioClip(getClass().getResource("/audio/confirm.wav").toString());
    private AudioClip BGMusic = new AudioClip(getClass().getResource("/audio/Evening_harmony.mp3").toString());

    @FXML
    private AnchorPane rootAnchorPane;
    @FXML
    private StackPane settingsOuterStackPane;
    @FXML
    private StackPane settingsInnerStackPane;
    @FXML
    private VBox settingsVbox;
    @FXML
    private Label settingsLbl;
    @FXML
    private GridPane settingsGridPane;
    @FXML
    private VBox settingsBtnVbox;
    @FXML
    private Button settingsBtn;
    @FXML
    private StackPane centerStackPane;
    @FXML
    private GridPane centerGridPane;
    @FXML
    private HBox playDirectionHbox = new HBox();
    @FXML
    private Label playDirectionLbl;
    @FXML
    private FlowPane opponentPlayerFlowPane;
    @FXML
    private HBox pilesHbox;
    @FXML
    private Button drawPileBtn;
    @FXML
    private Button discardPileBtn;
    @FXML
    private FlowPane playerCardsFlowPane;
    @FXML
    private StackPane playerControlsStackPane;
    @FXML
    private VBox localPlayerVbox;
    @FXML
    private Label localPlayerNameLbl;
    @FXML
    private Label localPlayerImageLbl;
    @FXML
    private Label localPlayerCardNumberLbl;
    @FXML
    private FlowPane suitColorSelectionFlowPane;
    @FXML
    private Label yellowCardLbl;
    @FXML
    private Label redCardLbl;
    @FXML
    private Label blueCardLbl;
    @FXML
    private Label greenCardLbl;
    @FXML
    private HBox passTurnHbox;
    @FXML
    private Button passTurnBtn;
    @FXML
    private HBox controlsHBox;
    @FXML
    private Button sayUnoBtn;
    @FXML
    private Button rulesBtn;
    @FXML
    private Button interfaceBtn;
    @FXML
    private Button keybindingsBtn;
    @FXML
    private Button newGameBtn;
    @FXML
    private Button saveGameBtn;
    @FXML
    private Button loadGameBtn;
    @FXML
    private Button mainMenuBtn;
    @FXML
    private Button exitBtn;
    @FXML
    private Button returnBtn;

    public void initialize() {
        settingsBtnVbox.toFront();
        playerControlsStackPane.toFront();
        initializeBtns();
        hideSuitColorSelection();
        setRootAnchorPaneKeybind();
    }

    public void initializeBtns() {
        setSayUnoBtnHandler();
        setPassTurnBtnHandler();
        setSuitColorSelectionHandler();
        setQuitBtnHandler();
        setSettingsBtnHandler();
        setMainMenuBtnHandler();
        setNewGameBtnHandler();
        setReturnBtnHandler();
        setRulesBtnHandler();
        disableBtn(sayUnoBtn);
        disableBtn(passTurnBtn);

    }

    public void disableBtn(Button button) {
        button.setDisable(true);
        button.setStyle("-fx-opacity: 0.5;");
    }

    public void enableBtn(Button button) {
        button.setDisable(false);
        button.setStyle("-fx-opacity: 1;");
    }

    public void setGameManager(ClientUnoGameManager gameManager) {
        this.gameManager = gameManager;
    }

    public void setGameState(UnoGameState gameState) {
        this.gameState = gameState;
        updateAll();
    }

    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    public SceneManager getSceneManager() {
        return sceneManager;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void playClick1() {
        click1.play();
    }

    public void playClick2() {
        click2.play();
    }

    public void playError1() {
        error1.play();
    }

    public void playConfirm1() {
        confirm1.play();
    }

    public void playBGMusic() {
        BGMusic.play();
    }

    public void updateAll() {
        updateDiscardPileImage();
        updateLocalPlayerCards();
        updateLocalPlayer();
        updateLocalPlayerCardHandler();
        updateOpponentPlayers();
        updatePlayDirection();
        highlightCurrentPlayer();
        highlightCurrentSuitColor();
        updateDrawPileCardHandler();
        showUnoBtn();
    }

    public void updateGameView(GameEvent event) {
        Platform.runLater(new Runnable() {
            public void run() {
                GameEventType type = event.getType();

                switch (type) {
                    case GameEventType.CARD_DRAWN, GameEventType.TURN_PASSED, GameEventType.CARD_PLAYED,
                         GameEventType.ANNOUNCE_WINNER, GameEventType.APPLY_PENALTY, GameEventType.SUIT_CHANGED:
                        updateAll();
                        break;
                    case GameEventType.NAME_CHANGED:
                        updateLocalPlayerCards();
                        updateOpponentPlayers();
                        break;
                    case GameEventType.IMAGE_CHANGED:
                        updateLocalPlayer();
                        updateOpponentPlayers();
                        break;
                    case GameEventType.SAID_UNO:
                        System.out.println("Controller updating SAY UNO");
                        break;
                    case GameEventType.NO_OP:
                        NoOpEvent noOpEvent = (NoOpEvent) event;
                        switch (noOpEvent.getEventType()) {
                            case NoOpEventType.INVALID_CARD, NoOpEventType.INVALID_TURN:
                                //playError1();
                                System.out.println("Game Area Controller received NoOpEvent. Not playing Error1");
                                break;
                        }
                        break;
                    default:
                        System.out.println("Controller: UNKNOWN EVENT OCCURRED");
                        break;
                }
            }
        });
    }

    public void updateDiscardPileImage() {
        UnoCardImageManager imageManager = gameState.getCardImageManager();
        UnoCard lastPlayedCard = gameState.getLastPlayedCard();

        Image image = imageManager.getImage(lastPlayedCard);
        ImageView imageView = new ImageView(image);

        imageView.setFitHeight(100);
        imageView.setFitWidth(100);

        discardPileBtn.setGraphic(imageView);
    }

    public void updateLocalPlayerCards() {
        UnoPlayer localPlayer = gameState.getLocalPlayer();
        int numberOfCards = localPlayer.getPlayerHand().size();
        playerCardsFlowPane.getChildren().clear();
        for (int i = 0; i < numberOfCards; i++) {
            Button cardBtn = new Button("");
            UnoCardImageManager imageManager = gameState.getCardImageManager();
            UnoCard card = localPlayer.getPlayerHand().get(i);

            Image cardImage = imageManager.getImage(card);
            ImageView imageView = new ImageView(cardImage);

            imageView.setFitHeight(60);
            imageView.setFitWidth(60);

            cardBtn.setGraphic(imageView);
            cardBtn.setUserData(card);

            if (!(localPlayer.equals(gameState.getCurrentPlayer()))) {
                cardBtn.setStyle("-fx-opacity: 0.5;");
                cardBtn.setOnMouseEntered(e -> cardBtn.setStyle("-fx-opacity: 1;"));
                cardBtn.setOnMouseExited(e -> cardBtn.setStyle("-fx-opacity: 0.5;"));
            }

            playerCardsFlowPane.getChildren().add(cardBtn);
        }
    }

    public void updateLocalPlayer() {
        UnoPlayer localPlayer = gameState.getLocalPlayer();
        Image playerImage = gameState.getPlayerImageManager().getImage(localPlayer.getImage());
        ImageView playerImageView = new ImageView(playerImage);

        playerImageView.setFitHeight(60);
        playerImageView.setFitWidth(60);

        localPlayerImageLbl.setUserData(localPlayer);
        localPlayerImageLbl.setGraphic(playerImageView);
        localPlayerNameLbl.setText(localPlayer.getName());

        localPlayerCardNumberLbl.setText(String.valueOf(localPlayer.getPlayerHand().size()));
    }

    public void updateOpponentPlayers() {

        var opponentPlayers = gameState.getPlayers()
                .stream()
                .filter(player -> !player.equals(gameState.getLocalPlayer()))
                .toList();

        opponentPlayerFlowPane.getChildren().clear();

        for (UnoPlayer player : opponentPlayers) {
            VBox opponentBox = new VBox();
            opponentBox.setAlignment(Pos.CENTER);

            if (!player.equals(gameState.getCurrentPlayer())) {
                opponentBox.setOnMouseEntered(e -> opponentBox.setStyle("-fx-opacity: 1;"));
            }

            opponentBox.setOnMouseExited(e -> highlightOpponentPlayer());

            Label cardNumberLbl = new Label();
            int playerIndex = gameState.getPlayers().indexOf(player);
            int totalCardsRemaining = gameState.getPlayerIndexToCardNumber().get(playerIndex);
            cardNumberLbl.setText(String.valueOf(totalCardsRemaining));

            if (player.getPlayerHand().size() == 1) {
                cardNumberLbl.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: yellow;");
            } else {
                cardNumberLbl.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: white;");
            }

            Label playerImageLbl = new Label();
            Label playerNameLbl = new Label(player.getName());

            Image playerImage = gameState.getPlayerImageManager().getImage(player.getImage());
            ImageView playerImageView = new ImageView(playerImage);
            playerNameLbl.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: white;");

            playerImageView.setFitHeight(60);
            playerImageView.setFitWidth(60);

            playerImageLbl.setGraphic(playerImageView);
            playerImageLbl.setPrefWidth(60);

            opponentBox.setUserData(player);
            opponentBox.getChildren().addAll(cardNumberLbl, playerImageLbl, playerNameLbl);
            opponentPlayerFlowPane.getChildren().add(opponentBox);
        }
    }

    public void updatePlayDirection() {
        PlayDirection direction = gameState.getPlayDirection();
        if (direction == PlayDirection.REVERSE) {
            playDirectionLbl.setRotate(180);
        } else {
            playDirectionLbl.setRotate(0);
        }
    }

    public void highlightCurrentPlayer() {
        highlightLocalPlayer();
        highlightOpponentPlayer();
    }

    public void highlightLocalPlayer() {
        UnoPlayer currentPlayer = gameState.getCurrentPlayer();
        UnoPlayer localPlayer = gameState.getLocalPlayer();

        if (currentPlayer.equals(localPlayer)) {
            localPlayerVbox.setStyle("-fx-effect: dropshadow(three-pass-box, white, 30, 0.6, 0, 0);" +
                    "-fx-opacity: 1");
        } else {
            localPlayerVbox.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0.5, 0, 0);");
        }
    }

    public void highlightOpponentPlayer() {
        UnoPlayer currentPlayer = gameState.getCurrentPlayer();

        for (var box : opponentPlayerFlowPane.getChildren()) {

            if (box.getUserData().equals(currentPlayer)) {
                box.setStyle("-fx-effect: dropshadow(three-pass-box, white, 30, 0.6, 0, 0);" +
                        "-fx-opacity: 1");
            } else {
                box.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0.5, 0, 0);" +
                        "-fx-opacity: 0.5");
            }
        }
    }

    public void highlightCurrentSuitColor() {
        UnoSuit suit = gameState.getCurrentSuit();
        String color = suit.getColor();
        String style = String.format("-fx-effect: dropshadow(three-pass-box, %s, 20, 0.5, 0, 0);", color);
        discardPileBtn.setStyle(style);
    }

    /*
    public void highlightPlayableCards() {
        UnoPlayer localPlayer = gameState.getLocalPlayer();
        UnoPlayer currentPlayer = gameState.getCurrentPlayer();
        var playableCards = localPlayer.getPlayableCards(gameState);

        if (localPlayer.equals(currentPlayer)) {
            playerCardsFlowPane.getChildren().forEach(cardBtn -> {

                UnoCard card = (UnoCard) cardBtn.getUserData();

                if (playableCards.contains(card)) {
                    cardBtn.setStyle("-fx-opacity: 1;");
                } else {
                    cardBtn.setStyle("-fx-opacity: 0.5;");
                }
            });
        }
    }
     */

    public void showUnoBtn() {
        UnoPlayer localPlayer = gameState.getLocalPlayer();
        if (localPlayer.getPlayerHand().size() == 1 && !(localPlayer.getSayUno())) {
            playConfirm1();
            enableBtn(sayUnoBtn);
        } else {
            disableBtn(sayUnoBtn);
        }
    }

    public void announceWinner(UnoPlayer player) {
        Platform.runLater(new Runnable() {
            public void run() {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, player.getName() + " has won!\n\nClick 'Ok' to begin a New Game.", ButtonType.OK);
                alert.setHeaderText("");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                }
            }
        });
    }

    public void showSuitColorSelection() {
        suitColorSelectionFlowPane.setManaged(true);
        suitColorSelectionFlowPane.setVisible(true);
        suitColorSelectionFlowPane.toFront();
    }

    public void hideSuitColorSelection() {
        suitColorSelectionFlowPane.setVisible(false);
    }

    public void setSuitColorSelectionHandler() {
        suitColorSelectionFlowPane.getChildren().forEach(suitLbl -> {
            String suitString = (String) suitLbl.getUserData();
            UnoSuit suit = UnoSuit.valueOf(suitString);

            suitLbl.setOnMouseClicked(e -> {
                gameManager.changeSuitColor(suit);
                hideSuitColorSelection();
            });
        });
    }

    public void updateLocalPlayerCardHandler() {

        var playableCards = gameState.getPlayableCards();

        playerCardsFlowPane.getChildren().forEach(cardBtn -> {
            UnoCard card = (UnoCard) cardBtn.getUserData();
            int cardIndex = gameManager.getLocalPlayer().getPlayerHand().indexOf(card);

            if (playableCards.contains(card)) {
                cardBtn.setOnMouseClicked(e -> {
                    disableBtn(passTurnBtn);
                    drawCard = false;

                    gameManager.playCard(cardIndex);

                    if (card.getSuit() == UnoSuit.WILD) {
                        gameManager.changeSuitColor(UnoSuit.WILD);
                        showSuitColorSelection();
                    } else {
                        hideSuitColorSelection();
                    }
                });

            } else {
                cardBtn.setOnMouseClicked(e -> playError1());
            }

        });
    }

    public void updateDrawPileCardHandler() {
        UnoPlayer currentPlayer = gameState.getCurrentPlayer();
        UnoPlayer localPlayer = gameState.getLocalPlayer();

        drawPileBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                if (currentPlayer.equals(localPlayer) && (!drawCard)) {
                    gameManager.drawCard();
                    enableBtn(passTurnBtn);
                    drawCard = true;
                } else {
                    playError1();
                }
            }
        });
    }

    public void setPassTurnBtnHandler() {
        passTurnBtn.setOnMouseClicked(e -> {
            gameManager.passTurn();
            disableBtn(passTurnBtn);
            drawCard = false;
        });
    }

    public void setSayUnoBtnHandler() {
        sayUnoBtn.setOnMouseClicked(e -> {
            gameManager.sayUno();
            disableBtn(sayUnoBtn);
        });
    }

    public void setSettingsBtnHandler() {
        settingsBtn.setOnMouseClicked(e -> {
            settingsOuterStackPane.setVisible(!(settingsOuterStackPane.isVisible()));
            settingsOuterStackPane.toFront();
        });
    }

    public void setRulesBtnHandler() {
    }

    public void setRootAnchorPaneKeybind() {
        rootAnchorPane.setOnKeyPressed(e -> {
            KeyCode code = e.getCode();
            if (code == KeyCode.ESCAPE) {
                settingsOuterStackPane.setVisible(!(settingsOuterStackPane.isVisible()));
                settingsOuterStackPane.toFront();
                /*
                if (rulesInnerStackPane.isVisible()) {
                    rulesInnerStackPane.setVisible(false);
                    rulesInnerStackPane.toFront();
                } else {
                    settingsOuterStackPane.setVisible(!(settingsOuterStackPane.isVisible()));
                    settingsOuterStackPane.toFront();
                }
                 */
            } else if (code == KeyCode.DIGIT1) {
                gameManager.sayUno();
                disableBtn(sayUnoBtn);
            } else if (code == KeyCode.DIGIT2) {
                gameManager.passTurn();
                disableBtn(passTurnBtn);
            }
        });
    }

    public void quit() {
        client.getServer().shutDown();
        System.out.println("From Game Area Controller: Server shutting down");
        System.out.flush();
        Platform.exit();
    }

    public void setQuitBtnHandler() {
        exitBtn.setOnMouseClicked(se -> {
            quit();
        });
    }

    public void setMainMenuBtnHandler() {
        mainMenuBtn.setOnMouseClicked(e -> {
            client.getServer().shutDown();
            System.out.println("From Game Area Controller: Server shutting down");
            System.out.flush();

            sceneManager.switchScene("gameSelection");
            sceneManager.removeScene("gameArea");
        });
    }

    public void setNewGameBtnHandler() {
        newGameBtn.setOnMouseClicked(e -> {
            client.getServer().shutDown();
            System.out.println("From Game Area Controller: Server shutting down");
            System.out.flush();

            sceneManager.switchScene("offline");
            sceneManager.removeScene("gameArea");
        });
    }

    public void setReturnBtnHandler() {
        returnBtn.setOnMouseClicked(e -> {
            settingsOuterStackPane.setVisible(!(settingsOuterStackPane.isVisible()));
            settingsOuterStackPane.toFront();
        });
    }
}