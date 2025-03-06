package games.cardgames.cards;

import java.util.ArrayList;
import java.util.List;

/*
    This class is similar to Deck. It is an abstract generic class that is meant to contain a list of Card objects.
    This class is responsible for owning different types of piles of cards, like draw piles and discard piles.
    This class can be extended for other card games that might have different rules or operations for various card
    piles.
 */

public abstract class Pile<C> {

    // Create variable to hold empty list of cards.
    protected List<C> cardPile = new ArrayList<>();

    // Get the pile of cards
    public List<C> getCardPile() {
        return cardPile;
    }

    public void addCard(C card) {
        cardPile.add(card);
    }

    // Get how many cards are in the pile
    public int size() {
        return cardPile.size();
    }

    public C drawCard(int index) {
        C card = cardPile.get(index);
        cardPile.remove(index);
        return card;
    }

    // Check if the pile of cards is empty
    public boolean isEmpty() {
        return cardPile.isEmpty();
    }

}
