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

public class UnoCardClassicImages {

    /*
    Team Members: Steve Wareham, Charles Davidson, Josiah Stoltzfus
    Date: 3/7/2025
    ------------------------------------------------------------------------------

    This class contains Maps for storing classic images of Uno cards.
        - Outer map keys are suits (e.g., RED, GREEN, BLUE, YELLOW, WILD).
        - Outer map values are maps.
            - Inner map keys correspond to card values within their suit.
            - Inner map values are file paths for an image.
     */
    // @formatter:off

    private static final Map<UnoSuit, HashMap<UnoValue, URL>> CARD_IMAGES = new HashMap<>();
    private static final URL DEFAULT_IMAGE_URL = PlayerImages.class.getResource("/images/cardimages/deck.png");
    public static final UnoCard DECK = new UnoCard(GENERAL, UnoValue.DECK);
    public static final UnoCard LOGO = new UnoCard(GENERAL, UnoValue.LOGO);

    public static void loadImages() {
        for (var suit: CARD_IMAGE_PATHS.keySet()) {
            HashMap<UnoValue, URL> map = new HashMap<>();
            var innerMap = CARD_IMAGE_PATHS.get(suit);
            for (var value: innerMap.keySet()) {

                String imagePath = CARD_IMAGE_PATHS.get(suit).get(value);

                try {
                    URL imageURL = UnoCardClassicImages.class.getResource(imagePath);

                    if (imageURL == null) {
                        ImageLogger.getImageLogger().severe("[Classic Card Image Log]: " + imagePath + " image failed to load. Setting default image.");
                        map.put(value, DEFAULT_IMAGE_URL);
                    } else {
                        map.put(value, imageURL);
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            CARD_IMAGES.put(suit, map);
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
