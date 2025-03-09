package application;

import games.Difficulty;
import games.cardgames.cards.unocards.UnoCard;
import games.cardgames.cards.unocards.UnoCardTheme;
import games.cardgames.cards.unocards.UnoEdition;
import games.cardgames.unogame.PlayDirection;
import games.cardgames.unogame.UnoClassicRules;
import games.cardgames.unogame.UnoGameManager;
import games.cardgames.unogame.UnoRules;
import games.players.cardplayers.unoplayers.UnoPlayer;
import games.players.cardplayers.unoplayers.UnoPlayerAI;

import java.util.List;

public class Test {
    public static void main(String[] args) {

        // create game manager
        UnoGameManager manager = new UnoGameManager(UnoEdition.CLASSIC, UnoCardTheme.CLASSIC, Difficulty.EASY);

        // get the deck
        manager.getDeck();

        // get the draw pile
        manager.getDrawPile();

        // get the discard pile
        manager.getDiscardPile();

        // get the rules
        UnoRules rules = manager.getRules();

        // set the rules
        manager.setRules(new UnoClassicRules());

        // get the difficulty
        Difficulty difficulty = manager.getDifficulty();

        // set the difficulty
        manager.setDifficulty(Difficulty.EASY);

        // get the card theme
        manager.getTheme();

        // set the card theme
        manager.setTheme(UnoCardTheme.CLASSIC);

        // get the uno game edition
        UnoEdition edition = manager.getEdition();

        // get the direction of play
        PlayDirection direction = manager.getDirection();

        // reverse the direction of play
        manager.reversePlayDirection();

        // create a player
        UnoPlayer player = new UnoPlayer();
        UnoPlayer playerAI1 = new UnoPlayerAI(manager.getGameState());
        UnoPlayer playerAI2 = new UnoPlayerAI(manager.getGameState());


        // add single player to game
        manager.addPlayer(player);

        // add list of players to game
        manager.addPlayers(List.of(player, playerAI1, playerAI2));

        // get a single player
        manager.getPlayer(0);

        // get a list of the players
        manager.getPlayers();

        // deal cards to players
        manager.dealCards(7, List.of(player, playerAI1, playerAI2));

        // get the current player position
        //int playerPosition = manager.getPlayerPosition();

        // set the next player position
        manager.moveToNextPlayer();

        // skip the next player and set position
        manager.skipNextPlayer();






































        /*
        ideas: player class
            - give player name field
            - change addPlayer to createPlayer
                - pass the name to constructor for creating player
            - create new method createAIPlayer
                - no parameter constructor that picks name from a list of names
            - instead of a hashmap, just have a list of players.
            - change the get playerNames and getPlayer methods to work with list
                - or still keep hashmap and use player.name as keys
        ideas: UnoPlayer class
            - add a boolean sayUno
            - will need a setSayUno method to reset it to false as well
            - add method callUno and sayUno
            - sayUno method will make boolean sayUno true
            - callUno method
                - this method accepts another player as the parameter
                - this method uses the validator/rules class to check whether the other player:
                    1.) has only 1 card remaining
                    2.) their sayUno variable is false
                        - if both of those things are true, then add cards to their hand of cards
                        - this can also return a boolean for checking whether we should do some kind of
                            visual action in the UI. like return true if we added cards, and then we can
                            output some kind of message in the game. if it is a false accusation, maybe we
                            can let them know it is a false accusation.
                        - another solution is that we can have every player's sayUno status displayed on the screen
                            so everybody can see
                        - if both of them are not true, then don't do anything
         */
    }
}
