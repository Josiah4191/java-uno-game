package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
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
        setPlayDirectionLabel();
        setMainPlayer();
        setOpponentPlayers();
        highlightCurrentPlayerImage();
        setDrawPileCardHandler();
        highlightPlayableCards();
        setPassBtnHandler();
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
        UnoPlayer mainPlayer = gameState.getMainPlayer();
        UnoPlayer currentPlayer = gameState.getCurrentPlayer();
        int currentPlayerIndex = gameState.getCurrentPlayerIndex();
        currentPlayer.setPassTurn(true);

        drawPileLbl.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {

                if (currentPlayer.equals(mainPlayer)) {
                    if (currentPlayer.isPassTurn()) {
                        showPassBtn();
                        boolean playable = gameManager.playerDrawCard(currentPlayerIndex);

                        setDiscardPileImage();
                        setDrawPileImage();
                        setPlayerCardImage();

                        if (playable) {
                            highlightDrawCard();
                            setDrawCardHandler();
                        }

                        currentPlayer.setPassTurn(false);
                    }
                }
            }
        });
    }

    public void setPassBtnHandler() {
        Button passBtn = gameAreaView.getPassBtn();
        passBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                gameManager.moveToNextPlayer();
                gameManager.runAITurn();
                updateGameArea();
                hidePassBtn();
            }
        });
    }

    public void hidePassBtn() {
        gameAreaView.getPassBtn().setVisible(false);
    }

    public void showPassBtn() {
        gameAreaView.getPassBtn().setVisible(true);
    }

    public void updateGameAreaView() {
        Platform.runLater(this::updateGameArea);
    }

    public void setDrawCardHandler() {
        UnoPlayer mainPlayer = gameState.getMainPlayer();
        UnoCard lastDrawCard = mainPlayer.getLastDrawCard();
        int lastDrawCardIndex = mainPlayer.getPlayerHand().indexOf(lastDrawCard);
        int currentPlayerIndex = gameState.getCurrentPlayerIndex();
        var cardLbls = gameAreaView.getPlayerCardsBox().getChildren();

        for (var label : cardLbls) {
            if (label.getUserData().equals(lastDrawCard)) {

                label.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent event) {
                        boolean valid = gameManager.playCard(currentPlayerIndex, lastDrawCardIndex);

                        if (valid) {
                            gameManager.runAITurn();
                        }
                    }
                });

            }
        }
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
                    }
                }
            });
        }
    }

    public void highlightDrawCard() {
        UnoPlayer mainPlayer = gameState.getMainPlayer();
        UnoPlayer currentPlayer = gameState.getCurrentPlayer();
        UnoCard lastDrawCard = mainPlayer.getLastDrawCard();
        var cardLbls = gameAreaView.getPlayerCardsBox().getChildren();

        if (currentPlayer.equals(mainPlayer)) {

            for (var label : cardLbls) {
                if (lastDrawCard.equals(label.getUserData())) {
                    label.setStyle("-fx-border-color: yellow; -fx-border-width: 3px; -fx-border-radius: 5px;");
                }
            }
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

    public void highlightCurrentPlayerImage() {
        UnoPlayer currentPlayer = gameState.getCurrentPlayer();
        UnoPlayer mainPlayer = gameState.getMainPlayer();

        if (currentPlayer.equals(mainPlayer)) {
            gameAreaView.getMainPlayerBox().setStyle("-fx-border-color: yellow; -fx-border-width: 3px; -fx-border-radius: 5px;");
        } else {
            gameAreaView.getMainPlayerBox().setStyle("-fx-border-color: white; -fx-border-width: 3px; -fx-border-radius: 5px;");
        }

        if (!(currentPlayer.equals(mainPlayer))) {

            for (var box : gameAreaView.getOpponentPlayerBox().getChildren()) {

                    if (box.getUserData().equals(currentPlayer)) {
                        box.setStyle("-fx-border-color: yellow; -fx-border-width: 3px; -fx-border-radius: 5px;");
                    } else {
                        box.setStyle("");
                    }
            }
        }
    }

    public void setMainPlayer() {
        UnoPlayer mainPlayer = gameState.getMainPlayer();
        Label mainPlayerNameLbl = gameAreaView.getMainPlayerNameLbl();
        Label mainPlayerImageLbl = gameAreaView.getMainPlayerImageLbl();

        ImageView playerImageView = new ImageView(mainPlayer.getImage());

        playerImageView.setFitHeight(60);
        playerImageView.setFitWidth(60);

        mainPlayerImageLbl.setUserData(mainPlayer);
        mainPlayerImageLbl.setGraphic(playerImageView);

        mainPlayerNameLbl.setText(mainPlayer.getName() + " | " + mainPlayer.getPlayerHand().size());
        mainPlayerNameLbl.setStyle("-fx-font-size: 15; -fx-font-weight: bold; -fx-text-fill: white;");
    }

    public void setOpponentPlayers() {
        var opponentPlayers = gameState.getPlayers().stream()
                .filter((player) -> !(player.equals(gameState.getMainPlayer())))
                .toList();

        gameAreaView.getOpponentPlayerBox().getChildren().clear();
        for (UnoPlayer player : opponentPlayers) {
            VBox playerBox = new VBox();
            playerBox.setAlignment(Pos.CENTER);

            Label playerImage = new Label();
            Label playerNameLbl = new Label(player.getName() + " | " + player.getPlayerHand().size());
            ImageView playerImageView = new ImageView(player.getImage());

            playerNameLbl.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: white;");

            playerImageView.setFitHeight(60);
            playerImageView.setFitWidth(60);

            playerImage.setGraphic(playerImageView);

            playerBox.setUserData(player);
            playerBox.getChildren().addAll(playerImage, playerNameLbl);
            gameAreaView.getOpponentPlayerBox().getChildren().add(playerBox);
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
