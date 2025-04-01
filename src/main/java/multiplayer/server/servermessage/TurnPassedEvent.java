package multiplayer.server.servermessage;

public class TurnPassedEvent extends GameEvent {

    private boolean turnPassed;
    private int playerIndex;

    public TurnPassedEvent(int playerIndex, boolean turnPassed) {
        this.playerIndex = playerIndex;
        this.turnPassed = turnPassed;
        setType(GameEventType.TURN_PASSED);
    }

    public boolean getTurnPassed() {
        return turnPassed;
    }

    public void setTurnPassed(boolean turnPassed) {
        this.turnPassed = turnPassed;
    }

    public int getPlayerIndex() {
        return playerIndex;
    }

    public void setPlayerIndex(int playerIndex) {
        this.playerIndex = playerIndex;
    }

}
