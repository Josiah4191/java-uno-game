package multiplayer.client.unoclient;

public class PassTurn extends ClientAction {
    boolean isPassTurn;

    public PassTurn(boolean isPassTurn) {
        this.isPassTurn = isPassTurn;
        setType(ClientActionType.PASS_TURN);
    }

    public boolean isPassTurn() {
        return isPassTurn;
    }

    public void setPassTurn(boolean passTurn) {
        isPassTurn = passTurn;
    }

}
