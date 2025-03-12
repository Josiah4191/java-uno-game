package view.views;

import games.cardgames.unogame.PlayDirection;
import games.cardgames.unogame.UnoGameState;
import games.players.cardplayers.unoplayers.UnoPlayer;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.List;

public class OpponentsView {
    private VBox opponentsBox;
    private HBox playersBox = new HBox(10);
    private Label directionPlaceholder = new Label("Direction Arrow Placeholder");

    public OpponentsView() {
        opponentsBox = new VBox(5);
        opponentsBox.setAlignment(Pos.CENTER);
        opponentsBox.setStyle("-fx-background-color: #1e1e1e;");

        playersBox.setAlignment(Pos.CENTER);

        directionPlaceholder.setStyle("-fx-text-fill: white;");
        opponentsBox.getChildren().addAll(playersBox, directionPlaceholder);
    }

    public VBox getOpponentsBox() {
        return opponentsBox;
    }

    public HBox getPlayersBox() {
        return playersBox;
    }

    public Label getDirectionPlaceholder() {
        return directionPlaceholder;
    }

    public VBox getView() {
        return opponentsBox;
    }
}
