package com.josiah.uno.multiplayer.client.clientmessage;

import com.josiah.uno.model.image.playerimage.PlayerImage;

/*
    This class is a bean class, or data transfer object, and it is used to send information from client
    to server.

    ChangeImageAction takes place when a player wants to change their image
 */


public class ChangeImageAction extends GameAction {

    private PlayerImage playerImage; // store the image of what the player wants to change to

    public ChangeImageAction(PlayerImage playerImage) {
        setType(GameActionType.CHANGE_IMAGE); // the constructor sets the action type
        this.playerImage = playerImage;
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
