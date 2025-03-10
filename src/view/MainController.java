package view;
import javafx.stage.Stage;
import GUI.MainView;
import GUI.MenuView;
import GUI.OpponentsView;
import GUI.GameAreaView;
import GUI.ControlsView;
import javafx.scene.Scene;

public class MainController {
    private MainView mainView;

    public MainController(Stage stage) {
        // Initialize all UI components
        MenuView menuView = new MenuView();
        OpponentsView opponentsView = new OpponentsView();
        GameAreaView gameAreaView = new GameAreaView();
        ControlsView controlsView = new ControlsView();

        // Create main view and pass UI components
        mainView = new MainView(menuView, opponentsView, gameAreaView, controlsView);

        // Set up scene and stage
        Scene scene = new Scene(mainView.getRoot(), 800, 600);
        stage.setScene(scene);
        stage.setTitle("UNO JavaFX UI - Dark Mode");
        stage.show();
    }
}

