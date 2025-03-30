package controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
import multiplayer.client.clientmessage.GameAction;
import multiplayer.server.servermessage.GameEvent;
import multiplayer.server.servermessage.GameEventType;

import java.util.Optional;

public class GameAreaController implements GameAreaListener {

    private Client client;
    private SceneManager sceneManager;
    private ClientUnoGameManager gameManager;
    private UnoGameState gameState;

    private AudioClip click1 = new AudioClip(getClass().getResource("/audio/click1.wav").toString());
    private AudioClip click2 = new AudioClip(getClass().getResource("/audio/double_click.wav").toString());
    private AudioClip error1 = new AudioClip(getClass().getResource("/audio/retro.wav").toString());
    private AudioClip confirm1 = new AudioClip(getClass().getResource("/audio/confirm.wav").toString());
    private AudioClip BGMusic = new AudioClip(getClass().getResource("/audio/Evening_harmony.mp3").toString());

    @FXML
    private StackPane centerStackPane;
    @FXML
    private GridPane centerGridPane;
    @FXML
    private VBox settingsVbox;
    @FXML
    private Button settingsBtn;
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
    private HBox playerControlsHbox;
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
    private HBox sayUnoBtnHbox;
    @FXML
    private Button sayUnoBtn;

    public void initialize() {
        initializeBtns();
        settingsVbox.toFront();
        //playBGMusic();
    }

    public void setGameManager(ClientUnoGameManager gameManager) {
        this.gameManager = gameManager;
    }

    public void setGameState(UnoGameState gameState) {
        this.gameState = gameState;
        updateAll();
        setSuitColorSelectionHandler();
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

    public void initializeBtns() {
        sayUnoBtn.setOnMouseEntered(e -> sayUnoBtn.setStyle("-fx-opacity: 1;"));
        sayUnoBtn.setOnMouseExited(e -> sayUnoBtn.setStyle("-fx-opacity: 0.5;"));

        passTurnBtn.setOnMouseEntered(e -> passTurnBtn.setStyle("-fx-opacity: 1;"));
        passTurnBtn.setOnMouseExited(e -> passTurnBtn.setStyle("-fx-opacity: 0.5;"));
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
        updateOpponentPlayers();
        updatePlayDirection();
        updateLocalPlayerCardHandler();
        highlightCurrentPlayer();
        highlightCurrentSuitColor();
        highlightUnoBtn();
        highlightPassTurnBtn();
    }

    public void updateGameView(GameEvent event) {
        Platform.runLater(new Runnable() {
            public void run() {
                GameEventType type = event.getType();

                switch (type) {
                    case GameEventType.TURN_PASSED, GameEventType.CARD_DRAWN, GameEventType.CARD_PLAYED,
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
                        System.out.println("Controller NO OPERATION");
                        break;
                    default:
                        System.out.println("Controller UNKNOWN EVENT");
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
            System.out.println("Current player does equal local player: " + true);
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

    public void highlightUnoBtn() {
        UnoPlayer localPlayer = gameState.getLocalPlayer();
        if (localPlayer.getPlayerHand().size() == 1 && !(localPlayer.getSayUno())) {
            playConfirm1();
            sayUnoBtn.setStyle("-fx-effect: dropshadow(three-pass-box, white, 30, 0.5, 0, 0);" +
                    "-fx-opacity: 1");
        } else if (localPlayer.getPlayerHand().size() > 1) {
            localPlayer.sayUno(false);
            sayUnoBtn.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 30, 0.5, 0, 0);" +
                    "-fx-opacity: 0.5");
        }
    }

    public void highlightPassTurnBtn() {
        boolean isPassTurn = gameState.getLocalPlayer().isPassTurn();
        if (isPassTurn) {
            passTurnBtn.setStyle("-fx-effect: dropshadow(three-pass-box, white, 30, 0.5, 0, 0);" +
                    "-fx-opacity: 1");
        } else {
            passTurnBtn.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 30, 0.5, 0, 0);" +
                    "-fx-opacity: 0.5");
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
                GameAction changeSuitAction = gameManager.changeSuitColor(suit);
                client.sendMessage(changeSuitAction.toJson());
                hideSuitColorSelection();
            });
        });
    }

    public void updateLocalPlayerCardHandler() {
        UnoPlayer currentPlayer = gameState.getCurrentPlayer();
        UnoPlayer localPlayer = gameState.getLocalPlayer();

        playerCardsFlowPane.getChildren().forEach(cardBtn -> {
            if (localPlayer.equals(currentPlayer)) {
                UnoCard card = (UnoCard) cardBtn.getUserData();
                int cardIndex = gameManager.getLocalPlayer().getPlayerHand().indexOf(card);
                var playableCards = gameManager.getLocalPlayer().getPlayableCards(gameState);

                if (playableCards.contains(card)) {

                    cardBtn.setOnMouseClicked(e -> {
                        GameAction playCardAction = gameManager.playCard(cardIndex);
                        client.sendMessage(playCardAction.toJson());

                        if (card.getSuit() == UnoSuit.WILD) {
                            showSuitColorSelection();
                        }

                    });

                } else {
                    cardBtn.setOnMouseClicked(e -> playError1());
                }
            } else {
                cardBtn.setOnMouseClicked(e -> playError1());
            }
        });
        highlightPlayableCards();
    }
}
    /*
    public void setDrawPileCardHandler() {
        UnoPlayer mainPlayer = gameState.getLocalPlayer();
        UnoPlayer currentPlayer = gameState.getCurrentPlayer();
        int currentPlayerIndex = gameState.getCurrentPlayerIndex();
        currentPlayer.setPassTurn(true);

        drawPileBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {

                if (currentPlayer.equals(mainPlayer)) {
                    if (currentPlayer.isPassTurn()) {
                        playClick1();
                        passBtn.setVisible(true);
                        //boolean playable = gameManager.playerDrawCardFromDrawPile(currentPlayerIndex);

                        setDiscardPileImage();
                        setPlayer();
                        setPlayerCardImages();
                        darkenNonPlayableCardsAfterDraw();
                        //setPlayableDrawCardHandler();
                        drawPileBtn.setOnMouseClicked(e -> playError1());

                        if (playable) {
                            System.out.println("The card is playable");
                            showUnoBtn();
                        } else {
                            System.out.println("The card is not playable");
                        }

                        currentPlayer.setPassTurn(false);
                    }
                } else {
                    playError1();
                }
            }
        });
    }
     */
