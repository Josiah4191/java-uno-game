package games.cardgames.cards.unocards;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static games.cardgames.cards.unocards.UnoSuit.*;
import static games.cardgames.cards.unocards.UnoValue.*;
import static java.util.Map.entry;

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


    public static final UnoCard DECK = new UnoCard(GENERAL, UnoValue.DECK);
    public static final UnoCard LOGO = new UnoCard(GENERAL, UnoValue.LOGO);

    protected static final Map<UnoSuit, Map<UnoValue, URL>> CARD_IMAGES = new HashMap<>(Map.of(
            RED, new HashMap<>(Map.ofEntries(
                    entry(ZERO, Objects.requireNonNull(UnoCardClassicImages.class.getResource("/images/cardimages/Red_0.png"))),
                    entry(ONE, Objects.requireNonNull(UnoCardClassicImages.class.getResource("/images/cardimages/Red_1.png"))),
                    entry(TWO, Objects.requireNonNull(UnoCardClassicImages.class.getResource("/images/cardimages/Red_2.png"))),
                    entry(THREE, Objects.requireNonNull(UnoCardClassicImages.class.getResource("/images/cardimages/Red_3.png"))),
                    entry(FOUR, Objects.requireNonNull(UnoCardClassicImages.class.getResource("/images/cardimages/Red_4.png"))),
                    entry(FIVE, Objects.requireNonNull(UnoCardClassicImages.class.getResource("/images/cardimages/Red_5.png"))),
                    entry(SIX, Objects.requireNonNull(UnoCardClassicImages.class.getResource("/images/cardimages/Red_6.png"))),
                    entry(SEVEN, Objects.requireNonNull(UnoCardClassicImages.class.getResource("/images/cardimages/Red_7.png"))),
                    entry(EIGHT, Objects.requireNonNull(UnoCardClassicImages.class.getResource("/images/cardimages/Red_8.png"))),
                    entry(NINE, Objects.requireNonNull(UnoCardClassicImages.class.getResource("/images/cardimages/Red_9.png"))),
                    entry(SKIP, Objects.requireNonNull(UnoCardClassicImages.class.getResource("/images/cardimages/Red_Skip.png"))),
                    entry(REVERSE, Objects.requireNonNull(UnoCardClassicImages.class.getResource("/images/cardimages/Red_Reverse.png"))),
                    entry(DRAW_TWO, Objects.requireNonNull(UnoCardClassicImages.class.getResource("/images/cardimages/Red_Draw.png"))))),
            GREEN, new HashMap<>(Map.ofEntries(
                    entry(ZERO, Objects.requireNonNull(UnoCardClassicImages.class.getResource("/images/cardimages/Green_0.png"))),
                    entry(ONE, Objects.requireNonNull(UnoCardClassicImages.class.getResource("/images/cardimages/Green_1.png"))),
                    entry(TWO, Objects.requireNonNull(UnoCardClassicImages.class.getResource("/images/cardimages/Green_2.png"))),
                    entry(THREE, Objects.requireNonNull(UnoCardClassicImages.class.getResource("/images/cardimages/Green_3.png"))),
                    entry(FOUR, Objects.requireNonNull(UnoCardClassicImages.class.getResource("/images/cardimages/Green_4.png"))),
                    entry(FIVE, Objects.requireNonNull(UnoCardClassicImages.class.getResource("/images/cardimages/Green_5.png"))),
                    entry(SIX, Objects.requireNonNull(UnoCardClassicImages.class.getResource("/images/cardimages/Green_6.png"))),
                    entry(SEVEN, Objects.requireNonNull(UnoCardClassicImages.class.getResource("/images/cardimages/Green_7.png"))),
                    entry(EIGHT, Objects.requireNonNull(UnoCardClassicImages.class.getResource("/images/cardimages/Green_8.png"))),
                    entry(NINE, Objects.requireNonNull(UnoCardClassicImages.class.getResource("/images/cardimages/Green_9.png"))),
                    entry(SKIP, Objects.requireNonNull(UnoCardClassicImages.class.getResource("/images/cardimages/Green_Skip.png"))),
                    entry(REVERSE, Objects.requireNonNull(UnoCardClassicImages.class.getResource("/images/cardimages/Green_Reverse.png"))),
                    entry(DRAW_TWO, Objects.requireNonNull(UnoCardClassicImages.class.getResource("/images/cardimages/Green_Draw.png"))))),
            BLUE, new HashMap<>(Map.ofEntries(
                    entry(ZERO, Objects.requireNonNull(UnoCardClassicImages.class.getResource("/images/cardimages/Blue_0.png"))),
                    entry(ONE, Objects.requireNonNull(UnoCardClassicImages.class.getResource("/images/cardimages/Blue_1.png"))),
                    entry(TWO, Objects.requireNonNull(UnoCardClassicImages.class.getResource("/images/cardimages/Blue_2.png"))),
                    entry(THREE, Objects.requireNonNull(UnoCardClassicImages.class.getResource("/images/cardimages/Blue_3.png"))),
                    entry(FOUR, Objects.requireNonNull(UnoCardClassicImages.class.getResource("/images/cardimages/Blue_4.png"))),
                    entry(FIVE, Objects.requireNonNull(UnoCardClassicImages.class.getResource("/images/cardimages/Blue_5.png"))),
                    entry(SIX, Objects.requireNonNull(UnoCardClassicImages.class.getResource("/images/cardimages/Blue_6.png"))),
                    entry(SEVEN, Objects.requireNonNull(UnoCardClassicImages.class.getResource("/images/cardimages/Blue_7.png"))),
                    entry(EIGHT, Objects.requireNonNull(UnoCardClassicImages.class.getResource("/images/cardimages/Blue_8.png"))),
                    entry(NINE, Objects.requireNonNull(UnoCardClassicImages.class.getResource("/images/cardimages/Blue_9.png"))),
                    entry(SKIP, Objects.requireNonNull(UnoCardClassicImages.class.getResource("/images/cardimages/Blue_Skip.png"))),
                    entry(REVERSE, Objects.requireNonNull(UnoCardClassicImages.class.getResource("/images/cardimages/Blue_Reverse.png"))),
                    entry(DRAW_TWO, Objects.requireNonNull(UnoCardClassicImages.class.getResource("/images/cardimages/Blue_Draw.png"))))),
            YELLOW, new HashMap<>(Map.ofEntries(
                    entry(ZERO, Objects.requireNonNull(UnoCardClassicImages.class.getResource("/images/cardimages/Yellow_0.png"))),
                    entry(ONE, Objects.requireNonNull(UnoCardClassicImages.class.getResource("/images/cardimages/Yellow_1.png"))),
                    entry(TWO, Objects.requireNonNull(UnoCardClassicImages.class.getResource("/images/cardimages/Yellow_2.png"))),
                    entry(THREE, Objects.requireNonNull(UnoCardClassicImages.class.getResource("/images/cardimages/Yellow_3.png"))),
                    entry(FOUR, Objects.requireNonNull(UnoCardClassicImages.class.getResource("/images/cardimages/Yellow_4.png"))),
                    entry(FIVE, Objects.requireNonNull(UnoCardClassicImages.class.getResource("/images/cardimages/Yellow_5.png"))),
                    entry(SIX, Objects.requireNonNull(UnoCardClassicImages.class.getResource("/images/cardimages/Yellow_6.png"))),
                    entry(SEVEN, Objects.requireNonNull(UnoCardClassicImages.class.getResource("/images/cardimages/Yellow_7.png"))),
                    entry(EIGHT, Objects.requireNonNull(UnoCardClassicImages.class.getResource("/images/cardimages/Yellow_8.png"))),
                    entry(NINE, Objects.requireNonNull(UnoCardClassicImages.class.getResource("/images/cardimages/Yellow_9.png"))),
                    entry(SKIP, Objects.requireNonNull(UnoCardClassicImages.class.getResource("/images/cardimages/Yellow_Skip.png"))),
                    entry(REVERSE, Objects.requireNonNull(UnoCardClassicImages.class.getResource("/images/cardimages/Yellow_Reverse.png"))),
                    entry(DRAW_TWO, Objects.requireNonNull(UnoCardClassicImages.class.getResource("/images/cardimages/Yellow_Draw.png"))))),
            UnoSuit.WILD, new HashMap<>(Map.ofEntries(
                    entry(UnoValue.WILD, Objects.requireNonNull(UnoCardClassicImages.class.getResource("/images/cardimages/Wild.png"))),
                    entry(UnoValue.WILD_DRAW_FOUR, Objects.requireNonNull(UnoCardClassicImages.class.getResource("/images/cardimages/Wild_Draw.png"))))),
            GENERAL, new HashMap<>(Map.ofEntries(
                    entry(UnoValue.DECK, Objects.requireNonNull(UnoCardClassicImages.class.getResource("/images/cardimages/Deck.png"))),
                    entry(UnoValue.LOGO, Objects.requireNonNull(UnoCardClassicImages.class.getResource("/images/cardimages/Logo.png")))
            ))
    ));
}

