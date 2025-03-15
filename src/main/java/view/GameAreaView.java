package view;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

public class GameAreaView {
    private StackPane gameArea;
    private VBox centerBox = new VBox(10);
    private Label centerLogo = new Label();
    private HBox pilesBox = new HBox(20);
    private Label drawPile = new Label("");
    private Label discardPile = new Label("");
    private Label currentPlayerLabel = new Label("");
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
