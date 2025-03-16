package controller;

import javafx.scene.layout.StackPane;
import model.cardgames.unogame.UnoGameManager;
import model.cardgames.unogame.UnoGameState;
import view.GameAreaView;

public class MainController {

    private UnoGameManager gameManager;
    private UnoGameState gameState;
    private GameAreaView gameAreaView;

    private GameAreaController gameAreaController;

    public MainController(UnoGameManager gameManager, GameAreaView gameAreaView) {
        this.gameManager = gameManager;
        this.gameState = gameManager.getGameState();
        this.gameAreaView = gameAreaView;
        this.gameAreaController = new GameAreaController(gameManager, gameAreaView);

        initialize();
    }

    public void initialize() {
        gameManager.initialize();
        gameAreaController.updateGameArea();
    }

    public StackPane getRoot() {
        return gameAreaView.getView();
    }



}
