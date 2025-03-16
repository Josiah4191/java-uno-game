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
    private Label drawPileLbl = new Label("");
    private Label discardPileLbl = new Label("");
    private HBox playerCardsBox = new HBox(10);

    private HBox mainPlayerBox = new HBox();
    private Label mainPlayerNameLbl = new Label();
    private HBox opponentsPlayerBox = new HBox(10);
    private Label playDirectionLbl = new Label("");

    public GameAreaView() {
        gameArea = new StackPane();
        gameArea.setStyle("-fx-background-color: darkgreen; -fx-padding: 10;");
        centerBox.setAlignment(Pos.CENTER);
        playDirectionLbl.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: white;");
        opponentsPlayerBox.setAlignment(Pos.CENTER);
        mainPlayerBox.setAlignment(Pos.CENTER);
        mainPlayerBox.getChildren().add(mainPlayerNameLbl);
        centerLogo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: white;");
        pilesBox.setAlignment(Pos.CENTER);
        pilesBox.getChildren().addAll(drawPileLbl, discardPileLbl);
        playerCardsBox.setAlignment(Pos.CENTER);
        centerBox.getChildren().addAll(playDirectionLbl, opponentsPlayerBox, centerLogo, pilesBox, playerCardsBox, mainPlayerBox);
        gameArea.getChildren().add(centerBox);
    }

    public Label getDrawPileLbl() {
        return drawPileLbl;
    }

    public Label getMainPlayerNameLbl() {
        return mainPlayerNameLbl;
    }

    public Label getDiscardPileLbl() {
        return discardPileLbl;
    }

    public HBox getPlayerCardsBox() {
        return playerCardsBox;
    }

    public StackPane getView() {
        return gameArea;
    }

    public HBox getOpponentsPlayerBox() {
        return opponentsPlayerBox;
    }

    public Label getPlayDirectionLbl() {
        return playDirectionLbl;
    }

    public HBox getMainPlayerBox() {
        return mainPlayerBox;
    }

}
