package model.database;

import model.cardgames.unogame.UnoGameState;

public class Test {
    public static void main(String[] args) {

        UnoGameState loadedGameState = SimpleUnoDatabase.loadGame();

        System.out.println(loadedGameState.getCurrentSuit());

    }
}
