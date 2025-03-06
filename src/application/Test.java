package application;

import games.Difficulty;
import games.cardgames.unogame.UnoGameManager;
import games.cardgames.unogame.UnoGameState;
import games.cardgames.cards.unocards.*;
import games.players.cardplayers.unoplayers.UnoAIPlayer;
import games.players.cardplayers.unoplayers.UnoPlayer;

import java.util.List;

public class Test {
    public static void main(String[] args) {
        UnoGameManager gameManager = new UnoGameManager(UnoEdition.CLASSIC, UnoCardTheme.CLASSIC, Difficulty.EASY);
        UnoGameState gameState = gameManager.getGameState();

        // when gameManager is created, the Deck is made and then transferred to the draw pile

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
        gameManager.addPlayer("Computer1", new UnoAIPlayer(gameState));

        // get the list of players
        List<UnoPlayer> players = gameManager.getPlayers();

        // get the list of player names
        List<String> playerNames = gameManager.getPlayerNames();

        // deal cards to the players
        gameManager.dealCards(7, players);

        // get a player
        var playerJosiah = gameManager.getPlayer("Josiah");
        var computer1Player = gameManager.getPlayer("Computer1");
        System.out.println(playerJosiah);
        System.out.println(computer1Player.getClass());

        // play a card from the player
        UnoCard card2 = gameManager.playCard("Josiah", 2);
        // look at the selected card
        System.out.println(card2);
        // play a card from the computer



        /*
            Change the playCard methods to automatically add the card to discard pile

            Two options:
                - CardPlayer can have abstract playCard(), and UnoPlayer has to implement and return null
                - Remove playCard() from CardPlayer, and make sure I use UnoAIPlayer type when playing cards
                - use var keyword! genius
         */





    }
}
