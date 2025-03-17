package model.players;

/*
Team Members: Steve Wareham, Charles Davidson, Josiah Stoltzfus
Date: 3/7/2025
------------------------------------------------------------------------------
*/

import javafx.scene.image.Image;
import model.images.playerimages.PlayerImage;
import model.images.playerimages.PlayerImageManager;

public abstract class Player {
    private String name;
    private Image playerImage;

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
        PlayerImageManager playerImageManager = new PlayerImageManager();
        Image image = playerImageManager.getImage(playerImage);
        this.playerImage = image;
    }

    public Image getImage() {
        return playerImage;
    }
}
