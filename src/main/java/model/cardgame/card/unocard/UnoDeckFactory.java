package model.cardgame.card.unocard;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
Team Members: Steve Wareham, Charles Davidson, Josiah Stoltzfus
Date: 3/7/2025
------------------------------------------------------------------------------

This class is responsible for creating a deck of Uno cards.
It flexibly allows adding different decks of UNO for other editions.
Only the classic UNO deck has been added.

Steps for adding another UNO edition:
    - Add new edition enum to UnoEdition enum type.
    - Add new card value enum to UnoValue enum type.
    - Create methods in this class to assemble a deck for the new edition.
    - Add the edition to the switch statement in the createDeck() method to create the appropriate deck.
 */

public class UnoDeckFactory {
    /*
    Creates and returns the appropriate list of cards based on the edition.
    This method is called from the UnoDeck class.
     */
    protected static List<UnoCard> createDeck(UnoEdition edition) {
        switch (edition) {
            case CLASSIC:
                return classicDeck();
            default:
                return null;
        }
    }

    /*
    Creates a classic Uno deck consisting of 108 cards:
        - 0(x1), 1-9 (x2), SKIP(x2), REVERSE(x2), DRAW TWO(x2), WILD(x4), WILD DRAW FOUR(x4)
    Assembles the individual suits to form the complete deck (e.g., if the selected edition is classic,
    a classic deck is created).
     */
    private static List<UnoCard> classicDeck() {
        ArrayList<UnoCard> deck = new ArrayList<>();
        deck.addAll(createSuit(UnoSuit.RED));
        deck.addAll(createSuit(UnoSuit.BLUE));
        deck.addAll(createSuit(UnoSuit.YELLOW));
        deck.addAll(createSuit(UnoSuit.GREEN));
        deck.addAll(createSuit(UnoSuit.WILD));
        return deck;
    }

    /*
    Receives a Suit enum (RED, GREEN, BLUE, YELLOW, WILD).
    Returns a list of Card objects for a suit of cards which contains each value and the number of times that
    value appears in the suit (e.g., createSuit(UnoSuit.RED) returns a list of red UNO Card objects).
     */
    private static List<UnoCard> createSuit(UnoSuit suit) {

        // Variable to hold a list of all values in the suit.
        ArrayList<UnoValue> values = new ArrayList<>();
        // Variable to hold a list of Card objects to be returned.
        ArrayList<UnoCard> suits = new ArrayList<>();

        // If suit is WILD, only add values for Wild Cards.
        if (suit == UnoSuit.WILD) {
            var wildValues = createWildValues();
            values.addAll(wildValues);
            // If suit is not WILD, add the values to the list of values.
        } else {
            var numberValues = createNumberValues();
            var actionValues = createActionValues();
            values.addAll(numberValues);
            values.addAll(actionValues);
        }
        /*
        Loop through list of values and create an UnoCard with the given suit and value.
        Add the UnoCard object to the list of UnoCards.
         */
        for (UnoValue value : values) {
            suits.add(new UnoCard(suit, value));
        }
        // Return the list of UnoCards.
        return suits;
    }
    /*
    The methods createNumberValues, createActionValues, and createWildValues are responsible for two things:
        1. Filter through the list of UnoValue enums and get only the values for that type of card.
        2. Return the filtered list of values with the correct number of values that belong in a Uno card
        deck (e.g., createNumberValues -> 0, 1, 1, 2, 2, 3, 3, 4, 4, ... 9, 9).
            - createWildValues -> WILD, WILD, WILD, WILD ...
            - createActionValues -> SKIP, SKIP, REVERSE, REVERSE ...
    Brief explanation of streams:
        - The collections in Java have a stream() method, which returns a stream.
        - (ArrayList, LinkedList, HashMap ...)
        - The chain of method calls is called the stream pipeline.
        - (.filter().flatMap().collect() ...)
        - The final method at the end of the pipeline is called a terminator (or terminal operation).
        - .collect() is an example of a terminator
        - I think its common for the terminator to just return the final result of the collection, which is all I do.
        - The methods in between (.filter() ...) are intermediate operations.
        - The intermediate operations do certain things to the list depending on what you want to do.
        - The intermediate operations accept specific functional interfaces for you to do certain actions to the elements in the stream,
        which are the items in the list.
        - In the code below, I used lambda expressions to implement the interfaces.
    NOTE:
        The UnoValue enums are associated with a number which corresponds to the number of times that they should be in a suit of cards
        (e.g., A SKIP is associated with 2, because there are 2 skips in a suit of cards)
        This is used stream pipeline to determine how many times that card should be duplicated (limit(value.getCount)).
        The UnoValue enum also has methods to check if the enum is a wild card, number card, or action card, and they're used for filtering.
     */

    private static List<UnoValue> createNumberValues() {
        // Variable to hold list of number values
        ArrayList<UnoValue> numberValues = new ArrayList<>(List.of(UnoValue.values()));
        /*
        Filter list by checking if the value is a number card value.
        Generate a new stream that contains the values from the filtered list.
        Limit the value for the new items that are generated in the stream to number of times that value should appear
        in a suit.
        Collect the stream to be returned as a list.
         */
        return numberValues.stream()
                .filter(UnoValue::isNumber)
                .flatMap(value -> Stream.generate(() -> value).limit(value.getCount()))
                .collect(Collectors.toList());
    }

    private static List<UnoValue> createActionValues() {
        // Variable to hold list of action card values.
        List<UnoValue> actionValues = new ArrayList<>(List.of(UnoValue.values()));
        /*
        Filter list by checking if the value is an action card value.
        Generate a new stream that contains the values from the filtered list.
        Limit the value for the new items that are generated in the stream to number of times that value should appear
        in a suit.
        Collect the stream to be returned as a list.
         */
        return actionValues.stream()
                .filter(UnoValue::isAction)
                .flatMap(value -> Stream.generate(() -> value).limit(value.getCount()))
                .collect(Collectors.toList());
    }

    private static List<UnoValue> createWildValues() {
        // Variable to hold list of wild card values.
        List<UnoValue> wildValues = new ArrayList<>(List.of(UnoValue.values()));
        /*
        Filter list by checking if the value is a wild card value.
        Generate a new stream that contains the values from the filtered list.
        Limit the value for the new items that are generated in the stream to number of times that value should appear
        in a suit.
        Collect the stream to be returned as a list.
         */
        return wildValues.stream()
                .filter(UnoValue::isWild)
                .flatMap(value -> Stream.generate(() -> value).limit(value.getCount()))
                .collect(Collectors.toList());
    }
}

