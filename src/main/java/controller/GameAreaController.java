package controller;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import model.cardgames.cards.unocards.UnoCard;
import model.cardgames.unogame.PlayDirection;
import model.images.cardimages.UnoCardClassicImages;
import model.images.cardimages.UnoCardImageManager;
import model.cardgames.unogame.UnoGameManager;
import model.cardgames.unogame.UnoGameState;
import model.players.cardplayers.unoplayers.UnoPlayer;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import view.GameAreaView;

public class GameAreaController implements GameAreaListener {

    private GameAreaView gameAreaView;
    private UnoGameManager gameManager;
    private UnoGameState gameState;

    public GameAreaController(UnoGameManager gameManager, GameAreaView gameAreaView) {
        this.gameManager = gameManager;
        this.gameState = gameManager.getGameState();
        this.gameAreaView = gameAreaView;
        gameManager.setGameAIListener(this);
    }

    public void updateGameArea() {
        setDiscardPileImage();
        setDrawPileImage();
        setPlayerCardImage();
        setPlayerCardHandler();
        setOpponentNames();
        setPlayDirectionLabel();
        setMainPlayer();
        setDrawPileCardHandler();
        highlightPlayableCards();
        highlightCurrentPlayer();
    }

    public void setDrawPileImage() {
        Label drawPileLbl = gameAreaView.getDrawPileLbl();
        UnoCardImageManager imageManager = gameState.getCardImageManager();

        Image image = imageManager.getImage(UnoCardClassicImages.DECK);
        ImageView imageView = new ImageView(image);

        imageView.setFitHeight(100);
        imageView.setFitWidth(100);

        drawPileLbl.setGraphic(imageView);
    }

    public void setDiscardPileImage() {
        Label discardPileLbl = gameAreaView.getDiscardPileLbl();
        UnoCardImageManager imageManager = gameState.getCardImageManager();
        UnoCard lastPlayedCard = gameState.getLastPlayedCard();

        Image image = imageManager.getImage(lastPlayedCard);
        ImageView imageView = new ImageView(image);

        imageView.setFitHeight(100);
        imageView.setFitWidth(100);

        discardPileLbl.setGraphic(imageView);
    }

    public void setPlayerCardImage() {
        UnoPlayer player = gameState.getMainPlayer();
        int numberOfCards = player.getPlayerHand().size();
        gameAreaView.getPlayerCardsBox().getChildren().clear();
        for (int i = 0; i < numberOfCards; i++) {
            Label cardPlaceholder = new Label("");
            UnoCardImageManager imageManager = gameState.getCardImageManager();
            UnoCard card = player.getPlayerHand().get(i);

            Image cardImage = imageManager.getImage(card);
            ImageView imageView = new ImageView(cardImage);

            imageView.setFitHeight(60);
            imageView.setFitWidth(60);

            cardPlaceholder.setGraphic(imageView);
            cardPlaceholder.setUserData(card);
            gameAreaView.getPlayerCardsBox().getChildren().add(cardPlaceholder);
        }
    }

    public void setDrawPileCardHandler() {
        Label drawPileLbl = getGameAreaView().getDrawPileLbl();
        UnoPlayer currentPlayer = gameState.getCurrentPlayer();
        int currentPlayerIndex = gameState.getCurrentPlayerIndex();
        currentPlayer.setPassTurn(true);

        drawPileLbl.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {

                if (currentPlayer.isPassTurn()) {
                    boolean playable = gameManager.playerDrawCard(currentPlayerIndex);
                    updateGameArea();
                    currentPlayer.setPassTurn(false);
                } else {
                    gameManager.moveToNextPlayer();
                    updateGameArea();
                    gameManager.runAITurn();
                }
            }
        });
    }

    public void updateGameAreaView() {
        Platform.runLater(this::updateGameArea);
    }

    public void setPlayerCardHandler() {
        var playerCardLbls = getGameAreaView().getPlayerCardsBox().getChildren();
        for (var label : playerCardLbls) {

            label.setOnMouseClicked(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent mouseEvent) {
                    UnoCard card = (UnoCard) label.getUserData();
                    UnoPlayer mainPlayer = gameState.getMainPlayer();
                    UnoPlayer currentPlayer = gameState.getCurrentPlayer();
                    int currentPlayerIndex = gameState.getPlayers().indexOf(currentPlayer);
                    int cardIndex = currentPlayer.getPlayerHand().indexOf(card);

                    if (currentPlayer.equals(mainPlayer)) {

                        boolean valid = gameManager.playCard(currentPlayerIndex, cardIndex);

                        if (valid) {
                            gameManager.runAITurn();
                        }
                        /*
                        if (valid) {
                            updateGameAreaView();
                            gameManager.runAITurn();
                        } else {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Invalid Card", ButtonType.OK);
                            alert.show();
                        }
                         */
                    }
                }
            });
        }
    }

    public void highlightPlayableCards() {
        UnoPlayer mainPlayer = gameState.getMainPlayer();
        UnoPlayer currentPlayer = gameState.getCurrentPlayer();
        var playableCards = mainPlayer.getPlayableCards(gameState);
        var cardLabels = gameAreaView.getPlayerCardsBox().getChildren();

        if (currentPlayer.equals(mainPlayer)) {
            for (UnoCard card : playableCards) {
                for (var label : cardLabels) {
                    if (card.equals(label.getUserData())) {
                        label.setStyle("-fx-border-color: yellow; -fx-border-width: 3px; -fx-border-radius: 5px;");
                    }
                }
            }
        }
    }

    public void highlightCurrentPlayer() {
        UnoPlayer currentPlayer = gameState.getCurrentPlayer();
        UnoPlayer mainPlayer = gameState.getMainPlayer();

        if (currentPlayer.equals(mainPlayer)) {
            gameAreaView.getMainPlayerNameLbl().setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: yellow;");
        } else {
            gameAreaView.getMainPlayerNameLbl().setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: white;");
        }

        if (!(currentPlayer.equals(mainPlayer))) {
            for (var label : gameAreaView.getOpponentsPlayerBox().getChildren()) {
                if (label.getUserData().equals(currentPlayer)) {
                    label.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: yellow;");
                } else {
                    label.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: white;");
                }
            }
        }
    }

    public void setMainPlayer() {
        UnoPlayer mainPlayer = gameState.getMainPlayer();
        Label mainPlayerNameLbl = gameAreaView.getMainPlayerNameLbl();
        mainPlayerNameLbl.setText(mainPlayer.getName());
        mainPlayerNameLbl.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: white;");
    }

    public void setOpponentNames() {
        var players = gameState.getPlayers();
        gameAreaView.getOpponentsPlayerBox().getChildren().clear();

        for (var player : players) {
            if (!(player.equals(gameState.getMainPlayer()))) {
                Label opponentNameLbl = new Label(player.getName() + " : " + player.getTotalCardsRemaining());
                opponentNameLbl.setUserData(player);
                opponentNameLbl.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: white;");
                gameAreaView.getOpponentsPlayerBox().getChildren().add(opponentNameLbl);
            }
        }
    }

    private void setPlayDirectionLabel() {
        PlayDirection direction = gameState.getDirection();
        gameAreaView.getPlayDirectionLbl().setText(direction.toString());
    }

    public GameAreaView getGameAreaView() {
        return gameAreaView;
    }

}
