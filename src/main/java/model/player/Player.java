package model.player;

/*
Team Members: Steve Wareham, Charles Davidson, Josiah Stoltzfus
Date: 3/7/2025
------------------------------------------------------------------------------
*/

import model.image.playerimage.PlayerImage;

import java.io.Serializable;

public abstract class Player implements Serializable {

    private int playerID;
    private boolean isAI;

    private String name;
    private PlayerImage playerImage;

    public Player(int playerID) {
        this.playerID = playerID;
    }

    public int getPlayerID() {
        return playerID;
    }

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
        setName(playerImage.getName());
    }

    public PlayerImage getImage() {
        return playerImage;
    }

    public void setIsAI(boolean isAI) {
        this.isAI = isAI;
    }

    public boolean isAI() {
        return isAI;
    }


}
