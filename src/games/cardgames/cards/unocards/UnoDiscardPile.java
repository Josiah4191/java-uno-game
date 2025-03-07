package games.cardgames.cards.unocards;

import games.cardgames.cards.Pile;

/*
Last edited: Josiah Stoltzfus
Date: 3/7/2025
    - Initial version - First time editing. Future edits and comments will be noted here. Please
    include your name and date.

Author: Josiah Stoltzfus
Date: 3/7/2025
------------------------------------------------------------------------------

This class manages the discard pile for an UNO game. It extends the Pile class and specifies UnoCard for the card type.
 */

public class UnoDiscardPile extends Pile<UnoCard> {
    /*
    Returns an UnoCard object that represents the last card that was played. The last UnoCard object in the list
    is retrieved. Does not remove the card from its list.
     */
    public UnoCard getLastPlayedCard() {
        int index = cardPile.size() - 1;
        return cardPile.get(index);
    }
    /*
    Receives an UnoDrawPile object, and transfers all UnoCard objects in the list to the drawPile's list, then the
    cards are removed from the list.
     */
    protected void transferCardsToDrawPile(UnoDrawPile drawPile) {
        drawPile.fill(cardPile);
        cardPile.clear();
    }
}
