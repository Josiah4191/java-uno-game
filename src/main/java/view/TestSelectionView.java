package view;

import controller.UnoSelectionController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.cardgames.unogame.UnoGameManager;

import java.io.File;

public class TestSelectionView extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        // load the fxml file
        FXMLLoader loader = new FXMLLoader(getClass().getResource(File.separator + "UnoSelectionView.fxml"));
        Parent root = loader.load();

        // get the controller from the fxml file
        UnoSelectionController controller = loader.getController();

        // inject dependencies into the controller
        UnoGameManager gameManager = new UnoGameManager();
        //gameManager.initialize();
        controller.setGameManager(gameManager);
        controller.setGameState(gameManager.getGameState());

        // set the scene
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Selection View");
        stage.show();
    }
}
