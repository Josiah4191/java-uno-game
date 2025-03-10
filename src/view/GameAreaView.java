package view;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;


public class GameAreaView {
    private StackPane gameArea;

    public GameAreaView() {
        gameArea = new StackPane();
        gameArea.setStyle("-fx-background-color: darkgreen; -fx-padding: 10;");

        VBox centerBox = new VBox(10);
        centerBox.setAlignment(Pos.CENTER);

        Label centerLogo = new Label("Game Logo Placeholder");
        centerLogo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: white;");

        HBox pilesBox = new HBox(20);
        pilesBox.setAlignment(Pos.CENTER);
        Label drawPile = new Label("Draw Pile");
        drawPile.setStyle("-fx-border-color: white; -fx-padding: 40px; -fx-text-fill: white;");
        Label discardPile = new Label("Discard Pile");
        discardPile.setStyle("-fx-border-color: white; -fx-padding: 40px; -fx-text-fill: white;");
        pilesBox.getChildren().addAll(drawPile, discardPile);

        Label roundsLabel = new Label("Rounds Remaining: 5");
        roundsLabel.setStyle("-fx-text-fill: white;");

        HBox playerCardsBox = new HBox(10);
        playerCardsBox.setAlignment(Pos.CENTER);
        for (int i = 1; i <= 5; i++) {
            Label cardPlaceholder = new Label("Card " + i);
            cardPlaceholder.setStyle("-fx-border-color: white; -fx-padding: 20px; -fx-text-fill: white;");
            playerCardsBox.getChildren().add(cardPlaceholder);
        }

        centerBox.getChildren().addAll(centerLogo, pilesBox, roundsLabel, playerCardsBox);
        gameArea.getChildren().add(centerBox);
    }

    public StackPane getView() {
        return gameArea;
    }

}
