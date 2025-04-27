package model.image.cardimage;

import model.cardgame.card.unocard.UnoCard;
import model.cardgame.card.unocard.UnoSuit;
import model.cardgame.card.unocard.UnoValue;
import model.image.ImageLogger;
import model.image.playerimage.PlayerImages;

import java.net.URL;
import java.util.*;

import static model.cardgame.card.unocard.UnoSuit.*;
import static model.cardgame.card.unocard.UnoValue.*;
import static java.util.Map.entry;

/*
    This class contains Maps for storing classic images of Uno cards.

    There are two maps to be aware of here:
        - CARD_IMAGES
        - CARD_IMAGE_PATHS

    The actual image URL paths are originally stored in the CARD_IMAGE_PATHS HashMap.
        - Outer map keys are suits (e.g., RED, GREEN, BLUE, YELLOW, WILD).
        - Outer map values are maps.
            - Inner map keys correspond to card values within their suit.
            - Inner map values are file paths for an image.

    The UnoCardImageManager class is what's used to retrieve images for cards. The UnoCardImageManager class reads
    from the CARD_IMAGES map to get the image, NOT the CARD_IMAGES_PATHS map.

    The CARD_IMAGES map is populated by the loadImages() method. This method is used to make sure the images load properly,
    and if there is an error loading an image, it should generate a placeholder image.

    The loadImages() method also uses the ImageLogger to log information to a log file if there is an error loading an image.

    Basically, the map is re-created by the loadImages method to verify that the images can be loaded, and if there is an error
    loading an image, a placeholder image is used instead. And it also logs information about the image that can't be loaded.
 */

// @formatter:off
public class UnoCardClassicImages {
    /*
        This is the map for storing the card images. It starts off empty. As the images are loaded, they will fill
        the map. If an image can't be loaded, then it will fill the image with a placeholder image.
     */
    private static final Map<UnoSuit, HashMap<UnoValue, URL>> CARD_IMAGES = new HashMap<>();

    // This is the default image URL that is used if an image can't be loaded for some reason
    private static final URL DEFAULT_IMAGE_URL = PlayerImages.class.getResource("/images/cardimages/deck.png");

    /*
        This constant UnoCard is used for the Deck image to be loaded. It's used for errorhandling. If the card
        image manager can't load a certain card, then it will use this card to load as a placeholder image.
     */
    public static final UnoCard DECK = new UnoCard(GENERAL, UnoValue.DECK);

    // This constant UnoCard had a similar purpose as the DECK UnoCard constant, but the LOGO isn't being used.
    public static final UnoCard LOGO = new UnoCard(GENERAL, UnoValue.LOGO);

