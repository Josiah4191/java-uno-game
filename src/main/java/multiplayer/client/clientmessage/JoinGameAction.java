package multiplayer.client.clientmessage;

import model.image.playerimage.PlayerImage;

public class JoinGameAction extends GameAction {

    private String name;
    private PlayerImage playerImage;

    public JoinGameAction(String name, PlayerImage playerImage) {
        this.name = name;
        this.playerImage = playerImage;
        setType(GameActionType.JOIN_GAME);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PlayerImage getPlayerImage() {
        return playerImage;
    }

    public void setPlayerImage(PlayerImage playerImage) {
        this.playerImage = playerImage;
    }
}
