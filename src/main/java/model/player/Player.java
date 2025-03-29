package model.player;

/*
Team Members: Steve Wareham, Charles Davidson, Josiah Stoltzfus
Date: 3/7/2025
------------------------------------------------------------------------------
*/

import model.image.playerimage.PlayerImage;

import java.io.Serializable;
import java.util.Objects;

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

    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return playerID == player.playerID && isAI == player.isAI && Objects.equals(name, player.name) && playerImage == player.playerImage;
    }

    public int hashCode() {
        return Objects.hash(playerID, isAI, name, playerImage);
    }
}
