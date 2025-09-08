package com.josiah.uno.model.player;

import com.josiah.uno.model.image.playerimage.PlayerImage;

import java.io.Serializable;
import java.util.Objects;

/*

    This class represents a Player in the game. It's an abstract class and needs to be subclassed to represent a specific
    type of player for the card game that is being played. Currently, only UnoPlayer has been implemented.

    Every player has an ID. The player ID is used by the server to identify the player and map them to the correct client
    socket when sending messages.
*/

public abstract class Player implements Serializable {

    private int playerID; // int variable to hold the playerID
    private boolean isAI; // boolean variable to determine if the player is an AI player

    private String name;
    private PlayerImage playerImage;

    public Player(int playerID) {
        this.playerID = playerID;
    }

    // Get playerID
    public int getPlayerID() {
        return playerID;
    }

    // Set the name
    public void setName(String name) {
        this.name = name;
    }

    // Get the name
    public String getName() {
        return name;
    }

    // Set the player image
    public void setImage(PlayerImage playerImage) {
        this.playerImage = playerImage;
        setName(playerImage.getName()); // set the default name to the name associated with the PlayerImage enum type
    }

    // Get the player image
    public PlayerImage getImage() {
        return playerImage;
    }

    // Set whether the player is AI
    public void setIsAI(boolean isAI) {
        this.isAI = isAI;
    }

    // Check if player is AI
    public boolean isAI() {
        return isAI;
    }

    // Auto-generated equals method
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return playerID == player.playerID && isAI == player.isAI && Objects.equals(name, player.name) && playerImage == player.playerImage;
    }

    // Auto-generated hash code method
    public int hashCode() {
        return Objects.hash(playerID, isAI, name, playerImage);
    }

    // Override toString method to return the name of the player. this is used for debugging.
    public String toString() {
        return name;
    }
}
