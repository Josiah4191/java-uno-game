import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.application.Application;
import controller.MainController;
import model.cardgames.unogame.UnoGameManager;
import view.MainView;

public class Main extends Application {
    
    public void start(Stage primaryStage) {
        run(primaryStage);
    }

    public void run(Stage primaryStage) {

        // model
        UnoGameManager gameManager = new UnoGameManager();

        // view
        MainView mainView = new MainView();

        // controller
        MainController mainController = new MainController(gameManager, mainView);

        // set scene
        Scene scene = new Scene(mainView.getRoot(), 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("UNO");
        primaryStage.show();

    }
}
