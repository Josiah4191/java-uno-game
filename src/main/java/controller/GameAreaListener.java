package controller;

import model.cardgames.cards.unocards.UnoSuit;
import model.cardgames.unogame.UnoGameState;
import model.players.cardplayers.unoplayers.UnoPlayer;

public interface GameAreaListener {
    void updateGameAreaView();

    void showSuitColorSelection();

    void announceWinner(UnoPlayer player);

    void setGameState(UnoGameState gameState);

}
