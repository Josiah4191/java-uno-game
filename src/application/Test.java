package application;

import games.Difficulty;
import games.cardgames.cards.unocards.UnoCard;
import games.cardgames.cards.unocards.UnoCardTheme;
import games.cardgames.cards.unocards.UnoEdition;
import games.cardgames.unogame.UnoGameManager;
import games.players.cardplayers.unoplayers.UnoPlayer;
import games.players.cardplayers.unoplayers.UnoPlayerAI;

import java.util.List;

public class Test {
    public static void main(String[] args) {
        // create UnoGameManager
        UnoGameManager gameManager = new UnoGameManager(UnoEdition.CLASSIC, UnoCardTheme.CLASSIC, Difficulty.EASY);
        // when gameManager is created, the Deck is created and automatically transferred to the draw pile
        // loop through the deck. it will be empty because it automatically transferred to draw pile
        gameManager.getDeck().forEach(System.out::println);
        // get the number of cards in the deck
        System.out.println(gameManager.getDeck().size());
        // check if the deck is empty
        System.out.println(gameManager.getDeck().isEmpty());
        System.out.println();

        // loop through draw pile. it will contain a shuffled 108 card uno deck
        gameManager.getDrawPile().forEach(System.out::println);
        // get the number of cards
        System.out.println(gameManager.getDrawPile().size());
        // check if the draw pile is empty
        System.out.println(gameManager.getDeck().isEmpty());
        System.out.println();

        // loop through the discard pile. it will be empty right now because we didn't add a card yet
        gameManager.getDiscardPile().forEach(System.out::println);
        // check the number of cards in discard pile
        System.out.println(gameManager.getDiscardPile().size());
        // check if the draw pile is empty
        System.out.println(gameManager.getDeck().isEmpty());
        System.out.println();

        // look at the last card of the draw pile from the list
        gameManager.getDrawPile().forEach(System.out::println);
        // add a card to the discard pile from the draw pile
        UnoCard card = gameManager.drawCardFromDrawPile();
        // check the size of the draw pile to see that the card was removed
        System.out.println(gameManager.getDrawPile().size());
        // check the size of the discard pile to see that a card was added
        System.out.println(gameManager.getDiscardPile().size());
        // check the actual card in the discard pile to verify that it was the card that was removed
        gameManager.getDrawPile().forEach(System.out::println);

        // add a player to the game
        gameManager.addPlayer("Josiah", new UnoPlayer());
        gameManager.addPlayer("Computer1", new UnoPlayerAI(gameManager.getGameState()));

        // get the list of players
        List<UnoPlayer> players = gameManager.getPlayers();

        // get the list of player names
        List<String> playerNames = gameManager.getPlayerNames();

        // deal cards to the players
        gameManager.dealCards(7, players);

        // get a player
        UnoPlayer playerJosiah = gameManager.getPlayer("Josiah");
        UnoPlayer computer1Player = gameManager.getPlayer("Computer1");
        System.out.println(playerJosiah.getClass());
        System.out.println(computer1Player.getClass());
        // play a card
        computer1Player.playCard();
        playerJosiah.playCard(3);

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
