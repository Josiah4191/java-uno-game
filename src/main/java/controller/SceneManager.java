package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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

    private GameAreaController gameAreaController;

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

            this.gameAreaController = loader.getController();
            gameAreaController.setSceneManager(this);
            gameAreaController.setGameManager(gameManager);
            gameAreaController.setGameState(gameManager.getGameState());
            gameAreaController.setClient(client);
            gameManager.setGameAreaListener(gameAreaController);

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

        stage.setOnCloseRequest(e -> {
            Scene gameAreaScene = scenes.get("gameArea");
            if (gameAreaController != null && gameAreaScene != null) {
                gameAreaController.quit();
            }
        });
    }

    public void removeScene(String scene) {
        if (scene.equals("gameArea")) {
            gameAreaController = null;
        }
        scenes.remove(scene);
    }
}
