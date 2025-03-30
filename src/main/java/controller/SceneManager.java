package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.cardgame.unogame.ClientUnoGameManager;
import multiplayer.client.Client;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SceneManager {

    private Stage stage;

    private Scene loginScene;

    private Scene gameSelectionScene;

    private Scene offlineScene;

    private Scene onlineScene;

    private Scene gameAreaScene;

    private Map<String, Scene> scenes = new HashMap<>();

    public SceneManager(Stage stage) {
        this.stage = stage;
    }

    public void loadLoginScene() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(File.separator + "view/LoginView.fxml"));
        try {
            Parent root = loader.load();
            loginScene = new Scene(root, 900, 700);

            LoginController controller = loader.getController();
            controller.setSceneManager(this);

            scenes.put("login", loginScene);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadOfflineScene() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(File.separator + "view/OfflineView.fxml"));
        try {
            Parent root = loader.load();
            offlineScene = new Scene(root, 900, 700);

            OfflineController controller = loader.getController();
            controller.setSceneManager(this);

            scenes.put("offline", offlineScene);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadOnlineScene() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(File.separator + "view/OnlineView.fxml"));
        try {
            Parent root = loader.load();
            onlineScene = new Scene(root, 900, 700);

            OnlineController controller = loader.getController();
            controller.setSceneManager(this);

            scenes.put("online", onlineScene);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadGameSelectionScene() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(File.separator + "view/GameSelectionView.fxml"));
        try {
            Parent root = loader.load();
            gameSelectionScene = new Scene(root, 900, 700);

            GameSelectionController controller = loader.getController();
            controller.setSceneManager(this);

            scenes.put("gameSelection", gameSelectionScene);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadGameAreaScene(ClientUnoGameManager gameManager, Client client) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(File.separator + "view/GameAreaView.fxml"));
        try {
            Parent root = loader.load();
            gameAreaScene = new Scene(root, 900, 700);

            GameAreaController controller = loader.getController();
            controller.setSceneManager(this);
            controller.setGameManager(gameManager);
            controller.setGameState(gameManager.getGameState());
            controller.setClient(client);
            gameManager.setGameAreaListener(controller);

            scenes.put("gameArea", gameAreaScene);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void launch(String scene) {
        switchScene(scene);
    }

    public void switchScene(String scene) {
        stage.setScene(scenes.get(scene));
        stage.setTitle("UNO");
        stage.show();
    }
}
