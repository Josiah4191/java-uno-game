package games.cardgames.cards.unocards;

/*
Team Members: Steve Wareham, Charles Davidson, Josiah Stoltzfus
Date: 3/7/2025
------------------------------------------------------------------------------

This enum contains values which correspond to card values in an Uno deck.
Each enum constructor sets its count to match the number of times it should appear in a
suit (e.g., A RED suit in Uno has one ZERO, but it has two ONE values).
 */

public enum UnoValue {
    ZERO(1), ONE(2), TWO(2), THREE(2), FOUR(2), FIVE(2), SIX(2), SEVEN(2), EIGHT(2), NINE(2), REVERSE(2), SKIP(2), DRAW_TWO(2), WILD_DRAW_FOUR(4), DRAW_TW0_STACK(1), WILD_DRAW_FOUR_STACK(1), WILD(4), DECK(1), LOGO(1);

    private int count;

    // The constructor for each enum value sets the number for how many times it appears in a suit.
    UnoValue(int count) {
        this.count = count;
    }

    // Get the number of times this enum value appears in a suit.
    public int getCount() {
        return count;
    }

    // Receives an enum value and checks if it is a WILD card.
    public static boolean isWild(UnoValue value) {
        return value.name().startsWith("WILD");
    }

    // Receives an enum value and checks if it is an ACTION card.
    public static boolean isAction(UnoValue value) {
        return switch (value) {
            case UnoValue.REVERSE, UnoValue.SKIP, UnoValue.DRAW_TWO -> true;
            default -> false;
        };
    }

    // Receives an enum value and checks if it is a NUMBER card.
    public static boolean isNumber(UnoValue value) {
        return switch (value) {
            case UnoValue.ZERO, UnoValue.ONE, UnoValue.TWO, UnoValue.THREE, UnoValue.FOUR, UnoValue.FIVE, UnoValue.SIX,
                 UnoValue.SEVEN, UnoValue.EIGHT, UnoValue.NINE -> true;
            default -> false;
        };
    }

}
