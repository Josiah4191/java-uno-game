package multiplayer.client.unoclient;

public class CallUno extends ClientAction {

    int playerIndex;

    public CallUno(int playerIndex) {
        setType(ClientActionType.CALL_UNO);
    }

    public int getPlayerIndex() {
        return playerIndex;
    }

    public void setPlayerIndex(int playerIndex) {
        this.playerIndex = playerIndex;
    }
}
