package com.josiah.uno.multiplayer.client.clientmessage;

/*
    This interface is implemented by the Client class, and it is used by the Client's game manager to allow the
    client game manager to use the Client to send messages to the server when an action takes place.

    The Client is the one who sends the messages to the server. The Client owns the ClientUnoGameManager. The
    ClientUnoGameManager is what updates the player's game state and controls the player's end of the game. The
    ClientUnoGameManager stores a reference to GameActionListener, which is a reference to the client, and then can
    use the client to send messages.

    For example, when the ClientUnoGameManager calls its drawCard() method, it can use gameActionListener.sendActionMessage(),
    which will use the client to send the message to the server.
 */

public interface GameActionListener {
    void sendActionMessage(GameAction action);
}
