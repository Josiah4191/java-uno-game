package com.josiah.uno.multiplayer.server.servermessage;

import com.josiah.uno.model.player.cardplayer.unoplayer.UnoPlayer;

/*
    This class is a bean class, or data transfer object, and it is used to send information from server
    to client.

    AddLocalPlayerEvent takes place when a client has sent a JoinGameAction to the server
 */


public class AddLocalPlayerEvent extends GameEvent {

    UnoPlayer localPlayer; // store the localPlayer that was added to the game

    public AddLocalPlayerEvent(UnoPlayer localPlayer) {
        this.localPlayer = localPlayer;
        setType(GameEventType.ADD_LOCAL_PLAYER); // the constructor sets the event type
    }

    // get the local player
    public UnoPlayer getLocalPlayer() {
        return localPlayer;
    }

    // set the local player
    public void setLocalPlayer(UnoPlayer localPlayer) {
        this.localPlayer = localPlayer;
    }
}
