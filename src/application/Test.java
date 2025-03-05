package application;

import model.cards.uno.*;
import model.players.unoplayers.UnoAIPlayer;
import model.players.unoplayers.UnoHumanPlayer;

import java.util.List;

public class Test {
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
        UnoAIPlayer player2 = new UnoAIPlayer(new UnoPlayerHandPile());

        // deal cards to each player by passing a list of players to the machine
        machine.dealCards(7, List.of(player1, player2));

        // display cards in player1's hand
        player1.getPlayerHand().forEach(System.out::println);
        System.out.println();

        // display cards in player2's hand
        player2.getPlayerHand().forEach(System.out::println);
        System.out.println();

        // draw a card from player1
        // pass an index to get the card from the list
        UnoCard card = player1.drawCard(0 );

        // display card
        System.out.println(card);

        // add the card to the discard pile
        machine.addCardToDiscardPile(card);

        // display cards from players hand again
        System.out.println(player1.getPlayerHand().size());



    }

}
