package controller;

import model.cardgames.unogame.PlayDirection;
import model.cardgames.unogame.UnoGameManager;
import model.cardgames.unogame.UnoGameState;
import view.OpponentsView;

public class OpponentsController {

    private OpponentsView opponentsView;
    private UnoGameManager gameManager;
    private UnoGameState gameState;

    public OpponentsController(UnoGameManager gameManager, OpponentsView opponentsView) {
        this.gameManager = gameManager;
        this.gameState = gameManager.getGameState();
        this.opponentsView = opponentsView;
    }

    public OpponentsView getOpponentsView() {
        return opponentsView;
    }

    public void setOpponentNames() {
    }

    public void setPlayDirection() {
        PlayDirection direction = gameState.getDirection();
        opponentsView.getDirectionPlaceholder().setText(direction.name());
    }
}
