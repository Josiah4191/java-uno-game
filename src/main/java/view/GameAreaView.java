package view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

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
    private Button unoBtn = new Button("UNO");

    private HBox suitColorSelectionBox = new HBox(10);
    private Rectangle redLbl = new Rectangle();
    private Rectangle blueLbl = new Rectangle();
    private Rectangle yellowLbl = new Rectangle();
    private Rectangle greenLbl = new Rectangle();

    private HBox menuBox = new HBox(5);
    private Button menuBtn = new Button("Menu");

    private VBox menuBtnBox = new VBox(5);
    private Button newGameBtn = new Button("New Game");
    private Button saveGameBtn = new Button("Save Game");
    private Button loadGameBtn = new Button("Load Game");

    public GameAreaView() {

        centerBox.setAlignment(Pos.CENTER);
        opponentPlayerBox.setAlignment(Pos.CENTER);
        pilesBox.setAlignment(Pos.CENTER);
        playerCardsBox.setAlignment(Pos.CENTER);
        mainPlayerBox.setAlignment(Pos.CENTER);
        suitColorSelectionBox.setAlignment(Pos.CENTER);

        gameArea.getChildren().add(centerBox);
        gameArea.setStyle("-fx-background-color: darkgreen; -fx-padding: 10;");

        centerBox.getChildren().addAll(
                menuBox,
                menuBtnBox,
                playDirectionLbl,
                opponentPlayerBox,
                pilesBox,
                playerCardsBox,
                mainPlayerBox,
                passBtn,
                unoBtn,
                suitColorSelectionBox);

        passBtn.setVisible(false);
        passBtn.setStyle("-fx-border-color: yellow; -fx-border-width: 3px; -fx-border-radius: 5px;");
        unoBtn.setVisible(false);
        unoBtn.setStyle("-fx-border-color: yellow; -fx-border-width: 3px; -fx-border-radius: 5px;");

        playDirectionLbl.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: white;");
        pilesBox.getChildren().addAll(drawPileLbl, discardPileLbl);
        mainPlayerBox.getChildren().addAll(mainPlayerImageLbl, mainPlayerNameLbl);
        mainPlayerBox.setMaxWidth(Region.USE_PREF_SIZE);
        mainPlayerBox.setMaxHeight(Region.USE_PREF_SIZE);

        suitColorSelectionBox.setVisible(false);
        suitColorSelectionBox.getChildren().addAll(redLbl, blueLbl, greenLbl, yellowLbl);
        redLbl.setWidth(50);
        redLbl.setHeight(60);
        redLbl.setFill(Color.RED);

        blueLbl.setWidth(50);
        blueLbl.setHeight(60);
        blueLbl.setFill(Color.BLUE);

        greenLbl.setWidth(50);
        greenLbl.setHeight(60);
        greenLbl.setFill(Color.GREEN);

        yellowLbl.setWidth(50);
        yellowLbl.setHeight(60);
        yellowLbl.setFill(Color.YELLOW);

        menuBox.getChildren().add(menuBtn);
        menuBtnBox.getChildren().addAll(newGameBtn, saveGameBtn, loadGameBtn);
        menuBtnBox.setVisible(false);
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

    public HBox getSuitColorSelectionBox() {
        return suitColorSelectionBox;
    }

    public Rectangle getRedLbl() {
        return redLbl;
    }

    public Rectangle getBlueLbl() {
        return blueLbl;
    }

    public Rectangle getYellowLbl() {
        return yellowLbl;
    }

    public Rectangle getGreenLbl() {
        return greenLbl;
    }

    public Button getUnoBtn() {
        return unoBtn;
    }

    public Button getMenuBtn() {
        return menuBtn;
    }

    public Button getNewGameBtn() {
        return newGameBtn;
    }

    public Button getSaveGameBtn() {
        return saveGameBtn;
    }

    public Button getLoadGameBtn() {
        return loadGameBtn;
    }

    public VBox getMenuBtnBox() {
        return menuBtnBox;
    }
}
