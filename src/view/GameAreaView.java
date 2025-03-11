package view;

import games.cardgames.cards.unocards.UnoCard;
import games.cardgames.unogame.UnoGameState;
import games.players.cardplayers.unoplayers.UnoPlayer;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

import java.io.File;
import java.net.URI;


public class GameAreaView {
    private StackPane gameArea;
    private VBox centerBox = new VBox(10);
    private Label centerLogo = new Label("Game Logo Placeholder");
    private HBox pilesBox = new HBox(20);
    private Label drawPile = new Label("");
    private Label discardPile = new Label("");
    private Label currentPlayerLabel = new Label("Current Player's Turn");
    private HBox playerCardsBox = new HBox(10);

    public GameAreaView() {
        gameArea = new StackPane();
        gameArea.setStyle("-fx-background-color: darkgreen; -fx-padding: 10;");
        centerBox.setAlignment(Pos.CENTER);
        centerLogo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: white;");
        pilesBox.setAlignment(Pos.CENTER);
        pilesBox.getChildren().addAll(drawPile, discardPile);
        currentPlayerLabel.setStyle("-fx-text-fill: white;");
        playerCardsBox.setAlignment(Pos.CENTER);
        centerBox.getChildren().addAll(centerLogo, pilesBox, currentPlayerLabel, playerCardsBox);
        gameArea.getChildren().add(centerBox);
    }

    public void setCurrentPlayer(UnoGameState gameState) {
        int currentPlayerIndex = gameState.getCurrentPlayerIndex();
        UnoPlayer player = gameState.getPlayer(currentPlayerIndex);
        currentPlayerLabel.setText(player.getName() + "'s turn");
    }

    public void addCardToBox(UnoGameState gameState, UnoCard card) {
        var players = gameState.getPlayers();
        var player = players.get(0);
        int numberOfCards = player.getPlayerHand().size();

        Label cardPlaceholder = new Label("");
        var imageManager = gameState.getCardImageManager();

        String imagePath = imageManager.getImage(card);
        System.out.println("Image path: " + imagePath);

        File imageFile = new File(imagePath);
        URI imageURI = imageFile.toURI();

        Image image = new Image(imageURI.toString());
        ImageView imageView = new ImageView(image);

        imageView.setFitHeight(60);
        imageView.setFitWidth(60);

        cardPlaceholder.setGraphic(imageView);
        cardPlaceholder.setUserData(card);
        playerCardsBox.getChildren().add(imageView);

    }

    public void displayPlayerCardBox(UnoGameState gameState) {
        var players = gameState.getPlayers();
        var player = players.get(0);
        int numberOfCards = player.getPlayerHand().size();

        for (int i = 0; i < numberOfCards; i++) {
            Label cardPlaceholder = new Label("");
            UnoCard card = player.getPlayerHand().get(i);
            var imageManager = gameState.getCardImageManager();

            String imagePath = imageManager.getImage(card);
            System.out.println("Image path: " + imagePath);

            File imageFile = new File(imagePath);
            URI imageURI = imageFile.toURI();

            Image image = new Image(imageURI.toString());
            ImageView imageView = new ImageView(image);

            imageView.setFitHeight(60);
            imageView.setFitWidth(60);

            cardPlaceholder.setGraphic(imageView);
            cardPlaceholder.setUserData(card);
            playerCardsBox.getChildren().add(cardPlaceholder);
        }
    }

    public void displayDrawPileImage(UnoGameState gameState) {
        var imageManager = gameState.getCardImageManager();

        String imagePath = imageManager.getImage("Deck");
        System.out.println("Image path: " + imagePath);

        File imageFile = new File(imagePath);
        URI imageURI = imageFile.toURI();

        Image image = new Image(imageURI.toString());
        ImageView imageView = new ImageView(image);

        imageView.setFitHeight(100);
        imageView.setFitWidth(100);

        drawPile.setGraphic(imageView);
    }

    public void displayDiscardPileImage(UnoGameState gameState) {
        UnoCard card = gameState.getLastPlayedCard();
        var imageManager = gameState.getCardImageManager();

        String imagePath = imageManager.getImage(card);
        System.out.println("Image path: " + imagePath);

        File imageFile = new File(imagePath);
        URI imageURI = imageFile.toURI();

        Image image = new Image(imageURI.toString());
        ImageView imageView = new ImageView(image);

        imageView.setFitHeight(100);
        imageView.setFitWidth(100);

        discardPile.setGraphic(imageView);
    }

    public StackPane getGameArea() {
        return gameArea;
    }

    public VBox getCenterBox() {
        return centerBox;
    }

    public Label getCenterLogo() {
        return centerLogo;
    }

    public HBox getPilesBox() {
        return pilesBox;
    }

    public Label getDrawPile() {
        return drawPile;
    }

    public Label getDiscardPile() {
        return discardPile;
    }

    public Label getCurrentPlayerLabel() {
        return currentPlayerLabel;
    }

    public HBox getPlayerCardsBox() {
        return playerCardsBox;
    }

    public StackPane getView() {
        return gameArea;
    }

}
