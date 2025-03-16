package controller;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import model.cardgames.cards.unocards.UnoCard;
import model.cardgames.cards.unocards.UnoValue;
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

public class GameAreaController implements GameAIListener {

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
        setCurrentPlayer();
        setPlayerCardHandler();
        setOpponentNames();
        setPlayDirectionLabel();
        setMainPlayer();
        setDrawPileCardHandler();
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

    public void setCurrentPlayer() {
        UnoPlayer player = gameState.getCurrentPlayer();
        gameAreaView.getCurrentPlayerLbl().setText(player.getName() + "'s turn");
        gameAreaView.getCurrentPlayerLbl().setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: white;");
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

    public void onAIMove() {
        Platform.runLater(this::updateGameArea);
    }

    public void setPlayerCardHandler() {
        var playerCardLbls = getGameAreaView().getPlayerCardsBox().getChildren();
        for (var label: playerCardLbls) {

            label.setOnMouseClicked(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent mouseEvent) {
                    UnoCard card = (UnoCard)label.getUserData();
                    var currentPlayer = gameState.getCurrentPlayer();
                    int playerIndex = gameState.getPlayers().indexOf(currentPlayer);
                    int cardIndex = currentPlayer.getPlayerHand().indexOf(card);

                    if (currentPlayer.equals(gameState.getMainPlayer())) {

                        boolean valid = gameManager.playCard(playerIndex, cardIndex);

                        if (valid) {
                            if (card.getValue() == UnoValue.SKIP) {
                                gameManager.skipNextPlayer();
                            } else {
                                gameManager.moveToNextPlayer();
                            }
                            updateGameArea();
                            gameManager.runAITurn();
                        } else {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Invalid Card", ButtonType.OK);
                            alert.show();
                        }
                    }
                }
            });
        }
    }


    public void setMainPlayer() {
        UnoPlayer player = gameState.getMainPlayer();
        Label playerNameLbl = new Label(player.getName());
        playerNameLbl.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: white;");
        gameAreaView.getMainPlayerBox().getChildren().clear();
        gameAreaView.getMainPlayerBox().getChildren().add(playerNameLbl);
    }

    public void setOpponentNames() {
        var players = gameState.getPlayers();
        gameAreaView.getOpponentsPlayerBox().getChildren().clear();

        for (var player: players) {
            if (!(player.equals(gameState.getMainPlayer()))) {
                Label opponentNameLbl = new Label(player.getName() + " : " + player.getTotalCardsRemaining());
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
