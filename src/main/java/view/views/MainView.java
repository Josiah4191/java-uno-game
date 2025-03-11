package view.views;

import javafx.scene.layout.*;

public class MainView {
    private BorderPane root;

    public MainView(MenuView menu, OpponentsView opponents, GameAreaView gameArea, ControlsView controls) {
        root = new BorderPane();
        root.setStyle("-fx-background-color: #1e1e1e;");

        root.setLeft(menu.getView());
        root.setTop(opponents.getView());
        root.setCenter(gameArea.getView());
        root.setBottom(controls.getView());
    }
    public BorderPane getRoot() {
        return root;
    }
}
