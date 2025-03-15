package test;

import model.Difficulty;
import model.cardgames.cards.unocards.UnoEdition;
import model.cardgames.unogame.UnoGameManager;
import model.cardgames.unogame.UnoGameState;
import model.players.cardplayers.unoplayers.UnoPlayer;

public class Test {
    public static void main(String[] args) {

        // create game manager
        UnoGameManager gameManager = new UnoGameManager();

        // get the game state
        UnoGameState gameState = gameManager.getGameState();

        // initialize the game
        /*
        Currently, initialize will create a regular player (the main player), sets the main player,
        creates some computer players, sets the name for each player, adds the players to the list of players,
        deals cards to players, and adds a card to the discard pile.
        */

        gameManager.initialize();

        // create a player
        UnoPlayer player = new UnoPlayer();


    }
}