/*
    public void setPassBtnHandler() {
        passBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                playClick2();
                gameManager.continueTurnCycle();
                passBtn.setVisible(false);
                updateGameArea();
            }
        });
    }
 */

    /*
    public void setPlayableDrawCardHandler() {
        UnoPlayer mainPlayer = gameState.getLocalPlayer();
        UnoCard lastDrawCard = mainPlayer.getLastDrawCard();
        int lastDrawCardIndex = mainPlayer.getPlayerHand().indexOf(lastDrawCard);
        int currentPlayerIndex = gameState.getCurrentPlayerIndex();
        var cardBtns = playerCardsBox.getChildren();

        for (var button : cardBtns) {
            UnoCard card = (UnoCard) button.getUserData();
            if (card.equals(lastDrawCard)) {
                button.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent event) {

                            playClick2();
                            passBtn.setVisible(false);
                            updateGameArea();
                            if (card.getSuit() == UnoSuit.WILD) {
                                showSuitColorSelection();
                            } else {
                                gameManager.continueTurnCycle();
                            }
                        } else {
                            playError1();
                        }
                    }
                });
            } else {
                button.setOnMouseClicked(e -> playError1());
            }
        }
    }

    public void highlightCurrentSuitColor() {
        UnoSuit suit = gameState.getCurrentSuit();
        if (!(suit == UnoSuit.WILD)) {
            String color = suit.getColor();
            String style = String.format("-fx-effect: dropshadow(three-pass-box, %s, 20, 0.5, 0, 0);", color);
            discardPileBtn.setStyle(style);
        }
    }

    public void darkenNonPlayableCardsAfterDraw() {
        UnoPlayer mainPlayer = gameState.getLocalPlayer();
        UnoCard lastDrawCard = mainPlayer.getLastDrawCard();

        var cardButtons = playerCardsBox.getChildren();
        var playableCards = mainPlayer.getPlayableCards(gameState);

        if (playableCards.contains(lastDrawCard)) {
            System.out.println("Does contain last draw card");
            cardButtons.stream()
                    .filter(e -> !e.getUserData().equals(lastDrawCard))
                    .forEach(e -> e.setStyle("-fx-opacity: 0.5;"));
        } else {
            cardButtons.forEach(e -> e.setStyle("-fx-opacity: 0.5;"));
        }

    }

    public void darkenNonPlayableCards() {
        UnoPlayer mainPlayer = gameState.getLocalPlayer();
        UnoPlayer currentPlayer = gameState.getCurrentPlayer();
        var nonPlayableCards = mainPlayer.getNonPlayableCards(gameState);
        var cardButtons = playerCardsBox.getChildren();

        if (currentPlayer.equals(mainPlayer)) {
            for (UnoCard card : nonPlayableCards) {
                for (var button : cardButtons) {
                    if (card.equals(button.getUserData())) {
                        button.setStyle("-fx-opacity: 0.5;");
                    }
                }
            }
        }
    }

    /*
    public void setOpponents() {
        var opponentPlayers = gameState.getPlayers().stream()
                .filter((player) -> !(player.equals(gameState.getLocalPlayer())))
                .toList();

        opponentPlayerBox.getChildren().clear();
        for (UnoPlayer player : opponentPlayers) {
            VBox opponentBox = new VBox();
            opponentBox.setAlignment(Pos.CENTER);

            if (!player.equals(gameState.getCurrentPlayer())) {
                opponentBox.setOnMouseEntered(e -> opponentBox.setStyle("-fx-opacity: 1;"));
            }

            opponentBox.setOnMouseExited(e -> highlightOpponentPlayer());

            Label cardNumberLbl = new Label();
            cardNumberLbl.setText(String.valueOf(player.getPlayerHand().size()));

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

            opponentBox.setUserData(player);
            opponentBox.getChildren().addAll(cardNumberLbl, playerImageLbl, playerNameLbl);
            opponentPlayerBox.getChildren().add(opponentBox);
        }
    }

    public void highlightMainPlayer() {
        UnoPlayer currentPlayer = gameState.getCurrentPlayer();
        UnoPlayer nextPlayer = gameManager.getNextPlayer();
        UnoPlayer mainPlayer = gameState.getLocalPlayer();

        if (currentPlayer.equals(mainPlayer)) {
            playerBox.setStyle("-fx-effect: dropshadow(three-pass-box, white, 30, 0.6, 0, 0);" +
                    "-fx-opacity: 1");
        } else if (nextPlayer.equals(mainPlayer)) {
            playerBox.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0.5, 0, 0);");
        } else {
            playerBox.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0.5, 0, 0);");
        }
    }

    /*
    public void highlightOpponentPlayer() {
        UnoPlayer currentPlayer = gameState.getCurrentPlayer();
        UnoPlayer nextPlayer = gameManager.getNextPlayer();

        for (var box : opponentPlayerBox.getChildren()) {

            if (box.getUserData().equals(currentPlayer)) {
                box.setStyle("-fx-effect: dropshadow(three-pass-box, white, 30, 0.6, 0, 0);" +
                        "-fx-opacity: 1");
            } else if (box.getUserData().equals(nextPlayer)) {
                box.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0.5, 0, 0);" +
                        "-fx-opacity: 0.5");
            } else {
                box.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0.5, 0, 0);" +
                        "-fx-opacity: 0.5");
            }
        }
    }
     */

    /*

    public void setSayUnoBtnHandler() {
        playClick2();
        gameState.getLocalPlayer().sayUno(true);
        unoBtn.setVisible(false);
    }

    /*
    public void setCallUnoHandler() {
        for (var opponent : opponentPlayerBox.getChildren()) {
            UnoPlayer player = (UnoPlayer) opponent.getUserData();
            int playerIndex = gameState.getPlayerIndex(player);
            opponent.setOnMouseClicked(e -> gameManager.callUno(playerIndex));
        }
    }


    public void updateGameAreaView() {
        Platform.runLater(this::updateGameArea);
    }



    public void setSuitColorSelectionHandler() {
        redCardLbl.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                playClick2();
                gameState.setCurrentSuit(UnoSuit.RED);
                suitColorSelectionBox.setVisible(false);
                gameManager.continueTurnCycle();
                updateGameArea();
            }
        });

        greenCardLbl.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                playClick2();
                gameState.setCurrentSuit(UnoSuit.GREEN);
                suitColorSelectionBox.setVisible(false);
                gameManager.continueTurnCycle();
                updateGameArea();
            }
        });

        blueCardLbl.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                playClick2();
                gameState.setCurrentSuit(UnoSuit.BLUE);
                suitColorSelectionBox.setVisible(false);
                gameManager.continueTurnCycle();
                updateGameArea();
            }
        });

        yellowCardLbl.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                playClick2();
                gameState.setCurrentSuit(UnoSuit.YELLOW);
                suitColorSelectionBox.setVisible(false);
                gameManager.continueTurnCycle();
                updateGameArea();
            }
        });
    }

    /*
    public void setPlayerCardHandler() {
        var playerCardLbls = playerCardsBox.getChildren();
        for (var label : playerCardLbls) {

            label.setOnMouseClicked(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent mouseEvent) {
                    UnoCard card = (UnoCard) label.getUserData();
                    UnoPlayer mainPlayer = gameState.getLocalPlayer();
                    UnoPlayer currentPlayer = gameState.getCurrentPlayer();
                    int currentPlayerIndex = gameState.getCurrentPlayerIndex();
                    int cardIndex = currentPlayer.getPlayerHand().indexOf(card);

                    if (currentPlayer.equals(mainPlayer)) {

                        int playerID = currentPlayer.getPlayerID();
                        System.out.println("Main player ID: " + mainPlayer.getPlayerID());
                        System.out.println("Current player ID: " + playerID);

                        boolean validCard = gameManager.humanPlayCard(currentPlayerIndex, cardIndex);
                        updateGameArea();

                        if (validCard) {
                            playClick2();

                            if (gameManager.checkWinner(mainPlayer)) {
                                announceWinner(mainPlayer);
                                return;
                            }

                            if (card.getSuit() == UnoSuit.WILD) {
                                showSuitColorSelection();
                            } else {
                                gameManager.continueTurnCycle();
                            }
                            updateGameArea();
                        } else {
                            playError1();
                        }
                    } else {
                        playError1();
                    }
                }
            });
        }
    }
}
*/