package controller;

import model.cardgames.unogame.UnoGameManager;
import model.cardgames.unogame.UnoGameState;
import view.MenuView;

public class MenuController {

    private MenuView menuView;
    private UnoGameManager gameManager;
    private UnoGameState gameState;

    public MenuController(UnoGameManager gameManager, MenuView menuView) {
        this.gameManager = gameManager;
        this.gameState = gameManager.getGameState();
        this.menuView = menuView;
    }

    public MenuView getMenuView() {
        return menuView;
    }

}
