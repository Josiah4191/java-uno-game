package games.cardgames.unogame;

/*
Team Members: Steve Wareham, Charles Davidson, Josiah Stoltzfus
Author: Josiah Stoltzfus
Date: 3/8/2025
 */

public enum PlayDirection {
    FORWARD(), REVERSE();


    public boolean isForward() {
        return this == FORWARD;
    }

    public boolean isReverse() {
        return this == REVERSE;
    }


}
