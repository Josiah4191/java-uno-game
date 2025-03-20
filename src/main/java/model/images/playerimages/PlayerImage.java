package model.images.playerimages;

import java.io.Serializable;

public enum PlayerImage implements Serializable {
    RED_PLAYER_CARD("Red Player"), YELLOW_PLAYER_CARD("Yellow Player"), GREEN_PLAYER_CARD("Green Player"), BLUE_PLAYER_CARD("Blue Player");

    private String name;

    PlayerImage(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
