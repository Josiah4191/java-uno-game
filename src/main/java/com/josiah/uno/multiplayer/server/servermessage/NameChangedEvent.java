package com.josiah.uno.multiplayer.server.servermessage;

/*
    This class is a bean class, or data transfer object, and it is used to send information from server
    to client.

    NameChangedEvent takes place when the server has changed a player's name.
 */

public class NameChangedEvent extends GameEvent {

    private int playerIndex; // store the player index for the player whose name was changed
    private String name; // store the updated String for what the player name was changed to

    public NameChangedEvent(int playerIndex, String name) {
        this.playerIndex = playerIndex;
        this.name = name;
        setType(GameEventType.NAME_CHANGED); // the constructor sets the event type
    }

    // get the player index
    public int getPlayerIndex() {
        return playerIndex;
    }

    // set the player index
    public void setPlayerIndex(int playerIndex) {
        this.playerIndex = playerIndex;
    }

    // get the player name
    public String getName() {
        return name;
    }

    // set the player name
    public void setName(String name) {
        this.name = name;
    }
}
