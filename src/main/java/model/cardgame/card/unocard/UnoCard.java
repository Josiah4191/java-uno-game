package model.cardgame.card.unocard;

import model.cardgame.card.Card;

/*
Team Members: Steve Wareham, Charles Davidson, Josiah Stoltzfus
Date: 3/7/2025
------------------------------------------------------------------------------

This class extends the Card class and represents an Uno card. The specified type for Card is set to
UnoSuit and UnoValue enum types.

The constructor accepts an UnoSuit and an UnoValue enum.

For details about instance variables and instance methods, refer to Card class.
 */

public class UnoCard extends Card<UnoSuit, UnoValue> {
    // Access is protected so additional cards cannot be created after a deck has been made.
    public UnoCard(UnoSuit suit, UnoValue value) {
        super(suit, value);
    }

}
