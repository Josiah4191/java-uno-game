package view;

import games.cardgames.cards.unocards.UnoCard;
import games.cardgames.unogame.UnoGameState;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;


public class GameAreaView {
    private StackPane gameArea;

    VBox centerBox = new VBox(10);
    Label centerLogo = new Label("Game Logo Placeholder");
    HBox pilesBox = new HBox(20);
    Label drawPile = new Label("Draw Pile");
    Label discardPile = new Label("Discard Pile");
    Label roundsLabel = new Label("Current Player's Turn");
    HBox playerCardsBox = new HBox(10);



    public GameAreaView() {
        gameArea = new StackPane();
        gameArea.setStyle("-fx-background-color: darkgreen; -fx-padding: 10;");

        centerBox.setAlignment(Pos.CENTER);
        centerLogo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: white;");
        pilesBox.setAlignment(Pos.CENTER);
        drawPile.setStyle("-fx-border-color: white; -fx-padding: 40px; -fx-text-fill: white;");
        discardPile.setStyle("-fx-border-color: white; -fx-padding: 40px; -fx-text-fill: white;");
        pilesBox.getChildren().addAll(drawPile, discardPile);
        roundsLabel.setStyle("-fx-text-fill: white;");
        playerCardsBox.setAlignment(Pos.CENTER);
        centerBox.getChildren().addAll(centerLogo, pilesBox, roundsLabel, playerCardsBox);
        gameArea.getChildren().add(centerBox);
    }

    public void displayPlayerCardBox(UnoGameState gameState) {
        var players = gameState.getPlayers();
        var player = players.get(0);
        int numberOfCards = player.getPlayerHand().size();

        for (int i = 1; i <= numberOfCards; i++) {
            UnoCard card = player.getPlayerHand().get(i);
            var imageManager = gameState.getCardImageManager();

            String imagePath = imageManager.getImage(card);
            Image image = new Image(imagePath);
            ImageView imageView = new ImageView(image);

            Label cardPlaceholder = new Label();
            cardPlaceholder.setStyle("-fx-border-color: white; -fx-padding: 20px; -fx-text-fill: white;");
            playerCardsBox.getChildren().add(cardPlaceholder);
        }
    }

    public StackPane getView() {
        return gameArea;
    }

}
