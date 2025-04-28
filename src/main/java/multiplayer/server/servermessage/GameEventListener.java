package multiplayer.server.servermessage;

/*
    This interface is implemented by the Server class, and it is used by the Server's game manager to allow the
    server game manager to use the Server to send messages to the client when an event takes place.

    The Server is the one who sends messages to the clients. The Server owns the ServerUnoGameManager. The
    ServerUnoGameManager is what updates the actual game state and controls the game. The
    ServerUnoGameManager stores a reference to GameEventListener, which is a reference to the server, and then can
    use the server to send messages.

    For example, when the ServerUnoGameManager calls its updatePlayableCards() method, it can use gameEventListener.sendEventMessage(),
    which will use the server to send the message to the client, or all clients with sendEventMessageToAll()
 */

public interface GameEventListener {
    void sendEventMessage(GameEvent event, int playerID);

    void sendEventMessageToAll(GameEvent event);
}
