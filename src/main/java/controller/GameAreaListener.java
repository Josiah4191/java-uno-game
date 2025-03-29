package controller;

import model.cardgame.unogame.UnoGameState;
import model.player.cardplayer.unoplayer.UnoPlayer;

public interface GameAreaListener {
    void updateGameAreaView();

    void showSuitColorSelection();

    void announceWinner(UnoPlayer player);

    void updatePlayDirection();
    
    void setGameState(UnoGameState gameState);



}
