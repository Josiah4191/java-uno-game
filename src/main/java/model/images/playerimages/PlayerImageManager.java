package model.images.playerimages;

import model.images.ImageLogger;
import javafx.scene.image.Image;

import java.io.Serializable;
import java.net.URL;

public class PlayerImageManager implements Serializable {

    static {
        ImageLogger.getImageLogger().info("[Player Image Log]: Loading images");
        PlayerImages.loadImages();
    }

    public Image getImage(PlayerImage playerImage) {
        var imageMap = PlayerImages.getPlayerImages();
        URL playerURL = imageMap.get(playerImage);
        return new Image(playerURL.toString());
    }
}
