package multiplayer.server.servermessage;

public class NameChangedEvent extends GameEvent {

    private int playerIndex;
    private String name;

    public NameChangedEvent(int playerIndex, String name) {
        this.playerIndex = playerIndex;
        this.name = name;
        setType(GameEventType.NAME_CHANGED);
    }

    public int getPlayerIndex() {
        return playerIndex;
    }

    public void setPlayerIndex(int playerIndex) {
        this.playerIndex = playerIndex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
