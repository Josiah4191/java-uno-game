package model.cards.uno;

import model.cards.Pile;

import java.util.Collections;
import java.util.List;

/*
    1. This UnoDrawPile class extends the Pile class and is responsible for managing the draw pile.
    2. This class owns the draw pile.
    3. The fill method fills this draw pile with the cards from whatever list of cards is passed to it.
    4. The shuffle method shuffles the pile of cards.
 */

public class UnoDrawPile extends Pile<UnoCard> {

    public UnoCard drawCard() {
        int index = pile.size() - 1;
        UnoCard card = pile.get(index);
        pile.remove(index);
        return card;
    }

    // This method fills the pile with the list of cards that is passed to it
    protected void fill(List<UnoCard> cards) {
        pile.addAll(cards);
    }

    // This method shuffles the draw pile
    protected void shuffle() {
        Collections.shuffle(pile);
    }

}
