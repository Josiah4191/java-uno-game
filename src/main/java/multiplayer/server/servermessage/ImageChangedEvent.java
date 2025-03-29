package multiplayer.server.servermessage;

import model.image.playerimage.PlayerImage;

public class ImageChangedEvent extends GameEvent {

    private int playerIndex;
    private PlayerImage image;

    public ImageChangedEvent(int playerIndex, PlayerImage image) {
        this.playerIndex = playerIndex;
        this.image = image;
        setType(GameEventType.IMAGE_CHANGED);
    }

    public int getPlayerIndex() {
        return playerIndex;
    }

    public void setPlayerIndex(int playerIndex) {
        this.playerIndex = playerIndex;
    }

    public PlayerImage getImage() {
        return image;
    }

    public void setImage(PlayerImage image) {
        this.image = image;
    }
}
