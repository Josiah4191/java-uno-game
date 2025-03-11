import javafx.stage.Stage;
import javafx.application.Application;
import view.controllers.MainController;

public class Main extends Application {
    
    public void start(Stage primaryStage) {
        new MainController().run(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
