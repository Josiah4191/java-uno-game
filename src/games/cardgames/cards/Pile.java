package games.cardgames.cards;

import java.util.ArrayList;
import java.util.List;

/*
Team Members: Steve Wareham, Charles Davidson, Josiah Stoltzfus
Author: Josiah Stoltzfus
Date: 3/7/2025
------------------------------------------------------------------------------

This abstract generic class is responsible for managing a list of cards, which represents a pile of
cards (e.g., draw pile, discard pile).

NOTE:
    This class needed to be generic for subclasses to specify the type of Card (e.g., UnoCard, BlackJackCard ...).
    If List<UnoCard> is used, then subclasses cannot store different card types.
 */

public abstract class Pile<C> {

    // Create variable to hold empty list of cards.
    protected List<C> cardPile = new ArrayList<>();

    // Get the pile of cards.
    public List<C> getCardPile() {
        return cardPile;
    }

    public void addCard(C card) {
        cardPile.add(card);
    }

    // Get the number of cards in the pile.
    public int size() {
        return cardPile.size();
    }

    // Gets a card from the pile. Accepts an integer for the index of the card that needs to be removed.
    public C drawCard(int cardIndex) {
        C card = cardPile.get(cardIndex);
        cardPile.remove(cardIndex);
        return card;
    }

    // Check if pile is empty.
    public boolean isEmpty() {
        return cardPile.isEmpty();
    }
}

/*
    Note to self: (Josiah)
        - Consider changing generic type to extend Card<S, V> to force C to be a Card object. This can also apply
        to the Deck class.
        - Consider Pile extend Deck, or Deck extend pile. It seems like there are similarities between
        them.
 */