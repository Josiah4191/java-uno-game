package games.cardgames.cards.unocards;
/*
    1. This class extends the Deck class.
    2. In the class declaration, it sets the generic type to UnoCard, so that it will
       store a list of UnoCards.
    3. The UnoEdition is an Enum that contains the editions of Uno.
    4. You must pass the edition of Uno to the constructor so that it knows which variation of Uno it will be.
    5. The deck, which is the list of cards, is assigned in the constructor, and it is created by the UnoDeckFactory.
       class. The UnoDeckFactory is responsible for creating Uno decks. The UnoDeckFactory class returns a list of UnoCard.
       objects. The UnoDeckFactory needs to know which edition of cards in order to create the correct one.
    6. The shuffle method shuffles the deck after it's created.
 */

import games.cardgames.cards.Deck;

public class UnoDeck extends Deck<UnoCard, UnoEdition> {

    public UnoDeck(UnoEdition edition) {
        super(edition);
        deck = UnoDeckFactory.createDeck(edition);
        shuffle();
    }

    // This method transfer the deck to the draw pile
    protected void transferDeckToDrawPile(UnoDrawPile pile) {
        pile.fill(deck);
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