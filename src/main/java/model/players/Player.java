package model.players;

/*
Team Members: Steve Wareham, Charles Davidson, Josiah Stoltzfus
Date: 3/7/2025
------------------------------------------------------------------------------
*/

import model.images.playerimages.PlayerImage;

import java.io.Serializable;

public abstract class Player implements Serializable {
    private String name;
    private PlayerImage playerImage;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return name;
    }

    public void setImage(PlayerImage playerImage) {
        this.playerImage = playerImage;
    }

    public PlayerImage getImage() {
        return playerImage;
    }
}
