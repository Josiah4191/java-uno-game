package multiplayer.server.servermessage;

public class AnnounceWinnerEvent extends GameEvent {
    private int playerIndex;

    public AnnounceWinnerEvent(int playerIndex) {
        this.playerIndex = playerIndex;
        setType(GameEventType.ANNOUNCE_WINNER);
    }

    public int getPlayerIndex() {
        return playerIndex;
    }

    public void setPlayerIndex(int playerIndex) {
        this.playerIndex = playerIndex;
    }
}
