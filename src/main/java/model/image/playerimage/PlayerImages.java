package model.image.playerimage;

import model.image.ImageLogger;

import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static java.util.Map.entry;
import static model.image.playerimage.PlayerImage.*;

public class PlayerImages {

    /*
    This class contains Maps for storing player images.

    There are two maps to be aware of here:
        - playerImages
        - imagePaths

    The actual image URL paths are originally stored in the imagePaths HashMap.
        - The key is a PlayerImage enum type
        - The value is the file path to the image

    The PlayerImageManager class is what's used to retrieve images for players. The PlayerImageManager class reads
    from the playerImages map to get the image, NOT the imagePaths map.

    The playerImages map is populated by the loadImages() method. This method is used to make sure the images load properly,
    and if there is an error loading an image, it should generate a placeholder image.

    The loadImages() method also uses the ImageLogger to log information to a log file if there is an error loading an image.

    Basically, the map is re-created by the loadImages method to verify that the images can be loaded, and if there is an error
    loading an image, a placeholder image is used instead. And it also logs information about the image that can't be loaded.
 */

    /*
    This is the map for storing the player images. It starts off empty. As the images are loaded, they will fill
    the map. If an image can't be loaded, then it will fill the image with a placeholder image.
    */
    private static final Map<PlayerImage, URL> playerImages = new HashMap<>();
    // Placeholder URL for an image if another image fails to load.
    private static final URL DEFAULT_IMAGE_URL = PlayerImages.class.getResource("/images/cardimages/deck.png");

    // This method loads the images and adds them to the playerImages HashMap if successful
    public static void loadImages() {
        for (var imageKey: imagePaths.keySet()) { // loop through the key set
            String imagePath = imagePaths.get(imageKey); // get the file path as a String
            try {
                URL imageURL = PlayerImages.class.getResource(imagePath); // try to load the resource with the image file path
                if (imageURL == null) { // if imageURL is null
                    playerImages.put(imageKey, DEFAULT_IMAGE_URL); // set the image as the default placeholder image
                    // log an error message about which image file couldn't be loaded
                    ImageLogger.getImageLogger().warning("[Player Image Log]: " + imagePath + " failed to load. Using default image.");
                } else {
                    playerImages.put(imageKey, imageURL); // if imageURL isn't null, add the imageURL to the playerImages map
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public static Map<PlayerImage, URL> getPlayerImages() {
        return Collections.unmodifiableMap(playerImages);
    }

    private static Map<PlayerImage, String> imagePaths = new HashMap<>(Map.ofEntries(
            entry(P1, "/image/playerimage/p1.png"),
            entry(P2, "/image/playerimage/p2.png"),
            entry(P3, "/image/playerimage/p3.png"),
            entry(P4, "/image/playerimage/p4.png"),
            entry(P5, "/image/playerimage/p5.png"),
            entry(P6, "/image/playerimage/p6.png"),
            entry(P7, "/image/playerimage/p7.png"),
            entry(P8, "/image/playerimage/p8.png"),
            entry(P9, "/image/playerimage/p9.png"),
            entry(P10, "/image/playerimage/p10.png"),
            entry(P11, "/image/playerimage/p11.png"),
            entry(P12, "/image/playerimage/p12.png"),
            entry(P13, "/image/playerimage/p13.png"),
            entry(P14, "/image/playerimage/p14.png"),
            entry(P15, "/image/playerimage/p15.png"),
            entry(P16, "/image/playerimage/p16.png"),
            entry(P17, "/image/playerimage/p17.png"),
            entry(P18, "/image/playerimage/p18.png"),
            entry(P19, "/image/playerimage/p19.png"),
            entry(P20, "/image/playerimage/p20.png")
    ));


}
