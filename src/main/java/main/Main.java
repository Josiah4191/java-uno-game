package main;

import controller.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    private static Stage primaryStage;

    public void start(Stage stage) throws Exception {
        primaryStage = stage;

        SceneManager sceneManager = new SceneManager(stage);
        sceneManager.loadLoginScene();
        sceneManager.launch("login");

    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

}
