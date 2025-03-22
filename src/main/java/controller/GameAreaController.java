package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.AudioClip;
import model.cardgames.cards.unocards.UnoCard;
import model.cardgames.cards.unocards.UnoSuit;
import model.cardgames.unogame.PlayDirection;
import model.cardgames.unogame.UnoGameManager;
import model.cardgames.unogame.UnoGameState;
import model.images.cardimages.UnoCardImageManager;
import model.players.cardplayers.unoplayers.UnoPlayer;

import java.util.Optional;

public class GameAreaController implements GameAreaListener {

    private UnoGameManager gameManager;
    private UnoGameState gameState;

    private AudioClip click1 = new AudioClip(getClass().getResource("/audio/click.wav").toString());
    private AudioClip error1 = new AudioClip(getClass().getResource("/audio/Retro12.wav").toString());
    private AudioClip confirm1 = new AudioClip(getClass().getResource("/audio/confirm.wav").toString());
    private AudioClip click2 = new AudioClip(getClass().getResource("/audio/double_click.wav").toString());

    @FXML
    private VBox menuBox;
    @FXML
    private Label menuBtn;
    @FXML
    private VBox menuBtnBox = new VBox();
    @FXML
    private Button newGameBtn = new Button();
    @FXML
    private Button saveGameBtn = new Button();
    @FXML
    private Button loadGameBtn = new Button();
    @FXML
    private HBox playDirectionBox = new HBox();
    @FXML
    private Label playDirectionLbl;
    @FXML
    private Tooltip playDirectionToolTip;
    @FXML
    private HBox opponentPlayerBox;
    @FXML
    private HBox pilesBox;
    @FXML
    private Button drawPileBtn;
    @FXML
    private Button discardPileBtn;
    @FXML
    private FlowPane playerCardsBox;
    @FXML
    private VBox playerBox;
    @FXML
    private Label playerNameLbl;
    @FXML
    private Label playerImageLbl;
    @FXML
    private Label playerCardNumberLbl;
    @FXML
    private VBox centerVBox;
    @FXML
    private FlowPane suitColorSelectionBox;
    @FXML
    private Label yellowCardLbl;
    @FXML
    private Label redCardLbl;
    @FXML
    private Label blueCardLbl;
    @FXML
    private Label greenCardLbl;
    @FXML
    private HBox passBtnBox;
    @FXML
    private Button passBtn;
    @FXML
    private HBox unoBtnBox;
    @FXML
    private Button unoBtn;

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

    public void initialize() {
        initializeMenu();
    }

    public void initializeMenu() {
        menuBox.toFront();
        menuBtnBox.setVisible(false);
        hideMenu();
    }


    public void setGameManager(UnoGameManager gameManager) {
        this.gameManager = gameManager;
    }

    public void setGameState(UnoGameState gameState) {
        this.gameState = gameState;
        setOpponents();
        setDiscardPileImage();
        setPlayerCardImages();
        setPlayer();
        updateGameArea();
        gameManager.setGameAreaListener(this);
    }

    public void updateGameArea() {
        setDiscardPileImage();
        setPlayerCardImages();
        setPlayer();
        setOpponents();
        highlightPlayers();
        setPlayerCardHandler();
        highlightPlayableCards();
        setDrawPileCardHandler();
        highlightCurrentSuitColor();
        setPassBtnHandler();
        setSuitColorSelectionHandler();
        setCallUnoHandler();
        showUnoBtn();
        displayCardInformation();

        /*
        setNewGameBtnHandler();
        setSaveGameBtnHandler();
        setLoadGameBtnHandler();
         */
    }

    public void updatePlayDirection() {
        PlayDirection direction = gameState.getDirection();
        if (direction == PlayDirection.REVERSE) {
            playDirectionLbl.setRotate(180);
        } else {
            playDirectionLbl.setRotate(0);
        }
    }

    public void displayCardInformation() {
        var players = gameState.getPlayers();
        int drawPile = gameState.getDrawPile().size();
        int discardPile = gameState.getDiscardPile().size();
        int totalCards = drawPile + discardPile;

        System.out.println("Draw Pile: " + drawPile);
        System.out.println("Discard Pile: " + discardPile);

        for (UnoPlayer player: players) {
            totalCards += player.getPlayerHand().size();
        }

        players.forEach(p -> System.out.println(p.getName() + ": " + p.getPlayerHand().size() + " cards."));

        System.out.println("Total Number of Cards: " + totalCards);
    }

    public void showUnoBtn() {
        UnoPlayer mainPlayer = gameState.getMainPlayer();
        if (mainPlayer.getPlayerHand().size() == 1 && !(mainPlayer.getSayUno())) {
            playConfirm1();
            unoBtn.setVisible(true);
        } else if (mainPlayer.getPlayerHand().size() > 1) {
            mainPlayer.sayUno(false);
        }
    }

