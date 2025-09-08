package com.josiah.uno;

import com.josiah.uno.controller.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    // Store a reference to the primary stage
    private static Stage primaryStage;

    public void start(Stage stage) throws Exception {
        primaryStage = stage; // set the stage

        SceneManager sceneManager = new SceneManager(stage); // create the SceneManager
        sceneManager.loadLoginScene(); // load the loginScene
        sceneManager.launch("login"); // launch the scene and load the loginScene

    }

}
