package view.views;

import javafx.scene.layout.*;

public class MainView {
    private BorderPane root;

    public MainView(MenuView menu, OpponentsView opponents, GameAreaView gameArea, PlayerView player) {
        root = new BorderPane();
        root.setStyle("-fx-background-color: #1e1e1e;");

        root.setLeft(menu.getView());
        root.setTop(opponents.getView());
        root.setCenter(gameArea.getView());
        root.setBottom(player.getPlayerView());
    }
    public BorderPane getRoot() {
        return root;
    }
}
