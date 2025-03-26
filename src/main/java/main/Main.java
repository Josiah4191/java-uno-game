package main;

import controller.OfflineController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.application.Application;
import multiplayer.client.unoclient.ClientUnoGameManager;

import java.io.File;
import java.io.IOException;

public class Main extends Application {

    private static Stage primaryStage;

    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        run(primaryStage);
    }

    public void run(Stage primaryStage) throws IOException {

        // model
        ClientUnoGameManager gameManager = new ClientUnoGameManager();

        // view
        // load the fxml file
        FXMLLoader loader = new FXMLLoader(getClass().getResource(File.separator + "OfflineView.fxml"));
        Parent root = loader.load();

        // get the controller from the fxml file
        OfflineController controller = loader.getController();
        controller.setGameManager(gameManager);
        controller.setGameState(gameManager.getGameState());

        // set scene
        Scene scene = new Scene(root, 800, 700);
        primaryStage.setScene(scene);
        primaryStage.setTitle("UNO");
        primaryStage.show();
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

}
