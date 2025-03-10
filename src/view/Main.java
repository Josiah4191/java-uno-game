package view;  // Make sure this matches your actual package name

import javafx.application.Application;
import javafx.stage.Stage;
import GUI.MainController;



public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        new MainController(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);  // Ensure this is inside the Application subclass
    }
}

