package view;

import javafx.stage.Stage;
import javafx.application.Application;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        new MainController(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
