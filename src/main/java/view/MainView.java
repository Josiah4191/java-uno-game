package view;

import javafx.scene.layout.*;

import java.awt.*;

public class MainView {
    private BorderPane root;

    // menu view
    MenuView menuView = new MenuView();
    OpponentsView opponentsView = new OpponentsView();
    GameAreaView gameAreaView = new GameAreaView();
    PlayerView playerView = new PlayerView();

    public MainView() {
        root = new BorderPane();
        root.setStyle("-fx-background-color: darkgreen;");

        root.setLeft(menuView.getView());
        root.setTop(opponentsView.getView());
        root.setCenter(gameAreaView.getView());
        root.setBottom(playerView.getPlayerView());
    }
    public BorderPane getRoot() {
        return root;
    }

    public MenuView getMenuView() {
        return menuView;
    }

    public OpponentsView getOpponentsView() {
        return opponentsView;
    }

    public GameAreaView getGameAreaView() {
        return gameAreaView;
    }

    public PlayerView getPlayerView() {
        return playerView;
    }
}
