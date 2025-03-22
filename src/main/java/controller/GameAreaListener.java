package controller;

import model.cardgames.unogame.UnoGameState;
import model.players.cardplayers.unoplayers.UnoPlayer;

public interface GameAreaListener {
    void updateGameAreaView();

    void showSuitColorSelection();

    void announceWinner(UnoPlayer player);

    void setGameState(UnoGameState gameState);

    void updatePlayDirection();

    void playClick1();

    void playClick2();

    void playConfirm1();

    void playError1();

}
