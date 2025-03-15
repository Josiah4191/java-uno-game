package controller;

import model.cardgames.unogame.UnoGameManager;
import model.cardgames.unogame.UnoGameState;
import model.players.cardplayers.unoplayers.UnoPlayer;
import view.PlayerView;

public class PlayerController {

    private PlayerView playerView;
    private UnoGameManager gameManager;
    private UnoGameState gameState;

    public PlayerController(UnoGameManager gameManager, PlayerView playerView) {
        this.gameManager = gameManager;
        this.gameState = gameManager.getGameState();
        this.playerView = playerView;
    }

    public void setPlayerName() {
        UnoPlayer player = gameState.getPlayer(0);
        playerView.getPlayerName().setText(player.getName());
    }

    public PlayerView getPlayerView() {
        return playerView;
    }
}
