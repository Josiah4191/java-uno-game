package games.cardgames.cards.unocards;

import games.cardgames.cards.Pile;

import java.util.Collections;
import java.util.List;

/*
Team Members: Steve Wareham, Charles Davidson, Josiah Stoltzfus
Date: 3/7/2025
------------------------------------------------------------------------------

This class manages the draw pile for an UNO game. It extends the Pile class and specifies UnoCard for the card type.
 */

public class UnoDrawPile extends Pile<UnoCard> {

    // Receives a list of Card objects and fills its own list of cards with that list.
    protected void fill(List<UnoCard> cards) {
        cardPile.addAll(cards);
    }

    /*
    Returns an UnoCard object from the end of its list of UnoCard objects.
    The drawCard method of the super class Pile is used. The card drawn is removed from the list.
    Refer to Pile for details.
     */
    public UnoCard drawCard() {
        int cardIndex = cardPile.size() - 1;
        return drawCard(cardIndex);
    }

    // Shuffles the list of UnoCard objects.
    protected void shuffle() {
        Collections.shuffle(cardPile);
    }

}
