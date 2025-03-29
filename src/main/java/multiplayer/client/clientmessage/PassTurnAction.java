package multiplayer.client.clientmessage;

public class PassTurnAction extends GameAction {
    boolean isPassTurn;

    public PassTurnAction(boolean isPassTurn) {
        this.isPassTurn = isPassTurn;
        setType(GameActionType.PASS_TURN);
    }

    public boolean isPassTurn() {
        return isPassTurn;
    }

    public void setPassTurn(boolean passTurn) {
        isPassTurn = passTurn;
    }

}
