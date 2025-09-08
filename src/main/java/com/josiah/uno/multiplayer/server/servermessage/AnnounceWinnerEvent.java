package com.josiah.uno.multiplayer.server.servermessage;

/*
    This class is a bean class, or data transfer object, and it is used to send information from server
    to client.

    AnnounceWinnerEvent takes place when a player has won the game by having 0 cards remaining.
 */

public class AnnounceWinnerEvent extends GameEvent {
    private int playerIndex; // store the player index for the player who has won

    public AnnounceWinnerEvent(int playerIndex) {
        this.playerIndex = playerIndex;
        setType(GameEventType.ANNOUNCE_WINNER); // the constructor sets the event type
    }

    // get the player index
    public int getPlayerIndex() {
        return playerIndex;
    }

    // set the player index
    public void setPlayerIndex(int playerIndex) {
        this.playerIndex = playerIndex;
    }
}
