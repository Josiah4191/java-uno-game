package view.controllers;

import games.cardgames.cards.unocards.UnoCard;
import games.cardgames.cards.unocards.UnoCardClassicImages;
import games.cardgames.cards.unocards.UnoCardImageManager;
import games.cardgames.unogame.UnoGameManager;
import games.cardgames.unogame.UnoGameState;
import games.players.cardplayers.unoplayers.UnoPlayer;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import view.views.GameAreaView;

public class GameAreaController {

    private GameAreaView gameAreaView = new GameAreaView();
    private UnoGameManager gameManager;
    private UnoGameState gameState;

    public GameAreaController(UnoGameManager gameManager, UnoGameState gameState) {
        this.gameManager = gameManager;
        this.gameState = gameState;
    }

    // i need to reevaluate what sort of information the game manager is returning about players, and what
    // sort of parameters that their methods require to get information about them. like, should we really
    // need to work with player indexes, card indexes, or pass those indexes to methods?

    public void initialize() {
        setDiscardPileImage();
        setDrawPileImage();
        setPlayerCardImage();
        setCurrentPlayer();
        clearLogo();
        playCard();
    }

    public void setDrawPileImage() {
        Label drawPileLbl = gameAreaView.getDrawPile();

        Image image = gameManager.getImage(UnoCardClassicImages.DECK);
        ImageView imageView = new ImageView(image);

        imageView.setFitHeight(100);
        imageView.setFitWidth(100);

        drawPileLbl.setGraphic(imageView);
    }

    public void setLogoImage() {
        Label logoLbl = gameAreaView.getCenterLogo();
        Image image = gameManager.getImage(UnoCardClassicImages.LOGO);
        ImageView imageView = new ImageView(image);

        imageView.setFitHeight(200);
        imageView.setFitWidth(240);

        logoLbl.setGraphic(imageView);
    }

    public void clearLogo() {
        gameAreaView.getCenterLogo().setGraphic(null);
    }

    public void setDiscardPileImage() {
        Label discardPileLbl = gameAreaView.getDiscardPile();
        UnoCard lastPlayedCard = gameManager.getLastPlayedCard();

        Image image = gameManager.getImage(lastPlayedCard);
        ImageView imageView = new ImageView(image);

        imageView.setFitHeight(100);
        imageView.setFitWidth(100);

        discardPileLbl.setGraphic(imageView);
    }

    public void setPlayerCardImage() {
        UnoPlayer player = gameManager.getMainPlayer();
        int numberOfCards = player.getPlayerHand().size();
        gameAreaView.getPlayerCardsBox().getChildren().clear();
        for (int i = 0; i < numberOfCards; i++) {
            Label cardPlaceholder = new Label("");
            UnoCard card = player.getPlayerHand().get(i);

            Image cardImage = gameManager.getImage(card);
            ImageView imageView = new ImageView(cardImage);

            imageView.setFitHeight(60);
            imageView.setFitWidth(60);

            cardPlaceholder.setGraphic(imageView);
            cardPlaceholder.setUserData(card);
            gameAreaView.getPlayerCardsBox().getChildren().add(cardPlaceholder);
        }
    }

    public void playCard() {
        var labels = getGameAreaView().getPlayerCardsBox().getChildren();
        for (var label: labels) {

            label.setOnMouseClicked(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent mouseEvent) {
                    UnoCard card = (UnoCard)label.getUserData();
                    var player = gameManager.getCurrentPlayer();
                    if (player.equals(gameManager.getMainPlayer())) {
                        gameManager.playCard(player, card);
                        gameManager.moveToNextPlayer();
                        setCurrentPlayer();
                        setDiscardPileImage();
                        setPlayerCardImage();
                    }
                }
            });
        }
    }

    public void setCurrentPlayer() {
        UnoPlayer player = gameManager.getCurrentPlayer();
        gameAreaView.getCurrentPlayerLabel().setText(player.getName() + "'s turn");
    }

    public GameAreaView getGameAreaView() {
        return gameAreaView;
    }

}
