package multiplayer.client.clientmessage;

import model.image.playerimage.PlayerImage;

/*
    This class is a bean class, or data transfer object, and it is used to send information from client
    to server.

    JoinGameAction takes place when a client/local/human player joins the game.
 */

public class JoinGameAction extends GameAction {

    private String name; // store the player's chosen name
    private PlayerImage playerImage; // store the player's chosen player image

    public JoinGameAction(String name, PlayerImage playerImage) {
        this.name = name;
        this.playerImage = playerImage;
        setType(GameActionType.JOIN_GAME); // the constructor sets the action type
    }

    // get the name
    public String getName() {
        return name;
    }

    // set the name
    public void setName(String name) {
        this.name = name;
    }

    // get the player image
    public PlayerImage getPlayerImage() {
        return playerImage;
    }

    // set the player image
    public void setPlayerImage(PlayerImage playerImage) {
        this.playerImage = playerImage;
    }
}
