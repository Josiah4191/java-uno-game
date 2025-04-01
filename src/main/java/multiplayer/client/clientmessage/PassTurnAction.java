package multiplayer.client.clientmessage;

public class PassTurnAction extends GameAction {
    boolean passTurn;

    public PassTurnAction(boolean passTurn) {
        this.passTurn = passTurn;
        setType(GameActionType.PASS_TURN);
    }

    public boolean getPassTurn() {
        return passTurn;
    }

    public void setPassTurn(boolean passTurn) {
        this.passTurn = passTurn;
    }

}
