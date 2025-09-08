package com.josiah.uno.model.cardgame.card;

/*
This abstract generic class represents a card in a card game.
The first generic type S extends Enum<S> means the class type must be an Enum type. The S represents a card Suit.
The second generic type V extends Enum<V> also means the class type must be an Enum type. The V represents a card Value.

More information about S extends Enum<S>:
    - Every Enum in Java is a subclass of Enum<S>.
    - S extends Enum<S> means that whatever class is supplied as E must extend Enum<E>.
    - In other words, every subclass of Deck must supply an Enum type to the generic E.
NOTE:
    This class needed to be generic to flexibly create cards with different suits and values (e.g., Uno
    suits are RED, GREEN, BLUE, and YELLOW. Blackjack suits are Clubs, Diamonds, Hearts ...).
 */

import java.io.Serializable;
import java.util.Objects;

public abstract class Card<S extends Enum<S>, V extends Enum<V>> implements Serializable {

    // Variables to refer to the suit and value.
    private S suit;
    private V value;

    // The constructor receives an enum suit and value, and sets them.
    public Card(S suit, V value) {
        this.suit = suit;
        this.value = value;
    }

    // Get the suit
    public S getSuit() {
        return suit;
    }

    // Get the value
    public V getValue() {
        return value;
    }

    // Get the suit and value as a string
    public String toString() {
        return "Suit: " + suit + " | " + "Value: " + value;
    }

    // Auto-generated equals method
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Card<?, ?> card = (Card<?, ?>) o;
        return Objects.equals(suit, card.suit) && Objects.equals(value, card.value);
    }

    // Auto-generated hash code method
    public int hashCode() {
        return Objects.hash(suit, value);
    }
}
