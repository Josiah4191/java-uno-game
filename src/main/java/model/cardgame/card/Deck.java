package model.cardgame.card;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/*
This abstract generic class represents a deck of cards.
The first generic type C specifies the Card type.
The second generic type E specifies the edition of the deck, and it must be an enum type.
All card main.java.games in the future must have editions, even if it's just a classic edition.
Separate editions require a separate deck of cards (e.g., classic UNO has 108 cards, and another might have 112).

More information about E extends Enum<E>:
    - Every Enum in Java is a subclass of Enum<E>.
    - E extends Enum<E> means that whatever class is supplied as E must extend Enum<E>.
    - In other words, every subclass of Deck must supply an Enum type to the generic E.

NOTE:
    This class needed to be generic for subclasses to specify the type of Card and its game edition (e.g., UnoCard, BlackJackCard ...).
    If List<UnoCard> is used, then subclasses cannot store different card types.
 */

public abstract class Deck<C, E extends Enum<E>> implements Serializable {
    // Variable to hold a list of Card objects.
    protected List<C> deck;

    // Variable for the edition.
    protected E edition;

    // The constructor is passed an Enum that represents the game edition, and sets the edition.
    public Deck(E edition) {
        this.edition = edition;
    }

    // Gets the cards in the deck.
    public List<C> getDeck() {
        return deck;
    }

    // Gets the number of cards in the deck.
    public int size() {
        return deck.size();
    }

    // Gets the edition.
    public E getEdition() {
        return edition;
    }

    // Shuffles th deck of cards.
    public void shuffle() {
        Collections.shuffle(deck);
    }

}
