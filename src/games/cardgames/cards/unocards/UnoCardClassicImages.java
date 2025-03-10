package games.cardgames.cards.unocards;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static java.util.Map.entry;
import static games.cardgames.cards.unocards.UnoSuit.*;
import static games.cardgames.cards.unocards.UnoSuit.YELLOW;
import static games.cardgames.cards.unocards.UnoValue.*;
import static games.cardgames.cards.unocards.UnoValue.DRAW_TWO;

public class UnoCardClassicImages {

    /*
    Team Members: Steve Wareham, Charles Davidson, Josiah Stoltzfus
    Author: Josiah Stoltzfus
    Date: 3/7/2025
    ------------------------------------------------------------------------------

    This class contains Maps for storing classic images of Uno cards.
        - Outer map keys are suits (e.g., RED, GREEN, BLUE, YELLOW, WILD).
        - Outer map values are maps.
            - Inner map keys correspond to card values within their suit.
            - Inner map values are file paths for an image.
     */
    // @formatter:off
    String cwd = System.getProperty("user.dir");


    protected static final Map<UnoSuit, Map<UnoValue, String>> IMAGES = new HashMap<>(Map.of(
            RED, new HashMap<>(Map.ofEntries(
                    entry(ZERO, "cwd" + File.separator + "images/cardimages/Red_0.png"),
                    entry(ONE, "cwd" + File.separator + "images/cardimages/Red_0.png"),
                    entry(TWO, "cwd" + File.separator + "images/cardimages/Red_0.png"),
                    entry(THREE, "cwd" + File.separator + "images/cardimages/Red_0.png"),
                    entry(FOUR, "cwd" + File.separator + "images/cardimages/Red_0.png"),
                    entry(FIVE, "cwd" + File.separator + "images/cardimages/Red_0.png"),
                    entry(SIX, "cwd" + File.separator + "images/cardimages/Red_0.png"),
                    entry(SEVEN, "cwd" + File.separator + "images/cardimages/Red_0.png"),
                    entry(EIGHT, "cwd" + File.separator + "images/cardimages/Red_0.png"),
                    entry(NINE, "cwd" + File.separator + "images/cardimages/Red_0.png"),
                    entry(SKIP, "cwd" + File.separator + "images/cardimages/Red_0.png"),
                    entry(REVERSE, "cwd" + File.separator + "images/cardimages/Red_0.png"),
                    entry(DRAW_TWO, "cwd" + File.separator + "images/cardimages/Red_0.png"))),
            GREEN, new HashMap<>(Map.ofEntries(
                    entry(ZERO, "GREEN ZERO IMAGE"),
                    entry(ONE, "GREEN ONE IMAGE"),
                    entry(TWO, "GREEN TWO IMAGE"),
                    entry(THREE, "GREEN THREE IMAGE"),
                    entry(FOUR, "GREEN FOUR IMAGE"),
                    entry(FIVE, "GREEN FIVE IMAGE"),
                    entry(SIX, "GREEN SIX IMAGE"),
                    entry(SEVEN, "GREEN SEVEN IMAGE"),
                    entry(EIGHT, "GREEN EIGHT IMAGE"),
                    entry(NINE, "GREEN NINE IMAGE"),
                    entry(SKIP, "GREEN SKIP IMAGE"),
                    entry(REVERSE, "GREEN REVERSE IMAGE"),
                    entry(DRAW_TWO, "GREEN DRAW TWO IMAGE"))),
            BLUE, new HashMap<>(Map.ofEntries(
                    entry(ZERO, "BLUE ZERO IMAGE"),
                    entry(ONE, "BLUE ONE IMAGE"),
                    entry(TWO, "BLUE TWO IMAGE"),
                    entry(THREE, "BLUE THREE IMAGE"),
                    entry(FOUR, "BLUE FOUR IMAGE"),
                    entry(FIVE, "BLUE FIVE IMAGE"),
                    entry(SIX, "BLUE SIX IMAGE"),
                    entry(SEVEN, "BLUE SEVEN IMAGE"),
                    entry(EIGHT, "BLUE EIGHT IMAGE"),
                    entry(NINE, "BLUE NINE IMAGE"),
                    entry(SKIP, "BLUE SKIP IMAGE"),
                    entry(REVERSE, "BLUE REVERSE IMAGE"),
                    entry(DRAW_TWO, "BLUE DRAW TWO IMAGE"))),
            YELLOW, new HashMap<>(Map.ofEntries(
                    entry(ZERO, "YELLOW ZERO IMAGE"),
                    entry(ONE, "YELLOW ONE IMAGE"),
                    entry(TWO, "YELLOW TWO IMAGE"),
                    entry(THREE, "YELLOW THREE IMAGE"),
                    entry(FOUR, "YELLOW FOUR IMAGE"),
                    entry(FIVE, "YELLOW FIVE IMAGE"),
                    entry(SIX, "YELLOW SIX IMAGE"),
                    entry(SEVEN, "YELLOW SEVEN IMAGE"),
                    entry(EIGHT, "YELLOW EIGHT IMAGE"),
                    entry(NINE, "YELLOW NINE IMAGE"),
                    entry(SKIP, "YELLOW SKIP IMAGE"),
                    entry(REVERSE, "YELLOW REVERSE IMAGE"),
                    entry(DRAW_TWO, "YELLOW DRAW TWO IMAGE"))),
            UnoSuit.WILD, new HashMap<>(Map.ofEntries(
                    entry(UnoValue.WILD, "WILD IMAGE"),
                    entry(UnoValue.WILD_DRAW_FOUR, "WILD DRAW FOUR IMAGE")))
    ));
}
