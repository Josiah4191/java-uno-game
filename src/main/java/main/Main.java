package main;

import controller.SelectionController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.application.Application;
import model.cardgames.unogame.UnoGameManager;

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
        UnoGameManager gameManager = new UnoGameManager();

        // view
        // load the fxml file
        FXMLLoader loader = new FXMLLoader(getClass().getResource(File.separator + "SelectionView.fxml"));
        Parent root = loader.load();

        // get the controller from the fxml file
        SelectionController controller = loader.getController();
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
