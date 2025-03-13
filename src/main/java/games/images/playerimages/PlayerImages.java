package games.images.playerimages;

import games.images.ImageLogger;

import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static java.util.Map.entry;
import static games.images.playerimages.PlayerImage.*;

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
            entry(RED_PLAYER_CARD, "/images/playerimages/RedPlayer.png"),
            entry(BLUE_PLAYER_CARD, "/images/playerimages/BluePlayer.png"),
            entry(GREEN_PLAYER_CARD, "/images/playerimages/GreenPlayer.png"),
            entry(YELLOW_PLAYER_CARD, "/images/playerimages/YellowPlayer.png")
    ));


}
