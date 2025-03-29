package multiplayer.client.clientmessage;

public class CallUnoAction extends GameAction {

    int playerIndex;

    public CallUnoAction(int playerIndex) {
        setType(GameActionType.CALL_UNO);
    }

    public int getPlayerIndex() {
        return playerIndex;
    }

    public void setPlayerIndex(int playerIndex) {
        this.playerIndex = playerIndex;
    }
}
