package multiplayer.client.clientmessage;

/*
    This class is a bean class, or data transfer object, and it is used to send information from client
    to server.

    DisconnectAction takes place when a player leaves the game

    Only the object itself needs to be sent, because the server can determine who the player is by their player ID,
    which the server maps to the client who is sending the message.
 */

public class DisconnectAction extends GameAction {

    public DisconnectAction() {
        setType(GameActionType.DISCONNECT); // the constructor sets the action type
    }
}
