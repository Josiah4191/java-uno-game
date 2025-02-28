package model.cards.uno;

import java.util.Collections;
import java.util.List;

public class UnoCardMachine {
    /*
        1. This class is responsible for controlling the flow of cards between the deck, draw pile, and discard pile.
        2. When we create this UnoCardMachine, we must pass it a UnoDeck object, which holds a deck of Uno cards.
        3. This UnoCardMachine class combines the UnoDrawPile, UnoDiscardPile, and UnoDeck so that they can shift cards between each list of cards.
            - UnoDrawPile class
                - Owns the draw pile list of cards
                - Has methods for managing the draw pile
            - UnoDiscardPile class
                - Owns the discard pile list of cards
                - Has methods for managing the discard pile
            - UnoDeck class
                - Creates the initial deck of Uno cards
                - Owns the deck of Uno cards
         4. This UnoCardMachine class creates the drawPile, discardPile, and is passed a deck. Then we use these methods, which will
            call the methods of UnoDrawPile, UnoDiscardPile, and UnoDeck.

         Note:
             The get methods in this class will get the list of cards in the drawPile, discardPile, and deck, but they are read-only. This way,
             we can't alter the lists. This is for encapsulation. The UnoDrawPile and UnoDiscardPile are responsible for making changes to their
             own lists of cards.

             The other methods are a higher level than UnoDrawPile and UnoDiscardPile, and they should be self-explanatory.
             To see under the hood, refer to Pile, UnoDrawPile, and UnoDiscardPile.
     */

    private UnoDrawPile drawPile = new UnoDrawPile();
    private UnoDiscardPile discardPile = new UnoDiscardPile();
    private UnoDeck deck;

    public UnoCardMachine(UnoDeck deck) {
        this.deck = deck;
    }

    public UnoCard drawCard() {

        if (drawPile.isEmpty()) {
            transferDiscardPileToDrawPile();
            shuffleDrawPile();
        }
        return drawPile.drawCard();
    }

    public UnoCard getLastPlayedCard() {
        return discardPile.getLastPlayedCard();
    }

    public void shuffleDrawPile() {
        drawPile.shuffle();
    }

    public void shuffleDeck() {
        deck.shuffle();
    }

    public void transferDeckToDrawPile() {
        deck.transferDeckToDrawPile(drawPile);
    }

    public void addCardToDiscardPile(UnoCard card) {
        discardPile.addCard(card);
    }

    public void transferDiscardPileToDrawPile() {
        discardPile.transferCardsToDrawPile(drawPile);
    }

    public List<UnoCard> getDrawPile() {
        return Collections.unmodifiableList(drawPile.getPile());
    }

    public List<UnoCard> getDiscardPile() {
        return Collections.unmodifiableList(discardPile.getPile());
    }

    public List<UnoCard> getDeck() {
        return Collections.unmodifiableList(deck.getDeck());
    }


    /*
    public void switchEdition(UnoEdition edition) {
        drawPile.getPile().clear();
        discardPile.getPile().clear();
        deck = new UnoDeck(edition);
    }
     */

    /*

    What will the UnoCardMachine do?

        creates the UnoDeck deck of cards
        creates the Draw Pile
        creates the Discard Pile

        - in general, this class should have methods that can be called by other parts of the program to manage the 3 piles of cards

            - when the game starts, the user chooses the game edition
            - when the unocardmachine is created, it will create the unodeck that matches the game edition
            - then the drawpile and discardpile are created

            - the cards need to be pulled from the initial deck of cards and then handed to all the players
                - add a method that accepts a list of players and then adds a certain number of cards from the deck to each player's hand. a player's hand will be a list of UnoCards

            - the card machine will be the one to draw a card from the draw pile
                - add a drawCard method so that the machine draws the card

            - the card machine will be the one to get the last played card from the discard pile
                - add a getLastPlayedCard() method so that the machine can retrieve the last played card

            - the card machine will be the one to shuffle the decks
                - add a shuffle() method to internally shuffle the drawPile

            - should the card machine need a reset method to reset the state of its cards (either create a different deck or reset the deck, empty the discard pile, empty the draw pile)
                - no. the game manager should be responsible for resetting the game and creating a new card machine. it's not a good idea to try to reconstruct the deck inside of this card machine.
                      (e.g. player cards would need to be added back into the deck)

            - should a method be created to allow the deck to be changed to a new deck in case the user wants to change the uno edition?
                - should a new card machine need to be created in the event that a new edition is created? or can we reuse the same card machine?
                    - a new card machine will need to be created when the game resets


        drawCards
        viewCard
        transferCards
        shuffleDeck
        shuffleDrawPile
     */


}
