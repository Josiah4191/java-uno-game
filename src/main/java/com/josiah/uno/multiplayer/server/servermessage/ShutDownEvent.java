package com.josiah.uno.multiplayer.server.servermessage;

/*
    This class is a bean class, or data transfer object, and it is used to send information from server
    to client.

    ShutDownEvent takes place when the server and client are shutting down.
 */

public class ShutDownEvent extends GameEvent {
    public ShutDownEvent() {
        setType(GameEventType.SHUT_DOWN);
    }
}
