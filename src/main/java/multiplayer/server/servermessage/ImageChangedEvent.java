package multiplayer.server.servermessage;

import model.image.playerimage.PlayerImage;

/*
    This class is a bean class, or data transfer object, and it is used to send information from server
    to client.

    ImageChangedEvent takes place when the server has changed a player's image.
 */

public class ImageChangedEvent extends GameEvent {

    private int playerIndex; // store the player index for the player whose image was changed
    private PlayerImage image; // store the player image for the image that the player changed to

    public ImageChangedEvent(int playerIndex, PlayerImage image) {
        this.playerIndex = playerIndex;
        this.image = image;
        setType(GameEventType.IMAGE_CHANGED); // the constructor sets the event type
    }

    // get the player index
    public int getPlayerIndex() {
        return playerIndex;
    }

    // set the player index
    public void setPlayerIndex(int playerIndex) {
        this.playerIndex = playerIndex;
    }

    // get the player image
    public PlayerImage getImage() {
        return image;
    }

    // set the player image
    public void setImage(PlayerImage image) {
        this.image = image;
    }
}
