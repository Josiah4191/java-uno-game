package games.cardgames.uno.unocards;

import games.cardgames.cards.Pile;

import java.util.Collections;
import java.util.List;

/*
    1. This UnoDrawPile class extends the Pile class and is responsible for managing the draw pile.
    2. This class owns the draw pile.
    3. The fill method fills this draw pile with the cards from whatever list of cards is passed to it.
    4. The shuffle method shuffles the pile of cards.
 */

public class UnoDrawPile extends Pile<UnoCard> {

    // This method fills the pile with the list of cards that is passed to it
    protected void fill(List<UnoCard> cards) {
        cardPile.addAll(cards);
    }

    public UnoCard drawCard() {
        int index = cardPile.size() - 1;
        UnoCard card = cardPile.get(index);
        cardPile.remove(index);
        return card;
    }

    // This method shuffles the draw pile
    protected void shuffle() {
        Collections.shuffle(cardPile);
    }

}
