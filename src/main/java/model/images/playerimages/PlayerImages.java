package model.images.playerimages;

import model.images.ImageLogger;

import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static java.util.Map.entry;
import static model.images.playerimages.PlayerImage.*;

public class PlayerImages {

    private static final Map<PlayerImage, URL> playerImages = new HashMap<>();
    private static final URL DEFAULT_IMAGE_URL = PlayerImages.class.getResource("/images/cardimages/Deck.png");

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
            entry(P1, "/images/playerimages/p1.png"),
            entry(P2, "/images/playerimages/p2.png"),
            entry(P3, "/images/playerimages/p3.png"),
            entry(P4, "/images/playerimages/p4.png"),
            entry(P5, "/images/playerimages/p5.png"),
            entry(P6, "/images/playerimages/p6.png"),
            entry(P7, "/images/playerimages/p7.png"),
            entry(P8, "/images/playerimages/p8.png"),
            entry(P9, "/images/playerimages/p9.png"),
            entry(P10, "/images/playerimages/p10.png"),
            entry(P11, "/images/playerimages/p11.png"),
            entry(P12, "/images/playerimages/p12.png"),
            entry(P13, "/images/playerimages/p13.png"),
            entry(P14, "/images/playerimages/p14.png"),
            entry(P15, "/images/playerimages/p15.png"),
            entry(P16, "/images/playerimages/p16.png"),
            entry(P17, "/images/playerimages/p17.png"),
            entry(P18, "/images/playerimages/p18.png"),
            entry(P19, "/images/playerimages/p19.png"),
            entry(P20, "/images/playerimages/p20.png")
    ));


}
