package controller;

import multiplayer.server.servermessage.GameEvent;

public interface GameAreaListener {
    void updateGameView(GameEvent event);

    /*
    void showSuitColorSelection();

    void announceWinner(UnoPlayer player);

    void updatePlayDirection();
    
    void setGameState(UnoGameState gameState);
     */



}
