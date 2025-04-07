package multiplayer.server.servermessage;

public class NoOpEvent extends GameEvent {

    private NoOpEventType eventType;

    public NoOpEvent(NoOpEventType eventType) {
        this.eventType = eventType;
        setType(GameEventType.NO_OP);
    }

    public NoOpEventType getEventType() {
        return eventType;
    }

}
