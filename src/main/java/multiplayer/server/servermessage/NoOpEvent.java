package multiplayer.server.servermessage;

public class NoOpEvent extends GameEvent {

    public NoOpEvent() {
        setType(GameEventType.NO_OP);
    }
}
