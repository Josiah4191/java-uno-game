package model.cardgame.card.unocard;

import model.cardgame.card.Card;

/*
This class extends the Card class and represents an Uno card. The specified type for Card is set to
UnoSuit and UnoValue enum types.

The constructor accepts an UnoSuit and an UnoValue enum.

For details about instance variables and instance methods, refer to Card class.
 */

public class UnoCard extends Card<UnoSuit, UnoValue> {
    public UnoCard(UnoSuit suit, UnoValue value) {
        super(suit, value);
    }

}
