package model.cardgame.unogame;

/*
This enum type represents and keeps track of the play direction in the UNO game.
 */

import java.io.Serializable;

public enum PlayDirection implements Serializable {
    FORWARD(), REVERSE();

    // Checks if the direction isForward
    public boolean isForward() {
        return this == FORWARD;
    }

}
