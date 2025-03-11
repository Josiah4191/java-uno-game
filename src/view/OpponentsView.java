package view;

import games.cardgames.unogame.PlayDirection;
import games.cardgames.unogame.UnoGameState;
import games.players.cardplayers.unoplayers.UnoPlayer;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.List;

public class OpponentsView {
    private VBox opponentsBox;
    private HBox playersBox = new HBox(10);
    private ArrayList<Label> opponents = new ArrayList<>(List.of());
    private Label directionPlaceholder = new Label("Direction Arrow Placeholder");

    public OpponentsView() {
        opponentsBox = new VBox(5);
        opponentsBox.setAlignment(Pos.CENTER);
        playersBox.setAlignment(Pos.CENTER);
        directionPlaceholder.setStyle("-fx-text-fill: white;");
        opponentsBox.getChildren().addAll(playersBox, directionPlaceholder);
    }

    public void setOpponentNames(UnoGameState gameState) {
        var mainPlayer = gameState.getPlayer(0);
        var players = gameState.getPlayers()
                .stream()
                .filter(e -> e != mainPlayer)
                .toList();

        players.forEach(e -> System.out.println(e.getName()));

        for (UnoPlayer player: players) {
            Label label = new Label(player.getName());
            label.setStyle("-fx-text-fill: white;");
            opponents.add(label);
        }
        playersBox.getChildren().addAll(opponents);
    }

    public void setPlayDirection(UnoGameState gameState) {
        PlayDirection direction = gameState.getDirection();
        directionPlaceholder.setText(direction.name());
    }

    public VBox getView() {
        return opponentsBox;
    }
}
