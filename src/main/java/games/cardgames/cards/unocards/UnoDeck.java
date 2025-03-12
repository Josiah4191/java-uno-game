package games.cardgames.cards.unocards;

/*
Team Members: Steve Wareham, Charles Davidson, Josiah Stoltzfus
Date: 3/7/2025
------------------------------------------------------------------------------

This class represents a deck of Uno cards. It extends the Deck class and specifies UnoCard for the Card type and
UnoEdition for the Enum type.

NOTE:
    For more information about UnoDeckFactory, refer to UnoDeckFactory class.
 */

import games.cardgames.cards.Deck;

public class UnoDeck extends Deck<UnoCard, UnoEdition> {
    /*
    The constructor receives the edition, sets the edition, and uses the UnoDeckFactory class to create the deck.
    The constructor also calls the shuffle() method from deck to automatically shuffle the Card objects in the list.
     */
    public UnoDeck(UnoEdition edition) {
        super(edition);
        deck = UnoDeckFactory.createDeck(edition);
        shuffle();
    }

    /*
    Receives an UnoDrawPile object and transfers the Card objects from the deck to the draw pile's list of cards.
    The deck.clear() method clears its cards after transfer.
     */
    protected void transferUnoDeckToDrawPile(UnoDrawPile drawPile) {
        drawPile.fill(deck);
        deck.clear();
    }
}

































/*
    Open/Closed Principle (O in SOLID)
        "Software should be open for extension but closed for modification."
 */

    /*
    @Override
    protected List<UnoCard> createDeck(UnoEdition edition) {
        switch (edition) {
            case UnoEdition.CLASSIC:
                return UnoDeckFactory.classicDeck();
            default:
                return null;
        }
    }
     */