package view;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ControlsView {
    private VBox controlsBox;

    public ControlsView() {
        controlsBox = new VBox(10);
        controlsBox.setPadding(new Insets(5));
        controlsBox.setAlignment(Pos.CENTER);

        Label playerFrame = new Label("Player Portrait");
        Label playerName = new Label("Player name");
        playerFrame.setStyle("-fx-text-fill: white;");
        playerName.setStyle("-fx-text-fill: white;");

        HBox controlButtonsBox = new HBox(10);
        controlButtonsBox.setAlignment(Pos.CENTER);

        Button playCardButton = new Button("Play Card");
        Button drawCardButton = new Button("Draw Card");
        Button passTurnButton = new Button("Pass Turn");

        controlButtonsBox.getChildren().addAll(playCardButton, drawCardButton, passTurnButton);

        controlsBox.getChildren().addAll(playerFrame, playerName, controlButtonsBox);
    }

    public VBox getView() {
        return controlsBox;
    }
}
