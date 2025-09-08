package com.josiah.uno.multiplayer.server.servermessage;

/*
    This class is a bean class, or data transfer object, and it is used to send information from server
    to client.

    NoOpEvent takes place when a change hasn't happened or some kind of validation error occurred in the program.

    For example, if a player tries to play a card when it is not their turn, or their card is invalid, then the server
    will send a NoOpEvent to inform the client that no operation took place, and the reason why.

    The NoOpEventType identifies the type of no operation that took place.
 */

public class NoOpEvent extends GameEvent {

    private NoOpEventType eventType;

    public NoOpEvent(NoOpEventType eventType) {
        this.eventType = eventType;
        setType(GameEventType.NO_OP); // the constructor sets the event type (this is not the same as the NoOpEventType eventType)
    }

    // get the event type
    public NoOpEventType getEventType() {
        return eventType;
    }

}
