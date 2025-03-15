package view;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class PlayerView {
    private VBox playerBox;
    private Label playerFrame = new Label("Player Portrait");
    private Label playerName = new Label("Player name");
    private HBox controlButtonsBox = new HBox(10);

    public PlayerView() {
        playerBox = new VBox(10);
        playerBox.setPadding(new Insets(5));
        playerBox.setAlignment(Pos.CENTER);
        playerBox.setStyle("-fx-background-color: #1e1e1e;");

        playerFrame.setStyle("-fx-text-fill: white;");
        playerName.setStyle("-fx-text-fill: white;");
        playerBox.getChildren().addAll(playerFrame, playerName, controlButtonsBox);
    }

    public VBox getPlayerBox() {
        return playerBox;
    }

    public Label getPlayerFrame() {
        return playerFrame;
    }

    public Label getPlayerName() {
        return playerName;
    }

    public HBox getControlButtonsBox() {
        return controlButtonsBox;
    }

    public VBox getPlayerView() {
        return playerBox;
    }

}
