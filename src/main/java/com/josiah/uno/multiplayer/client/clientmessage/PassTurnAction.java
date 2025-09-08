package com.josiah.uno.multiplayer.client.clientmessage;

/*
    This class is a bean class, or data transfer object, and it is used to send information from client
    to server.

    PassTurnAction takes place when a player passes their turn.

    Only the object itself needs to be sent, because the server can determine who the player is by their player ID,
    which the server maps to the client who is sending the message.
 */

public class PassTurnAction extends GameAction {

    public PassTurnAction() {
        setType(GameActionType.PASS_TURN); // the constructor sets the action type
    }

}
