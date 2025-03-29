package multiplayer.client.clientmessage;

import model.image.playerimage.PlayerImage;

public class ChangeImageAction extends GameAction {

    private PlayerImage playerImage;

    public ChangeImageAction(PlayerImage playerImage) {
        setType(GameActionType.CHANGE_IMAGE);
        this.playerImage = playerImage;
    }

    public PlayerImage getPlayerImage() {
        return playerImage;
    }

    public void setPlayerImage(PlayerImage playerImage) {
        this.playerImage = playerImage;
    }
}
