package multiplayer.server.servermessage;

public class TurnPassedEvent extends GameEvent {

    private int currentPlayerIndex;

    public TurnPassedEvent(int nextPlayerIndex) {
        this.currentPlayerIndex = nextPlayerIndex;
        setType(GameEventType.TURN_PASSED);
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public void setCurrentPlayerIndex(int currentPlayerIndex) {
        this.currentPlayerIndex = currentPlayerIndex;
    }
}
