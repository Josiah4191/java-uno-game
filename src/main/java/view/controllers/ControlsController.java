package view.controllers;

import games.cardgames.unogame.UnoGameManager;
import games.cardgames.unogame.UnoGameState;
import view.views.ControlsView;

public class ControlsController {

    private ControlsView controlsView = new ControlsView();
    private UnoGameManager gameManager;
    private UnoGameState gameState;

    public ControlsController(UnoGameManager gameManager, UnoGameState gameState) {
    }

    public ControlsView getControlsView() {
        return controlsView;
    }
}
