package model.cards.uno;

/*
    This enum contains the values which correspond to the values in a Uno deck.
    Each enum constructor sets its count to match the number of times it should appear in a suit.
    e.g., A RED suit in Uno has only one ZERO, but it has two ONE values.
 */

public enum UnoValue {
    ZERO(1), ONE(2), TWO(2), THREE(2), FOUR(2), FIVE(2), SIX(2), SEVEN(2), EIGHT(2), NINE(2), REVERSE(2), SKIP(2), DRAW_TWO(2), WILD_DRAW_FOUR(4), WILD(4);

    private int count;

    UnoValue(int count) {
        this.count = count;
    }

    // This method returns an integer that represents the number of times that value appears in a suit.
    public int getCount() {
        return count;
    }

    // This method checks if the enum is a wild card.
    public boolean isWild() {
        return this.name().startsWith("WILD");
    }

    // This method checks if the enum is an action card.
    public boolean isAction() {
        switch(this.name()) {
            case "REVERSE", "SKIP", "DRAW_TWO":
                return true;
            default:
                return false;
        }
    }

    // This method checks if the enum is a number card.
    public boolean isNumber() {
        switch(this.name()) {
            case "ZERO", "ONE", "TWO", "THREE", "FOUR", "FIVE", "SIX", "SEVEN", "EIGHT", "NINE":
                return true;
            default:
                return false;
        }
    }

}
