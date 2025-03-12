package test;

import games.Difficulty;
import games.cardgames.cards.unocards.UnoCard;
import games.cardgames.cards.unocards.UnoEdition;
import games.cardgames.unogame.UnoGameManager;
import games.cardgames.unogame.UnoGameState;
import games.players.cardplayers.unoplayers.UnoPlayer;
import games.players.cardplayers.unoplayers.UnoPlayerAI;

public class TestCardManagement {
    public static void main(String[] args) {
        // initialize the game
        UnoGameManager manager = new UnoGameManager(UnoEdition.CLASSIC, Difficulty.EASY);
        UnoGameState gameState = manager.getGameState();

        /*
        Consider removing getDeck() because we never use it. The deck is automatically swapped to the draw pile, and then
        we only need to manage draw pile and discard pile. When the draw pile runs out, the discard pile automatically transfers
        all its cards to the draw pile, and then the draw pile automatically shuffles. After the discard pile transfers the cards
        to the draw pile, and the draw pile has been shuffled, the drawCard method is called again and then a new card is placed
        in the discard pile, which becomes the last played card. Make sure this works properly.
        */

        // get the deck
        var deck = gameState.getDeck();
        System.out.println("Deck: " + deck.size() + " cards");
        deck.forEach(System.out::println);
        System.out.println();

        // get the draw pile
        var drawPile = gameState.getDrawPile();
        System.out.println("Draw pile: " + drawPile.size() + " cards");
        drawPile.forEach(System.out::println);
        System.out.println();

        // get the discard pile
        var discardPile = gameState.getDiscardPile();
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
        UnoCard card2 = discardPile.getFirst();

        // verify that the card in the discard pile is the same object as the card that was drawn from earlier
        System.out.println("They are the same object: " + (card1 == card2));

        // get a player from the list of players
        // this throws an error because no players have been created yet.
        // we need to handle this error
        // UnoPlayer player1 = manager.getPlayer(0);
        // player1.addCard(card1);

        // we also need to handle when playing cards when the player has no cards
        // we need to handle getting players when there are no players
        // handle drawing cards when there are no cards
        // handle transferring cards when there are no cards

        /*
        should we figure out a way to keep track of where each card is at all times?
        think about this: right now, we can't add the same card to a pile. an exception
        will be thrown. but what if we have 'card1' in a pile, and then try to add 'card1'
        to another pile? we would need to stop that from happening as well. our cards objects
        need to pass some sort of verification process in order to be added to a pile, I think
        instead of doing really loops through other piles to see if the card exists, maybe
        we can store an on/off switch in cards. if we try to add a card, we check their on/off
        switch. if they are on, then we can't add it to the pile. if they are off, we can add it to
        the pile. when we draw the card, we turn the switch off before adding it to another pile.
        this should also solve the problem of trying to add a duplicate card. because if we try to add
        it to the same pile, then the switch would be on, and the exception will be thrown.
        */



        // create a player
        UnoPlayer player1 = new UnoPlayer();
        player1.setName("Josiah");
        UnoPlayer robot1 = new UnoPlayerAI(manager.getGameState());
        robot1.setName("Robot 1");

        // add the player to the game
        manager.addPlayer(player1);
        manager.addPlayer(robot1);

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
        //player1.addCard(card1);

        // see what happens if we try to add card1 to a player when another player already has
        // that card
        robot1.addCard(card1);

        // see what happens when we try to add card1 to a pile if it already exists in another
        // pile (player hand, draw pile, discard pile)
        




    }
}
