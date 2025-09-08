package com.josiah.uno.multiplayer.server.servermessage;

/*
    This class is a bean class, or data transfer object, and it is used to send information from server
    to client.

    TurnPassedEvent takes place when a client sends a PassTurnAction to the server and the server move to the next player
    and calls the continueTurnCycle().
 */

public class TurnPassedEvent extends GameEvent {

    private int currentPlayerIndex; // store the updated current player index

    public TurnPassedEvent(int currentPlayerIndex) {
        this.currentPlayerIndex = currentPlayerIndex;
        setType(GameEventType.TURN_PASSED); // the constructor sets the event type
    }

    // get the current player index
    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

}
