package multiplayer.client.clientmessage;

public class DisconnectAction extends GameAction {

    public DisconnectAction() {
        setType(GameActionType.DISCONNECT);
    }
}
