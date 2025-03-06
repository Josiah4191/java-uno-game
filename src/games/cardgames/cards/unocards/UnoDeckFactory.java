package games.cardgames.cards.unocards;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
        This class is responsible for creating a deck of Uno cards. It is designed in such a way that we can add multiple variations of
        Uno. Right now, there is only a classic deck.

        If we want to add another edition of Uno, the only thing we have to do is:
            1. Add the edition to the UnoEdition enum
            2. Create another method in this class that assembles a deck for that edition
            3. Add the edition to the switch statement in the createDeck() method to create the proper deck

            Additionally, if we added a new edition, then it's likely we will need to add those cards to the UnoValue enum. It also
            is flexible to allow the addition of new cards. Refer to the UnoValue.java file for more information about it.

 */

public class UnoDeckFactory {

    // Create the deck variable to contain the UnoCards
    private static ArrayList<UnoCard> deck;

    // This method creates the appropriate deck based on the edition.
    // It is called from the UnoDeck class
    protected static List<UnoCard> createDeck(UnoEdition edition) {
        switch (edition) {
            case UnoEdition.CLASSIC:
                return classicDeck();
            default:
                return null;
        }
    }

    /*
        This method creates the classic Uno deck consisting of 108 cards:
            - 0(x1), 1-9 (x2), SKIP(x2), REVERSE(x2), DRAW TWO(x2), WILD(x4), WILD DRAW FOUR(x4)

        Basically, this method is assembling the individual suits to form the complete deck.
        If the selected edition is classic, then this method will be called to create the classic deck.
     */
    private static List<UnoCard> classicDeck() {
        deck = new ArrayList<>();
        deck.addAll(createSuit(UnoSuit.RED));
        deck.addAll(createSuit(UnoSuit.BLUE));
        deck.addAll(createSuit(UnoSuit.YELLOW));
        deck.addAll(createSuit(UnoSuit.GREEN));
        deck.addAll(createSuit(UnoSuit.WILD));

        return deck;
    }

    /*
        This method creates a suit of cards which contains the proper values and the proper number of duplicates for those
        values.

        This method is passed a suit (RED, GREEN, BLUE, YELLOW, WILD), and depending on which suit is provided, it will return
        a list of UnoCards that are of that suit. E.g., if createSuit(UnoSuit.RED), then it will return all the RED Uno cards
        that belong in the complete deck.
     */
    private static List<UnoCard> createSuit(UnoSuit suit) {

        // The values variable is used to collect all the numberValues, actionValues, and wildCardValues.
        ArrayList<UnoValue> values = new ArrayList<>();
        // The suits variable is used to hold a list of UnoCards and will be returned.
        ArrayList<UnoCard> suits = new ArrayList<>();

        // If the suit is WILD, then we only want those values (Wild Card and Wild Draw Four).
        if (suit == UnoSuit.WILD) {
            var wildValues = createWildValues();
            values.addAll(wildValues);
        // Otherwise, if the suit is NOT WILD, then we want to add all the numberValues and actionValues to the list of values.
        } else {
            var numberValues = createNumberValues();
            var actionValues = createActionValues();
            values.addAll(numberValues);
            values.addAll(actionValues);
        }

        // Loop through the list of values and create an UnoCard for the given suit for every card value.
        // Assign that UnoCard to the list of UnoCards.
        for (UnoValue value : values) {
            suits.add(new UnoCard(suit, value));
        }

        // return the suit of UnoCards
        return suits;
    }

    /*
        The createNumberValues, createActionValues, and createWildValues are responsible for two things:
            1. Filter through the list of UnoValue enums and get only the values for that type of card
            2. Return the filtered list of values with the correct number of values that belong in a Uno card deck
                - e.g., createNumberValues -> 0, 1, 1, 2, 2, 3, 3, 4, 4, ... 9, 9
                        createWildValues -> WILD, WILD, WILD, WILD ...
                        createActionValues -> SKIP, SKIP, REVERSE, REVERSE ...

         All the streams work similarly so I will explain them here: (This is my best explanation without trying to do a research paper).
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
     */

    // Note: The UnoValue enums are associated with a number which corresponds to the number of times that they should be in a suit of cards
    // (e.g., A SKIP is associated with 2, because there are 2 skips in a suit of cards)
    // This is used stream pipeline to determine how many times that card should be duplicated (limit(value.getCount)).
    // The UnoValue enum also has methods to check if the enum is a wild card, number card, or action card, and they're used for filtering.

    private static List<UnoValue> createNumberValues() {
        // Create variable to hold a list of number values
        ArrayList<UnoValue> numberValues = new ArrayList<>(List.of(UnoValue.values()));

        return numberValues.stream()
                .filter(UnoValue::isNumber) // filter the list by check if the value is a number card value
                .flatMap(value -> Stream.generate(()-> value).limit(value.getCount())) // generate a new stream that contains the values from the filtered list
                .collect(Collectors.toList()); // collect the list to be returned
    }

    private static List<UnoValue> createActionValues() {
        // Create variable to hold a list of action values
        List<UnoValue> actionValues = new ArrayList<>(List.of(UnoValue.values()));

        return actionValues.stream()
                .filter(UnoValue::isAction) // filter the list and check if the value is an action card value
                .flatMap(value -> Stream.generate(() -> value).limit(value.getCount())) // generate a new stream that contains the values from the filtered list
                .collect(Collectors.toList()); // collect the list to be returned
    }

    private static List<UnoValue> createWildValues() {
        // Create variable to hold a list of wild values
        List<UnoValue> wildValues = new ArrayList<>(List.of(UnoValue.values()));

        return wildValues.stream()
                .filter(UnoValue::isWild) // filter the list and check if the value is a wild card value
                .flatMap(value -> Stream.generate(() -> value).limit(value.getCount())) // generate a new stream that contains the values from the filtered list
                .collect(Collectors.toList()); // collect the list to be returned
    }
}

