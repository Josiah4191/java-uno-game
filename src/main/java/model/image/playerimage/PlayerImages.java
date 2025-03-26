package model.image.playerimage;

import model.image.ImageLogger;

import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static java.util.Map.entry;
import static model.image.playerimage.PlayerImage.*;

public class PlayerImages {

    private static final Map<PlayerImage, URL> playerImages = new HashMap<>();
    private static final URL DEFAULT_IMAGE_URL = PlayerImages.class.getResource("/images/cardimages/deck.png");

    public static void loadImages() {
        for (var imageKey: imagePaths.keySet()) {
            String imagePath = imagePaths.get(imageKey);
            try {
                URL imageURL = PlayerImages.class.getResource(imagePath);
                if (imageURL == null) {
                    playerImages.put(imageKey, DEFAULT_IMAGE_URL);
                    ImageLogger.getImageLogger().warning("[Player Image Log]: " + imagePath + " failed to load. Using default image.");
                } else {
                    playerImages.put(imageKey, imageURL);
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
