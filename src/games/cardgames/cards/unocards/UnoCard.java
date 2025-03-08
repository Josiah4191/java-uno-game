package games.cardgames.cards.unocards;

import games.cardgames.cards.Card;
/*
Last edited: Josiah Stoltzfus
Date: 3/7/2025
    - Initial version - First time editing. Future edits and comments will be noted here. Please
    include your name and date.

Author: Josiah Stoltzfus
Date: 3/7/2025
------------------------------------------------------------------------------

This class extends the Card class and represents an Uno card. The specified type for Card is set to
UnoSuit and UnoValue enum types.

The constructor accepts an UnoSuit and an UnoValue enum.

For details about instance variables and instance methods, refer to Card class.
 */

public class UnoCard extends Card<UnoSuit, UnoValue> {
    // Access is protected so additional cards cannot be created after a deck has been made.
    protected UnoCard(UnoSuit suit, UnoValue value) {
        super(suit, value);
    }

}
