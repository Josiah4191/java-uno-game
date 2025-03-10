package view;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

public class OpponentsView {
    private VBox opponentsBox;

    public OpponentsView() {
        opponentsBox = new VBox(5);
        opponentsBox.setAlignment(Pos.CENTER);

        HBox playersBox = new HBox(10);
        playersBox.setAlignment(Pos.CENTER);

        Label opp1 = new Label("Opponent 1: 5 cards");
        Label opp2 = new Label("Opponent 2: 4 cards");
        Label opp3 = new Label("Opponent 3: 6 cards");

        opp1.setStyle("-fx-text-fill: white;");
        opp2.setStyle("-fx-text-fill: white;");
        opp3.setStyle("-fx-text-fill: white;");

        playersBox.getChildren().addAll(opp1, opp2, opp3);

        Label directionPlaceholder = new Label("Direction Arrow Placeholder");
        directionPlaceholder.setStyle("-fx-text-fill: white;");

        opponentsBox.getChildren().addAll(playersBox, directionPlaceholder);
    }

    public VBox getView() {
        return opponentsBox;
    }
}
