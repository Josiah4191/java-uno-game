package model.cards;

import java.util.Collections;
import java.util.List;
/*
    1. This class represents a deck of cards. It is an abstract generic class.
    2. The first generic type C represents Card objects, and this class contains a list of Card objects.
    3. The second generic type E must be an enum type, and it represents the edition of the deck. UNO has various
       editions. This assumes that all card games implemented in the future will also need to have an edition, even if it's just "classic."
       Different editions require different decks of cards. E.g., Classic UNO has 108 cards. Other variations have 112, and so on.

    More information about E extends Enum<E>
        - Every Enum in Java is a subclass of Enum<E>
        - E extends Enum<E> means that whatever class is supplied as E must extend Enum<E>.
        - In other words, every subclass of Deck must supply an Enum type to the generic E.

    This class needed to be generic to allow other subclasses to pass different card types (UnoCard, BlackJack ...)
    If this class is not generic, then we would have to create List<UnoCard>, but then it wouldn't flexibly work with other card types.
    Otherwise, we would need List<Card<UnoCard>> which looks more confusing, but again, it wouldn't be flexible.
    Either way, we need flexibility with the card type in the list if we want to add other types of card games in the future.
 */

public abstract class Deck <C, E extends Enum<E>> {
    // Create the deck variable to hold a list of Card objects
    protected List<C> deck;
    // Create the edition variable to refer to the edition
    protected E edition;

    // This constructor requires an Enum that represents the game edition, and then sets the edition
    public Deck(E edition) {
        this.edition = edition;
    }

    // This method returns a list of cards in the deck
    public List<C> getDeck() {
        return deck;
    }

    // This method gets the size of the cards in the deck
    public int size() {
        return deck.size();
    }

    // This method gets the edition of the deck of cards
    public E getEdition() {
        return edition;
    }

    // This method shuffles the deck of cards
    public void shuffle() {
        Collections.shuffle(deck);
    }

}
