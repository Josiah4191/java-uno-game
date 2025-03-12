package view.controllers;

import games.cardgames.unogame.UnoGameManager;
import games.cardgames.unogame.UnoGameState;
import games.players.cardplayers.unoplayers.UnoPlayer;
import view.views.PlayerView;

public class PlayerController {

    private PlayerView playerView = new PlayerView();
    private UnoGameManager gameManager;
    private UnoGameState gameState;

    public PlayerController(UnoGameManager gameManager, UnoGameState gameState) {
        this.gameManager = gameManager;
        this.gameState = gameState;
    }

    public void setPlayerName() {
        UnoPlayer player = gameState.getPlayer(0);
        playerView.getPlayerName().setText(player.getName());
    }

    public PlayerView getPlayerView() {
        return playerView;
    }
}