    public void setDrawPileCardHandler() {
        UnoPlayer mainPlayer = gameState.getMainPlayer();
        UnoPlayer currentPlayer = gameState.getCurrentPlayer();
        int currentPlayerIndex = gameState.getCurrentPlayerIndex();
        currentPlayer.setPassTurn(true);

        drawPileBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {

                if (currentPlayer.equals(mainPlayer)) {
                    if (currentPlayer.isPassTurn()) {
                        playClick1();
                        passBtn.setVisible(true);
                        boolean playable = gameManager.playerDrawCard(currentPlayerIndex);

                        setDiscardPileImage();
                        setPlayer();
                        setPlayerCardImages();

                        if (playable) {
                            highlightDrawCard();
                            setPlayableDrawCardHandler();
                            showUnoBtn();
                        }

                        currentPlayer.setPassTurn(false);
                    }
                } else {
                    playError1();
                }
            }
        });
    }

    public void setPassBtnHandler() {
        passBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                playClick2();
                gameManager.moveToNextPlayer();
                gameManager.startAIRunning();
                passBtn.setVisible(false);
                updateGameArea();
            }
        });
    }

    public void setPlayableDrawCardHandler() {
        UnoPlayer mainPlayer = gameState.getMainPlayer();
        UnoCard lastDrawCard = mainPlayer.getLastDrawCard();
        int lastDrawCardIndex = mainPlayer.getPlayerHand().indexOf(lastDrawCard);
        int currentPlayerIndex = gameState.getCurrentPlayerIndex();
        var cardBtns = playerCardsBox.getChildren();

        for (var button : cardBtns) {
            UnoCard card = (UnoCard) button.getUserData();
            if (card.equals(lastDrawCard)) {
                button.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent event) {
                        boolean valid = gameManager.playCard(currentPlayerIndex, lastDrawCardIndex);
                        passBtn.setVisible(false);
                        updateGameArea();

                        if (valid) {
                            if (card.getSuit() == UnoSuit.WILD) {
                                showSuitColorSelection();
                            } else {
                                gameManager.startAIRunning();
                            }
                        }
                    }
                });
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

    public void highlightDrawCard() {
        UnoPlayer mainPlayer = gameState.getMainPlayer();
        UnoPlayer currentPlayer = gameState.getCurrentPlayer();
        UnoCard lastDrawCard = mainPlayer.getLastDrawCard();
        var cardBtns = playerCardsBox.getChildren();

        if (currentPlayer.equals(mainPlayer)) {
            for (var button : cardBtns) {
                if (lastDrawCard.equals(button.getUserData())) {
                    UnoCard card = (UnoCard) button.getUserData();
                    String style = String.format("-fx-effect: dropshadow(three-pass-box, %s, 20, 0.5, 0, 0);", card.getSuit().getColor());
                    button.setStyle(style);
                }
            }
        }
    }

    public void highlightPlayableCards() {
        UnoPlayer mainPlayer = gameState.getMainPlayer();
        UnoPlayer currentPlayer = gameState.getCurrentPlayer();
        var playableCards = mainPlayer.getPlayableCards(gameState);
        var cardBtns = playerCardsBox.getChildren();

        if (currentPlayer.equals(mainPlayer)) {
            for (UnoCard card : playableCards) {
                for (var button : cardBtns) {
                    if (card.equals(button.getUserData())) {
                        String style = String.format("-fx-effect: dropshadow(three-pass-box, %s, 20, 0.5, 0, 0);", card.getSuit().getColor());
                        button.setStyle(style);                    }
                }
            }
        }
    }

    public void setOpponents() {
        var opponentPlayers = gameState.getPlayers().stream()
                .filter((player) -> !(player.equals(gameState.getMainPlayer())))
                .toList();

        opponentPlayerBox.getChildren().clear();
        for (UnoPlayer player : opponentPlayers) {
            VBox playerBox = new VBox();
            playerBox.setAlignment(Pos.CENTER);

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

            playerBox.setUserData(player);
            playerBox.getChildren().addAll(cardNumberLbl, playerImageLbl, playerNameLbl);
            opponentPlayerBox.getChildren().add(playerBox);
        }
    }

    public void highlightPlayers() {
        UnoPlayer currentPlayer = gameState.getCurrentPlayer();
        UnoPlayer nextPlayer = gameManager.getNextPlayer();
        UnoPlayer mainPlayer = gameState.getMainPlayer();

        if (currentPlayer.equals(mainPlayer)) {
            playerBox.setStyle("-fx-effect: dropshadow(three-pass-box, yellow, 30, 0.6, 0, 0);");
        } else if (nextPlayer.equals(mainPlayer)) {
            playerBox.setStyle("-fx-effect: dropshadow(three-pass-box, lightskyblue, 10, 0.5, 0, 0);");
        } else {
            playerBox.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0.5, 0, 0);");
        }

        for (var box : opponentPlayerBox.getChildren()) {

            if (box.getUserData().equals(currentPlayer)) {
                box.setStyle("-fx-effect: dropshadow(three-pass-box, yellow, 30, 0.6, 0, 0);" +
                        "-fx-scale-x: 1.2;" +
                        "-fx-scale-y: 1.2;");
            } else if (box.getUserData().equals(nextPlayer)) {
                box.setStyle("-fx-effect: dropshadow(three-pass-box, lightskyblue, 10, 0.5, 0, 0);");
            } else {
                box.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0.3, 0, 0);");
            }
        }
    }

    public void setSayUnoBtnHandler() {
        playClick2();
        gameState.getMainPlayer().sayUno(true);
        unoBtn.setVisible(false);
    }

    public void setDiscardPileImage() {
        UnoCardImageManager imageManager = gameState.getCardImageManager();
        UnoCard lastPlayedCard = gameState.getLastPlayedCard();

        Image image = imageManager.getImage(lastPlayedCard);
        ImageView imageView = new ImageView(image);

        imageView.setFitHeight(100);
        imageView.setFitWidth(100);

        discardPileBtn.setGraphic(imageView);
    }

    public void hideMenu() {
        Platform.runLater(new Runnable() {
            public void run() {
                if (menuBtnBox.getScene() != null) {
                    menuBtnBox.getScene().setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            if (menuBtnBox.isVisible() && !menuBtnBox.contains(mouseEvent.getSceneX(), mouseEvent.getSceneY())) {
                                menuBtnBox.setVisible(false);
                            }
                        }
                    });
                }
            }
        });
    }

    public void setPlayer() {
        UnoPlayer mainPlayer = gameState.getMainPlayer();
        Image playerImage = gameState.getPlayerImageManager().getImage(mainPlayer.getImage());
        ImageView playerImageView = new ImageView(playerImage);

        playerImageView.setFitHeight(60);
        playerImageView.setFitWidth(60);

        playerImageLbl.setUserData(mainPlayer);
        playerImageLbl.setGraphic(playerImageView);
        playerNameLbl.setText(mainPlayer.getName());
        playerCardNumberLbl.setText(String.valueOf(mainPlayer.getPlayerHand().size()));
    }

    public void setPlayerCardImages() {
        UnoPlayer player = gameState.getMainPlayer();
        int numberOfCards = player.getPlayerHand().size();
        playerCardsBox.getChildren().clear();
        for (int i = 0; i < numberOfCards; i++) {
            Button cardPlaceholder = new Button("");
            UnoCardImageManager imageManager = gameState.getCardImageManager();
            UnoCard card = player.getPlayerHand().get(i);

            Image cardImage = imageManager.getImage(card);
            ImageView imageView = new ImageView(cardImage);

            imageView.setFitHeight(60);
            imageView.setFitWidth(60);

            cardPlaceholder.setGraphic(imageView);
            cardPlaceholder.setUserData(card);
            playerCardsBox.getChildren().add(cardPlaceholder);
        }
    }

    public void toggleMenu() {
        menuBtnBox.setVisible(!menuBtnBox.isVisible());
    }

    public void announceWinner(UnoPlayer player) {
        Platform.runLater(new Runnable() {
            public void run() {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, player.getName() + " has won!\n\nClick 'Ok' to begin a New Game.", ButtonType.OK);
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    gameManager.resetGame();
                }
            }
        });
    }

    public void setCallUnoHandler() {
        for (var opponent : opponentPlayerBox.getChildren()) {
            UnoPlayer player = (UnoPlayer) opponent.getUserData();
            opponent.setOnMouseClicked(e -> gameManager.callUno(player));
        }
    }

    public void updateGameAreaView() {
        Platform.runLater(this::updateGameArea);
    }

    public void showSuitColorSelection() {
        suitColorSelectionBox.setVisible(true);
        suitColorSelectionBox.toFront();
    }

    public void setSuitColorSelectionHandler() {
        redCardLbl.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                playClick2();
                gameState.setCurrentSuit(UnoSuit.RED);
                suitColorSelectionBox.setVisible(false);
                gameManager.startAIRunning();
                updateGameArea();
            }
        });

        greenCardLbl.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                playClick2();
                gameState.setCurrentSuit(UnoSuit.GREEN);
                suitColorSelectionBox.setVisible(false);
                gameManager.startAIRunning();
                updateGameArea();
            }
        });

        blueCardLbl.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                playClick2();
                gameState.setCurrentSuit(UnoSuit.BLUE);
                suitColorSelectionBox.setVisible(false);
                gameManager.startAIRunning();
                updateGameArea();
            }
        });

        yellowCardLbl.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                playClick2();
                gameState.setCurrentSuit(UnoSuit.YELLOW);
                suitColorSelectionBox.setVisible(false);
                gameManager.startAIRunning();
                updateGameArea();
            }
        });
    }

    public void setPlayerCardHandler() {
        var playerCardLbls = playerCardsBox.getChildren();
        for (var label : playerCardLbls) {

            label.setOnMouseClicked(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent mouseEvent) {
                    UnoCard card = (UnoCard) label.getUserData();
                    UnoPlayer mainPlayer = gameState.getMainPlayer();
                    UnoPlayer currentPlayer = gameState.getCurrentPlayer();
                    int currentPlayerIndex = gameState.getPlayers().indexOf(currentPlayer);
                    int cardIndex = currentPlayer.getPlayerHand().indexOf(card);

                    if (currentPlayer.equals(mainPlayer)) {

                        boolean validCard = gameManager.playCard(currentPlayerIndex, cardIndex);
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
                                gameManager.startAIRunning();
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
