package multiplayer.client.clientmessage;

/*
    This class is a bean class, or data transfer object, and it is used to send information from client
    to server.

    DrawCardAction takes place when a player draws a card

    Only the object itself needs to be sent, because the server can determine who the player is by their player ID,
    which the server maps to the client who is sending the message.
 */

public class DrawCardAction extends GameAction {

    public DrawCardAction() {
        setType(GameActionType.DRAW_CARD); // the constructor sets the draw card action
    }


}
