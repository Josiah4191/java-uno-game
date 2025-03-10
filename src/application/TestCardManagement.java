package application;

import games.Difficulty;
import games.cardgames.cards.unocards.UnoCard;
import games.cardgames.cards.unocards.UnoCardTheme;
import games.cardgames.cards.unocards.UnoEdition;
import games.cardgames.unogame.UnoGameManager;
import games.players.cardplayers.unoplayers.UnoPlayer;

public class TestCardManagement {
    public static void main(String[] args) {
        // initialize the game
        UnoGameManager manager = new UnoGameManager(UnoEdition.CLASSIC, UnoCardTheme.CLASSIC, Difficulty.EASY);

        /*
        Consider removing getDeck() because we never use it. The deck is automatically swapped to the draw pile, and then
        we only need to manage draw pile and discard pile. When the draw pile runs out, the discard pile automatically transfers
        all its cards to the draw pile, and then the draw pile automatically shuffles. After the discard pile transfers the cards
        to the draw pile, and the draw pile has been shuffled, the drawCard method is called again and then a new card is placed
        in the discard pile, which becomes the last played card. Make sure this works properly.
        */

        // get the deck
        var deck = manager.getDeck();
        System.out.println("Deck: " + deck.size() + " cards");
        deck.forEach(System.out::println);
        System.out.println();

        // get the draw pile
        var drawPile = manager.getDrawPile();
        System.out.println("Draw pile: " + drawPile.size() + " cards");
        drawPile.forEach(System.out::println);
        System.out.println();

        // get the discard pile
        var discardPile = manager.getDiscardPile();
        System.out.println("Discard pile: " + discardPile.size() + " cards");
        discardPile.forEach(System.out::println);
        System.out.println();

        // draw a card from the draw pile
        UnoCard card1 = manager.drawCardFromDrawPile();
        System.out.println("Draw a card from draw pile: " + card1);
        System.out.println();

        // output the size of the draw pile after drawing card to verify a card was removed
        System.out.println("Draw pile size after drawing a card: " + drawPile.size());
        System.out.println();

        // output the discard pile again after drawing a card
        System.out.println("Discard pile after drawing a card: " + discardPile.size());
        discardPile.forEach(System.out::println);
        System.out.println();

        // get the card that is in the discard pile
        UnoCard card2 = discardPile.get(0);

        // verify that the card in the discard pile is the same object as the card that was drawn from earlier
        System.out.println("They are the same object: " + (card1 == card2));

        // get a player from the list of players
        // this throws an error because no players have been created yet.
        // we need to handle this error
        // UnoPlayer player1 = manager.getPlayer(0);
        // player1.addCard(card1);

        // create a player
        UnoPlayer player1 = new UnoPlayer();
        player1.setName("Josiah");

        // add the player to the game
        manager.addPlayer(player1);

        // get the players hand of cards
        var cards = player1.getPlayerHand();

        // output the cards in the players hand
        System.out.println("Player cards: " + cards.size());
        cards.forEach(System.out::println);
        System.out.println();

        // add the card that we drew from the discard pile to the player's cards
        player1.addCard(card1);

        // output the cards in the players hand
        System.out.println("Player cards: " + cards.size());
        cards.forEach(System.out::println);
        System.out.println();

        // see what happens if we add card1 to the hand again
        // this throws a DuplicateCardException because both cards are the same
        player1.addCard(card1);




    }
}
