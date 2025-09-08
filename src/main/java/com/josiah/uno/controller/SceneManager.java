package com.josiah.uno.controller;

import com.josiah.uno.model.cardgame.unogame.ClientUnoGameManager;
import com.josiah.uno.multiplayer.client.Client;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/*
    This class manages the scenes for the program.

    - Stage holds a reference to the stage

    - loginScene holds a reference to the LoginView
    - gameSelectionScene holds a reference to the GameSelectionView
    - offlineScene holds a reference to the OfflineView
    - onlineScene holds a reference to the OnlineView
    - gameAreaScene holds a reference to the GameAreaView

    - gameAreaController holds a reference to the GameAreaController. This is used for setting up the GameAreaView

    - the scenes are stored in a map.
        - when the scene is loaded, it is added to the map.
        - the key for the scene in the map is a String which corresponds to the type of view. The map is used for
          switching scenes in the program.
 */

public class SceneManager {

    private Stage stage;

    private Scene loginScene;

    private Scene gameSelectionScene;

    private Scene offlineScene;

    private Scene onlineScene;

    private Scene gameAreaScene;

    private GameAreaController gameAreaController;

    private Map<String, Scene> scenes = new HashMap<>();

    // Set the stage in the SceneManager constructor
    public SceneManager(Stage stage) {
        this.stage = stage;
    }

    // This method loads the LoginView
    public void loadLoginScene() {
        // Use FXMLLoader to load the LoginView.fxml resource file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LoginView.fxml"));
        try {
            Parent root = loader.load(); // get the root by calling loader.load() method
            loginScene = new Scene(root, 900, 700); // create and set the scene with loaded root

            LoginController controller = loader.getController(); // get controller from the FXMLLoader object
            controller.setSceneManager(this); // set the controller sceneManager

            scenes.put("login", loginScene); // store the scene in the map

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // This method loads the OfflineView
    public void loadOfflineScene() {
        // Use FXMLLoader to load the OfflineView.fxml resource file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/OfflineView.fxml"));
        try {
            Parent root = loader.load(); // get the root by calling loader.load() method
            offlineScene = new Scene(root, 900, 700); // create and set the scene with loaded root

            OfflineController controller = loader.getController(); // get controller from the FXMLLoader object
            controller.setSceneManager(this); // set the controller sceneManager

            scenes.put("offline", offlineScene); // store the scene in the map

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // This method loads the OnlineView
    public void loadOnlineScene() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(File.separator + "view/OnlineView.fxml"));
        try {
            Parent root = loader.load(); // get the root by calling loader.load() method
            onlineScene = new Scene(root, 900, 700); // create and set the scene with loaded root

            OnlineController controller = loader.getController(); // get controller from the FXMLLoader object
            controller.setSceneManager(this); // set the controller sceneManager

            scenes.put("online", onlineScene); // store the scene in the map

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadGameSelectionScene() {
        // Use FXMLLoader to load the GameSelectionView.fxml resource file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/GameSelectionView.fxml"));
        try {
            Parent root = loader.load(); // get the root by calling loader.load() method
            gameSelectionScene = new Scene(root, 900, 700); // create and set the scene with loaded root

            GameSelectionController controller = loader.getController(); // get controller from the FXMLLoader object
            controller.setSceneManager(this); // set the controller sceneManager

            scenes.put("gameSelection", gameSelectionScene); // store the scene in the map

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadGameAreaScene(ClientUnoGameManager gameManager, Client client) {
        // Use FXMLLoader to load the GameAreaView.fxml resource file
        FXMLLoader loader = new FXMLLoader(getClass().getResource( "/view/GameAreaView.fxml"));
        try {
            Parent root = loader.load(); // get the root by calling loader.load() method
            gameAreaScene = new Scene(root, 900, 700); // create and set the scene with loaded root

            this.gameAreaController = loader.getController(); // get controller from the FXMLLoader object
            gameAreaController.setSceneManager(this); // set the controller sceneManager
            gameAreaController.setGameManager(gameManager); // set the gameManager
            gameAreaController.setGameState(gameManager.getGameState()); // set the gameState
            gameAreaController.setClient(client); // set the Client
            gameManager.setGameAreaListener(gameAreaController); // set the gameAreaListener

            scenes.put("gameArea", gameAreaScene); // store the scene in the map

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // This method is used to launch the program. It loads the login scene.
    public void launch(String scene) {
        switchScene(scene);
    }

    // This method switches scenes. It accepts a String that corresponds to the selected scene in the map.
    public void switchScene(String scene) {
        stage.setScene(scenes.get(scene)); // set the scene by getting the scene from the map based on the given String
        stage.setTitle("UNO"); // set the title to UNO
        stage.show(); // show the stage

        /*
             Set the behavior of the exit button when the window is closed. If the exit button is clicked and the
             client/server has been created, then this will use the gameAreaController to call its quit method, which
             will shut down the server/client threads.
         */
        stage.setOnCloseRequest(e -> {
            Scene gameAreaScene = scenes.get("gameArea");
            if (gameAreaController != null && gameAreaScene != null) {
                gameAreaController.quit();
            }
        });
    }

    // This method removes a scene from the map
    public void removeScene(String scene) {
        /*
            Check if the scene that is being removed is the GameAreaView
            If it is the GameAreaView, the gameAreaController needs to be set to null
         */
        if (scene.equals("gameArea")) {
            gameAreaController = null;
        }
        scenes.remove(scene); // remove the scene from the map
    }
}
