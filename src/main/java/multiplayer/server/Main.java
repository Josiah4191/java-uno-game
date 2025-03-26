package multiplayer.server;

import controller.OfflineController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import multiplayer.client.Client;
import multiplayer.client.unoclient.ClientUnoGameManager;
import multiplayer.server.unoserver.ServerUnoGameManager;

import java.io.File;

public class Main extends Application {

    private static Stage primaryStage;

    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource(File.separator + "view/OfflineView.fxml"));
        Parent root = loader.load();
        OfflineController controller = loader.getController();

        ServerUnoGameManager serverGameManager = new ServerUnoGameManager();

        Server server = new Server();
        server.setGameManager(serverGameManager);


        Client client = new Client("localhost", server.getPort());
        ClientUnoGameManager clientGameManager = new ClientUnoGameManager();
        client.setGameManager(clientGameManager);
        controller.setGameManager(clientGameManager);
        controller.setGameState(clientGameManager.getGameState());

        Scene scene = new Scene(root, 900, 700);
        stage.setScene(scene);
        stage.setTitle("UNO");
        stage.show();

    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

}
