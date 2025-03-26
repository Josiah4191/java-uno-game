package multiplayer.client.unoclient;

import model.image.playerimage.PlayerImage;

public class ChangePlayerImage extends ClientAction {

    private PlayerImage playerImage;

    public ChangePlayerImage(PlayerImage playerImage) {
        setType(ClientActionType.CHANGE_IMAGE);
        this.playerImage = playerImage;
    }

    public PlayerImage getPlayerImage() {
        return playerImage;
    }

    public void setPlayerImage(PlayerImage playerImage) {
        this.playerImage = playerImage;
    }
}
