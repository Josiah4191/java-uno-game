package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {
        Pane pane = new Pane();
        pane.setPrefSize(400, 250);

        Scene scene = new Scene(pane);

        primaryStage.setTitle("Test Title");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
