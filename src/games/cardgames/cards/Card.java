package games.cardgames.cards;
/*
    1. This is a generic class that represents a card in a card game.
    2. The first generic type S extends Enum<S> means the class type must be an Enum type. The S represents a card Suit.
    3. The second generic type V extends Enum<V> also means the class type must be an Enum type. The V represents a card Value.

    More information about E extends Enum<E>
        - Every Enum in Java is a subclass of Enum<E>
        - E extends Enum<E> means that whatever class is supplied as E must extend Enum<E>.
        - In other words, every subclass of Deck must supply an Enum type to the generic E.

    This class needed to be generic to allow the creation of cards with different suits and values. The suits
    in a Uno deck are RED, GREEN, BLUE, YELLOW. Blackjack suits are different (e.g., Clubs, Diamonds, Hearts ...) Blackjack
    also doesn't have Wild Card, SKIP, or REVERSE values.

    All card object belong to a suit and have a value. If another card class is going to subclass this Card class, it will
    need appropriate Enums to represent their suits and values.
 */

public abstract class Card<S extends Enum<S>, V extends Enum<V>> {

        // Create variables to represent the card suit and value
        private S suit;
        private V value;

        // Set the suit and value in the constructor
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
}
