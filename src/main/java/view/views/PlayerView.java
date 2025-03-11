package view.views;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class PlayerView {
    private VBox playerBox;
    private Label playerFrame = new Label("Player Portrait");
    private Label playerName = new Label("Player name");
    private HBox controlButtonsBox = new HBox(10);
    private Button playCardButton = new Button("Play Card");
    private Button drawCardButton = new Button("Draw Card");
    private Button passTurnButton = new Button("Pass Turn");

    public PlayerView() {
        playerBox = new VBox(10);
        playerBox.setPadding(new Insets(5));
        playerBox.setAlignment(Pos.CENTER);

        playerFrame.setStyle("-fx-text-fill: white;");
        playerName.setStyle("-fx-text-fill: white;");

        controlButtonsBox.setAlignment(Pos.CENTER);
        controlButtonsBox.getChildren().addAll(playCardButton, drawCardButton, passTurnButton);
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

    public Button getPlayCardButton() {
        return playCardButton;
    }

    public Button getDrawCardButton() {
        return drawCardButton;
    }

    public Button getPassTurnButton() {
        return passTurnButton;
    }

    public VBox getPlayerView() {
        return playerBox;
    }


}
