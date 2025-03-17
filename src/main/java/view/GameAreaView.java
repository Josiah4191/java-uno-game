package view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

public class GameAreaView {
    private StackPane gameArea = new StackPane();

    private VBox centerBox = new VBox(10);

    private Label playDirectionLbl = new Label();

    private HBox opponentPlayerBox = new HBox(10);

    private HBox pilesBox = new HBox(20);
    private Label drawPileLbl = new Label();
    private Label discardPileLbl = new Label();

    private HBox playerCardsBox = new HBox(10);

    private VBox mainPlayerBox = new VBox();
    private Label mainPlayerImageLbl = new Label();
    private Label mainPlayerNameLbl = new Label();

    private Button passBtn = new Button("Pass");

    public GameAreaView() {

        centerBox.setAlignment(Pos.CENTER);
        opponentPlayerBox.setAlignment(Pos.CENTER);
        pilesBox.setAlignment(Pos.CENTER);
        playerCardsBox.setAlignment(Pos.CENTER);
        mainPlayerBox.setAlignment(Pos.CENTER);

        gameArea.getChildren().add(centerBox);
        gameArea.setStyle("-fx-background-color: darkgreen; -fx-padding: 10;");

        centerBox.getChildren().addAll(
                playDirectionLbl,
                opponentPlayerBox,
                pilesBox,
                playerCardsBox,
                mainPlayerBox,
                passBtn);

        passBtn.setVisible(false);
        playDirectionLbl.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: white;");
        pilesBox.getChildren().addAll(drawPileLbl, discardPileLbl);
        mainPlayerBox.getChildren().addAll(mainPlayerImageLbl, mainPlayerNameLbl);
        mainPlayerBox.setMaxWidth(Region.USE_PREF_SIZE);
        mainPlayerBox.setMaxHeight(Region.USE_PREF_SIZE);

    }

    public Label getDrawPileLbl() {
        return drawPileLbl;
    }

    public Label getMainPlayerNameLbl() {
        return mainPlayerNameLbl;
    }

    public Label getMainPlayerImageLbl() {
        return mainPlayerImageLbl;
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

    public Label getPlayDirectionLbl() {
        return playDirectionLbl;
    }

    public HBox getOpponentPlayerBox() {
        return opponentPlayerBox;
    }

    public VBox getMainPlayerBox() {
        return mainPlayerBox;
    }

    public Button getPassBtn() {
        return passBtn;
    }
}
