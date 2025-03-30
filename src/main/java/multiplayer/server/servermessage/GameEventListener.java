package multiplayer.server.servermessage;

public interface GameEventListener {
    void sendEventMessage(GameEvent event, int playerID);

    void sendEventMessageToAll(GameEvent event);
}
