package view.controllers;

import games.cardgames.unogame.PlayDirection;
import games.cardgames.unogame.UnoGameManager;
import games.cardgames.unogame.UnoGameState;
import view.views.OpponentsView;

public class OpponentsController {

    private OpponentsView opponentsView = new OpponentsView();
    private UnoGameManager gameManager;
    private UnoGameState gameState;

    public OpponentsController(UnoGameManager gameManager, UnoGameState gameState) {
        this.gameManager = gameManager;
        this.gameState = gameState;
    }

    public OpponentsView getOpponentsView() {
        return opponentsView;
    }

    public void setOpponentNames() {
    }

    public void setPlayDirection() {
        PlayDirection direction = gameManager.getDirection();
        opponentsView.getDirectionPlaceholder().setText(direction.name());
    }
}
