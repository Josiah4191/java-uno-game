import controller.GameAreaController;
import controller.MainController;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.application.Application;
import model.cardgames.unogame.UnoGameManager;
import view.GameAreaView;

public class Main extends Application {
    
    public void start(Stage primaryStage) {
        run(primaryStage);
    }

    public void run(Stage primaryStage) {

        // model
        UnoGameManager gameManager = new UnoGameManager();

        // view
        GameAreaView gameAreaView = new GameAreaView();

        // controller
        MainController mainController = new MainController(gameManager, gameAreaView);

        // set scene
        Scene scene = new Scene(mainController.getRoot(), 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("UNO");
        primaryStage.show();

    }
}
