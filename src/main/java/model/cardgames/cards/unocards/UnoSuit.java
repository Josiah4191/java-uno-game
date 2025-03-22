package model.cardgames.cards.unocards;

/*
Team Members: Steve Wareham, Charles Davidson, Josiah Stoltzfus
Date: 3/7/2025
------------------------------------------------------------------------------

This enum contains the suits for an Uno card deck.
 */

import java.io.Serializable;

public enum UnoSuit implements Serializable {
    RED("red"), GREEN("limegreen"), BLUE("deepskyblue"), YELLOW("yellow"), WILD("white"), GENERAL("white");

    private String color;

    UnoSuit(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }
}


