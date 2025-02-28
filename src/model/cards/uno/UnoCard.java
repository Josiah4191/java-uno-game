package model.cards.uno;

import model.cards.Card;
/*
    1. This class extends the Card class and represents a Uno card
    2. In the class declaration, it sets the Enum type to UnoSuit and UnoValue, so that the creation of this
       object works with suits and values corresponding to Uno.
    3. When a card is created, you must pass a suit and a value to the constructor.
        e.g., UnoCard card = new UnoCard(UnoSuit.RED, UnoValue.ONE);
    4. You can get information about the card.
        e.g., card.getSuit();
              card.getValue();
 */

public class UnoCard extends Card<UnoSuit, UnoValue> {
    // The constructor is protected so we don't have people adding cards to the game once the deck has been created
    protected UnoCard(UnoSuit suit, UnoValue value) {
        super(suit, value);
    }
}
