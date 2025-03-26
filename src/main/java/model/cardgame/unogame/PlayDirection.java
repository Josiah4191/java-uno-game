package model.cardgame.unogame;

/*
Team Members: Steve Wareham, Charles Davidson, Josiah Stoltzfus
Date: 3/8/2025
 */

import java.io.Serializable;

public enum PlayDirection implements Serializable {
    FORWARD(), REVERSE();

    public boolean isForward() {
        return this == FORWARD;
    }

}
