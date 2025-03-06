package application;

import games.cardgames.cardplayers.CardPlayer;
import games.cardgames.uno.UnoGameState;
import games.cardgames.uno.unocards.*;
import games.cardgames.uno.unoplayers.UnoAIPlayer;
import games.cardgames.uno.unoplayers.UnoHumanPlayer;

import java.util.ArrayList;
import java.util.List;

public class BrainTest {
    public static void main(String[] args) {

        // create deck of cards
        UnoDeck deck = new UnoDeck(UnoEdition.CLASSIC);

        // create card machine to manage cards. pass the deck to machine
        // the machine manages the deck, draw pile, and discard pile
        UnoCardMachine machine = new UnoCardMachine(deck);

        // transfer the deck to draw pile
        machine.transferDeckToDrawPile();

        // create players
        UnoHumanPlayer player1 = new UnoHumanPlayer(new UnoPlayerHandPile());
        UnoAIPlayer player2 = new UnoAIPlayer(new UnoPlayerHandPile(), new UnoGameState());

        // create list of players
        List<CardPlayer<UnoCard>> players = new ArrayList<>(List.of(player1, player2));

        // deal cards to each player by passing a list of players to the machine
        machine.dealCards(7, players);

        // display cards in player1's hand
        player1.getPlayerHand().forEach(System.out::println);
        System.out.println();

        // display cards in player2's hand
        player2.getPlayerHand().forEach(System.out::println);
        System.out.println();

        // draw a card from player1
        // pass an index to get the card from the list
        UnoCard card1 = player1.playCard(0 );

        // display card
        System.out.println(card1);

        // add the card to the discard pile
        machine.addCardToDiscardPile(card1);

        // display cards from players hand again
        System.out.println(player1.getPlayerHand().size());
        

        // AI player plays a card
        UnoCard card2 = player2.playCard();

    }
}
