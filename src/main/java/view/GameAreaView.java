package view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GameAreaView {
    private StackPane gameArea = new StackPane();
    private FlowPane playerCardsBox = new FlowPane();
    private HBox opponentPlayerBox = new HBox(10);
    private HBox suitColorSelectionBox = new HBox(10);
    private HBox pilesBox = new HBox(20);
    private HBox menuBox = new HBox(5);
    private VBox centerBox = new VBox(10);
    private VBox menuBtnBox = new VBox(5);
    private VBox mainPlayerBox = new VBox();
    private Label playDirectionLbl = new Label();
    private Label drawPileLbl = new Label();
    private Label discardPileLbl = new Label();
    private Label mainPlayerImageLbl = new Label();
    private Label mainPlayerNameLbl = new Label();
    private Button passBtn = new Button("Pass");
    private Button unoBtn = new Button("UNO");
    private Button menuBtn = new Button("Menu");
    private Button newGameBtn = new Button("New Game");
    private Button saveGameBtn = new Button("Save Game");
    private Button loadGameBtn = new Button("Load Game");
    private Rectangle redRect = new Rectangle();
    private Rectangle blueRect = new Rectangle();
    private Rectangle yellowRect = new Rectangle();
    private Rectangle greenRect = new Rectangle();

    public GameAreaView() {

        // Center alignment for everything except menus
        centerBox.setAlignment(Pos.CENTER);
        opponentPlayerBox.setAlignment(Pos.CENTER);
        pilesBox.setAlignment(Pos.CENTER);
        playerCardsBox.setAlignment(Pos.CENTER);
        mainPlayerBox.setAlignment(Pos.CENTER);
        suitColorSelectionBox.setAlignment(Pos.CENTER);

        // Menu boxes and menu buttons
        menuBox.getChildren().add(menuBtn);
        menuBtnBox.getChildren().addAll(newGameBtn, saveGameBtn, loadGameBtn);
        menuBtnBox.setVisible(false);

        // Pass button and Uno button
        passBtn.setVisible(false);
        passBtn.setStyle("-fx-border-color: yellow; -fx-border-width: 3px; -fx-border-radius: 5px;");
        unoBtn.setVisible(false);
        unoBtn.setStyle("-fx-border-color: yellow; -fx-border-width: 3px; -fx-border-radius: 5px;");

        // Play direction label
        playDirectionLbl.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: white;");

        // Box to hold the draw pile label and discard pile label
        pilesBox.getChildren().addAll(drawPileLbl, discardPileLbl);

        // Box to hold the name label and player image label of the main player
        mainPlayerBox.getChildren().addAll(mainPlayerImageLbl, mainPlayerNameLbl);
        mainPlayerBox.setMaxWidth(Region.USE_PREF_SIZE);
        mainPlayerBox.setMaxHeight(Region.USE_PREF_SIZE);

        // Player card box add some spacing for the card labels it will hold
        playerCardsBox.setHgap(10);
        playerCardsBox.setVgap(10);

        // Suit selection rectangle shapes for when a Wild card is played
        redRect.setWidth(50);
        redRect.setHeight(60);
        redRect.setFill(Color.RED);

        blueRect.setWidth(50);
        blueRect.setHeight(60);
        blueRect.setFill(Color.BLUE);

        greenRect.setWidth(50);
        greenRect.setHeight(60);
        greenRect.setFill(Color.GREEN);

        yellowRect.setWidth(50);
        yellowRect.setHeight(60);
        yellowRect.setFill(Color.YELLOW);

        suitColorSelectionBox.setVisible(false);
        suitColorSelectionBox.getChildren().addAll(redRect, blueRect, greenRect, yellowRect);

        // The center box for the game screen
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

        // Add the center box to the stack pane game area
        gameArea.getChildren().add(centerBox);
        gameArea.setStyle("-fx-background-color: darkgreen; -fx-padding: 10;");
    }

    public FlowPane getPlayerCardsBox() {
        return playerCardsBox;
    }

    public StackPane getView() {
        return gameArea;
    }

    public HBox getSuitColorSelectionBox() {
        return suitColorSelectionBox;
    }

    public HBox getOpponentPlayerBox() {
        return opponentPlayerBox;
    }

    public VBox getMainPlayerBox() {
        return mainPlayerBox;
    }

    public VBox getMenuBtnBox() {
        return menuBtnBox;
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

    public Label getPlayDirectionLbl() {
        return playDirectionLbl;
    }

    public Rectangle getRedRect() {
        return redRect;
    }

    public Rectangle getBlueRect() {
        return blueRect;
    }

    public Rectangle getYellowRect() {
        return yellowRect;
    }

    public Rectangle getGreenRect() {
        return greenRect;
    }

    public Button getPassBtn() {
        return passBtn;
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

}
