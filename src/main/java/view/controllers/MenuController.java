package view.controllers;

import games.cardgames.unogame.UnoGameManager;
import games.cardgames.unogame.UnoGameState;
import view.views.MenuView;

public class MenuController {

    private MenuView menuView = new MenuView();
    private UnoGameManager gameManager;
    private UnoGameState gameState;

    public MenuController(UnoGameManager gameManager, UnoGameState gameState) {
    }

    public MenuView getMenuView() {
        return menuView;
    }

}
