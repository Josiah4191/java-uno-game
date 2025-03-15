package controller;

import model.cardgames.cards.unocards.UnoCard;
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

public class GameAreaController {

    private GameAreaView gameAreaView;
    private UnoGameManager gameManager;
    private UnoGameState gameState;

    public GameAreaController(UnoGameManager gameManager, GameAreaView gameAreaView) {
        this.gameManager = gameManager;
        this.gameState = gameManager.getGameState();
        this.gameAreaView = gameAreaView;
    }

    // i need to reevaluate what sort of information the game manager is returning about players, and what
    // sort of parameters that their methods require to get information about them. like, should we really
    // need to work with player indexes, card indexes, or pass those indexes to methods?

    public void initialize() {
        setDiscardPileImage();
        setDrawPileImage();
        setPlayerCardImage();
        setCurrentPlayer();
        playCard();
    }

    public void setDrawPileImage() {
        Label drawPileLbl = gameAreaView.getDrawPile();
        UnoCardImageManager imageManager = gameState.getCardImageManager();

        Image image = imageManager.getImage(UnoCardClassicImages.DECK);
        ImageView imageView = new ImageView(image);

        imageView.setFitHeight(100);
        imageView.setFitWidth(100);

        drawPileLbl.setGraphic(imageView);
    }

    public void setLogoImage() {
        Label logoLbl = gameAreaView.getCenterLogo();
        UnoCardImageManager imageManager = gameState.getCardImageManager();
        Image image = imageManager.getImage(UnoCardClassicImages.LOGO);
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

    public void playCard() {
        var labels = getGameAreaView().getPlayerCardsBox().getChildren();
        for (var label: labels) {

            label.setOnMouseClicked(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent mouseEvent) {
                    UnoCard card = (UnoCard)label.getUserData();
                    var player = gameState.getCurrentPlayer();
                    if (player.equals(gameState.getMainPlayer())) {
                        //gameManager.playCard(player, card);
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
        UnoPlayer player = gameState.getCurrentPlayer();
        gameAreaView.getCurrentPlayerLabel().setText(player.getName() + "'s turn");
    }

    public GameAreaView getGameAreaView() {
        return gameAreaView;
    }

}
