package view;

import games.Difficulty;
import games.cardgames.cards.unocards.UnoCardTheme;
import games.cardgames.cards.unocards.UnoEdition;
import games.cardgames.unogame.UnoGameManager;
import games.cardgames.unogame.UnoGameState;
import games.players.cardplayers.unoplayers.UnoPlayer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.File;

public class MainController {
    private MainView mainView;


    public MainController(Stage stage) {
        // Initialize all UI components
        MenuView menuView = new MenuView();
        OpponentsView opponentsView = new OpponentsView();
        GameAreaView gameAreaView = new GameAreaView();
        ControlsView controlsView = new ControlsView();

        // create game manager
        UnoGameManager manager = new UnoGameManager(UnoEdition.CLASSIC, UnoCardTheme.CLASSIC, Difficulty.EASY);

        // Create main view and pass UI components
        mainView = new MainView(menuView, opponentsView, gameAreaView, controlsView);

        // Set up scene and stage
        Scene scene = new Scene(mainView.getRoot(), 800, 600);
        stage.setScene(scene);
        stage.setTitle("UNO JavaFX UI - Dark Mode");
        stage.show();

        menuView.getNewGameButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                manager.startNewGame();
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "New Game Started", ButtonType.OK);
                alert.show();
                gameAreaView.displayPlayerCardBox(manager.getGameState());
            }
        });

        //String image = manager.getImage();


    }
}

