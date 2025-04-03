package multiplayer.server.servermessage;

public class ShutDownEvent extends GameEvent {
    public ShutDownEvent() {
        setType(GameEventType.SHUT_DOWN);
    }
}
