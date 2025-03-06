
/*


import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) {
        // Main layout using BorderPane
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));

        // Top Region: Opponents and Direction Arrow Placeholder
        VBox topBox = new VBox(5);
        topBox.setAlignment(Pos.CENTER);

        // HBox for opponent info
        HBox opponentsBox = new HBox(10);
        opponentsBox.setAlignment(Pos.CENTER);
        opponentsBox.getChildren().addAll(
                new Label("Opponent 1: 5 cards"),
                new Label("Opponent 2: 4 cards"),
                new Label("Opponent 3: 6 cards")
        );
        // Placeholder for direction arrow
        Label directionPlaceholder = new Label("Direction Arrow Placeholder");
        topBox.getChildren().addAll(opponentsBox, directionPlaceholder);
        root.setTop(topBox);

        // Left Region: Game Logo and Menu
        VBox leftBox = new VBox(10);
        leftBox.setPadding(new Insets(5));
        leftBox.setAlignment(Pos.TOP_CENTER);

        Label gameLogo = new Label("UNO");
        gameLogo.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // Menu buttons in a TitledPane
        VBox menuBox = new VBox(5);
        Button newGameButton = new Button("New Game");
        Button saveGameButton = new Button("Save Game");
        Button loadGameButton = new Button("Load Game");
        Button rulesButton = new Button("Rules");
        Button scoreHistoryButton = new Button("Score History");
        menuBox.getChildren().addAll(newGameButton, saveGameButton, loadGameButton, rulesButton, scoreHistoryButton);
        TitledPane menuPane = new TitledPane("Menu", menuBox);
        menuPane.setExpanded(true);

        leftBox.getChildren().addAll(gameLogo, menuPane);
        root.setLeft(leftBox);

        // Center Region: Draw/Discard Piles, Rounds Label, and Player Cards
        VBox centerBox = new VBox(10);
        centerBox.setPadding(new Insets(5));
        centerBox.setAlignment(Pos.CENTER);

        // HBox for Draw and Discard Pile placeholders
        HBox pilesBox = new HBox(20);
        pilesBox.setAlignment(Pos.CENTER);
        Label drawPile = new Label("Draw Pile Placeholder");
        drawPile.setStyle("-fx-border-color: black; -fx-padding: 40px;");
        Label discardPile = new Label("Discard Pile Placeholder");
        discardPile.setStyle("-fx-border-color: black; -fx-padding: 40px;");
        pilesBox.getChildren().addAll(drawPile, discardPile);

        // Label for rounds remaining
        Label roundsLabel = new Label("Rounds Remaining: 5");

        // HBox for player card placeholders
        HBox playerCardsBox = new HBox(10);
        playerCardsBox.setAlignment(Pos.CENTER);
        for (int i = 1; i <= 5; i++) {
            Label cardPlaceholder = new Label("Card " + i);
            cardPlaceholder.setStyle("-fx-border-color: gray; -fx-padding: 20px;");
            playerCardsBox.getChildren().add(cardPlaceholder);
        }

        centerBox.getChildren().addAll(pilesBox, roundsLabel, playerCardsBox);
        root.setCenter(centerBox);

        // Bottom Region: Player Unit Frame and Control Buttons
        VBox bottomBox = new VBox(10);
        bottomBox.setPadding(new Insets(5));
        bottomBox.setAlignment(Pos.CENTER);

        Label playerFrame = new Label("Player: Your Name (Unit Frame Placeholder)");
        HBox controlButtonsBox = new HBox(10);
        controlButtonsBox.setAlignment(Pos.CENTER);
        Button playCardButton = new Button("Play Card");
        Button drawCardButton = new Button("Draw Card");
        Button passTurnButton = new Button("Pass Turn");
        controlButtonsBox.getChildren().addAll(playCardButton, drawCardButton, passTurnButton);

        bottomBox.getChildren().addAll(playerFrame, controlButtonsBox);
        root.setBottom(bottomBox);

        // Right Region: Additional Info Placeholder
        VBox rightBox = new VBox(10);
        rightBox.setPadding(new Insets(5));
        rightBox.setAlignment(Pos.TOP_CENTER);
        Label additionalInfo = new Label("Additional Info Placeholder");
        rightBox.getChildren().add(additionalInfo);
        root.setRight(rightBox);

        // Set up the Scene and Stage
        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.setTitle("UNO JavaFX UI");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
 */