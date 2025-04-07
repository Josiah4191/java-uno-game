package multiplayer.server.servermessage;

public class TurnPassedEvent extends GameEvent {

    private int currentPlayerIndex;

    public TurnPassedEvent(int currentPlayerIndex) {
        this.currentPlayerIndex = currentPlayerIndex;
        setType(GameEventType.TURN_PASSED);
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public void setCurrentPlayerIndex(int currentPlayerIndex) {
        this.currentPlayerIndex = currentPlayerIndex;
    }

}