    // This method loads the images of the cards.
    public static void loadImages() {
        // Loop through the image paths map key set
        for (var suit: CARD_IMAGE_PATHS.keySet()) {
            HashMap<UnoValue, URL> map = new HashMap<>(); // create a new HashMap that will be the inner map for CARD_IMAGES map
            var innerMap = CARD_IMAGE_PATHS.get(suit); // get the inner map within the CARD_IMAGE_PATHS
            for (var value: innerMap.keySet()) { // loop through the key set for the inner map

                String imagePath = CARD_IMAGE_PATHS.get(suit).get(value); // get the path as a String for the image

                try {
                    URL imageURL = UnoCardClassicImages.class.getResource(imagePath); // get the image resource as a URL object

                    if (imageURL == null) { // check if the imageURL is null
                        // if the imageURL is null, then the image resource couldn't be found with that file path, and this uses
                        // the ImageLogger to log an error message to the log file.
                        ImageLogger.getImageLogger().severe("[Classic Card Image Log]: " + imagePath + " image failed to load. Setting default image.");
                        map.put(value, DEFAULT_IMAGE_URL); // if the image wasn't loaded, then it puts the default image URL as a placeholder
                    } else {
                        map.put(value, imageURL); // if it isn't null, then map the imageURL with the value of the card
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            CARD_IMAGES.put(suit, map); // map the inner map to the current suit
        }
    }

    public static Map<UnoSuit, HashMap<UnoValue, URL>> getClassicCardImages() {
        return Collections.unmodifiableMap(CARD_IMAGES);
    }

    protected static final Map<UnoSuit, Map<UnoValue, String>> CARD_IMAGE_PATHS = new HashMap<>(Map.of(
            RED, new HashMap<>(Map.ofEntries(
                    entry(ZERO, "/image/cardimage/red0.png"),
                    entry(ONE, "/image/cardimage/red1.png"),
                    entry(TWO, "/image/cardimage/red2.png"),
                    entry(THREE, "/image/cardimage/red3.png"),
                    entry(FOUR, "/image/cardimage/red4.png"),
                    entry(FIVE, "/image/cardimage/red5.png"),
                    entry(SIX, "/image/cardimage/red6.png"),
                    entry(SEVEN, "/image/cardimage/red7.png"),
                    entry(EIGHT, "/image/cardimage/red8.png"),
                    entry(NINE, "/image/cardimage/red9.png"),
                    entry(SKIP, "/image/cardimage/red_skip.png"),
                    entry(REVERSE, "/image/cardimage/red_reverse.png"),
                    entry(DRAW_TWO, "/image/cardimage/red_draw.png")
            )),
            GREEN, new HashMap<>(Map.ofEntries(
                    entry(ZERO, "/image/cardimage/green0.png"),
                    entry(ONE, "/image/cardimage/green1.png"),
                    entry(TWO, "/image/cardimage/green2.png"),
                    entry(THREE, "/image/cardimage/green3.png"),
                    entry(FOUR, "/image/cardimage/green4.png"),
                    entry(FIVE, "/image/cardimage/green5.png"),
                    entry(SIX, "/image/cardimage/green6.png"),
                    entry(SEVEN, "/image/cardimage/green7.png"),
                    entry(EIGHT, "/image/cardimage/green8.png"),
                    entry(NINE, "/image/cardimage/green9.png"),
                    entry(SKIP, "/image/cardimage/green_skip.png"),
                    entry(REVERSE, "/image/cardimage/green_reverse.png"),
                    entry(DRAW_TWO, "/image/cardimage/green_draw.png")
            )),
            BLUE, new HashMap<>(Map.ofEntries(
                    entry(ZERO, "/image/cardimage/blue0.png"),
                    entry(ONE, "/image/cardimage/blue1.png"),
                    entry(TWO, "/image/cardimage/blue2.png"),
                    entry(THREE, "/image/cardimage/blue3.png"),
                    entry(FOUR, "/image/cardimage/blue4.png"),
                    entry(FIVE, "/image/cardimage/blue5.png"),
                    entry(SIX, "/image/cardimage/blue6.png"),
                    entry(SEVEN, "/image/cardimage/blue7.png"),
                    entry(EIGHT, "/image/cardimage/blue8.png"),
                    entry(NINE, "/image/cardimage/blue9.png"),
                    entry(SKIP, "/image/cardimage/blue_skip.png"),
                    entry(REVERSE, "/image/cardimage/blue_reverse.png"),
                    entry(DRAW_TWO, "/image/cardimage/blue_draw.png")
            )),
            YELLOW, new HashMap<>(Map.ofEntries(
                    entry(ZERO, "/image/cardimage/yellow0.png"),
                    entry(ONE, "/image/cardimage/yellow1.png"),
                    entry(TWO, "/image/cardimage/yellow2.png"),
                    entry(THREE, "/image/cardimage/yellow3.png"),
                    entry(FOUR, "/image/cardimage/yellow4.png"),
                    entry(FIVE, "/image/cardimage/yellow5.png"),
                    entry(SIX, "/image/cardimage/yellow6.png"),
                    entry(SEVEN, "/image/cardimage/yellow7.png"),
                    entry(EIGHT, "/image/cardimage/yellow8.png"),
                    entry(NINE, "/image/cardimage/yellow9.png"),
                    entry(SKIP, "/image/cardimage/yellow_skip.png"),
                    entry(REVERSE, "/image/cardimage/yellow_reverse.png"),
                    entry(DRAW_TWO, "/image/cardimage/yellow_draw.png")
            )),
            UnoSuit.WILD, new HashMap<>(Map.ofEntries(
                    entry(UnoValue.WILD, "/image/cardimage/wild.png"),
                    entry(UnoValue.WILD_DRAW_FOUR, "/image/cardimage/wild_draw.png")
            )),
            GENERAL, new HashMap<>(Map.ofEntries(
                    entry(UnoValue.DECK, "/image/cardimage/deck.png"),
                    entry(UnoValue.LOGO, "/image/cardimage/uno_logo.png")
            ))));
}
