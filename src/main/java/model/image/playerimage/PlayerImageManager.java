package model.image.playerimage;

import model.image.ImageLogger;
import javafx.scene.image.Image;

import java.net.URL;

/*
This class is responsible for getting Player images.
The getImage method receives a PlayerImage enum type and returns an Image object for that player image.
 */

public class PlayerImageManager {

    // This static initialization block is used to load the player images.
    static {
        ImageLogger.getImageLogger().info("[Player Image Log]: Loading images"); // log that the player images are being loaded
        PlayerImages.loadImages(); // load the player images.
    }

    /* This method receives a PlayerImage enum type, and returns an Image object by getting file path of the image
        from the map contained in the PlayerImages class.
     */
    public Image getImage(PlayerImage playerImage) {
        var imageMap = PlayerImages.getPlayerImages();
        URL playerURL = imageMap.get(playerImage);
        return new Image(playerURL.toString());
    }
}
