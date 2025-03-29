package multiplayer.server.servermessage;

import model.player.cardplayer.unoplayer.UnoPlayer;

public class AddLocalPlayerEvent extends GameEvent {

    UnoPlayer localPlayer;

    public AddLocalPlayerEvent(UnoPlayer localPlayer) {
        this.localPlayer = localPlayer;
        setType(GameEventType.ADD_LOCAL_PLAYER);
    }

    public UnoPlayer getLocalPlayer() {
        return localPlayer;
    }

    public void setLocalPlayer(UnoPlayer localPlayer) {
        this.localPlayer = localPlayer;
    }
}
