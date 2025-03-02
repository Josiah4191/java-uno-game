package model.cards.uno;

import java.util.HashMap;
import java.util.Map;

import static java.util.Map.entry;
import static model.cards.uno.UnoSuit.*;
import static model.cards.uno.UnoValue.*;

/*
    1. This class is responsible for managing the images for our cards.
    2. When we create an UnoCardImageManager object, we can pass an UnoCard object to the getImage() method, which
       will return the image for that card.
    3. This class is created in such a way that it will allow us to add different sets of Uno card images, which I called
       themes.
    4. The default theme is set to classic, which will look something like the original Uno cards.
    5. We can pass a UnoCardTheme enum to the setTheme() method to change the theme.
    6. The getImage() method will return the image based on the theme that is set.
    7. If we want to add another theme, we will need to:
        - Add a new theme to the UnoCardTheme enum
        - Create another map that contains all the images
        - Add the new theme to the switch statement in getImage() so it pulls image from the appropriate map

    Note:
        Right now, getImage() is returning a String. That's because I don't have images yet. To actually implement the images, I
        can think of a couple options:
            - We can still return a String which will be a file path of the image.
            - We can return an Image object from the javaFX library.
            - Personally, I lean towards returning a String for the file path, and then we can deal with error handling
              further up the call stack.
            - I'm still not opposed to just returning an Image object. If we do it here, I think we'll need to add some
              error handling here.
 */

public class UnoCardImageManager {

    // Create and initialize the theme to classic
    private UnoCardTheme theme = UnoCardTheme.CLASSIC;

    public UnoCardImageManager() {
    }

    // Returns the Image. Accepts a UnoCard card object for the parameter
    public String getImage(UnoCard card) {
        switch (theme) { // Switch on the current theme
            case UnoCardTheme.CLASSIC: // If it's classic theme
                var images = classicImages.get(card.getSuit()); // Get all the map entries from the cards suit
                return images.get(card.getValue()); // Return the image that corresponds to the value of the card with that suit
            default:
                return "";
        }
    }

    // This method sets the theme of the Images to be returned.
    public void setTheme(UnoCardTheme theme) {
        this.theme = theme;
    }

    /* This is a Map that contains all the Images for each Uno card

       The outer map keys are RED, GREEN, BLUE, YELLOW, WILD.
       The outer map values are maps.

       The inner map keys correspond to the card values in their suit.
       The inner map values are the images for that value.
     */

    // @formatter:off
    private static Map<UnoSuit, Map<UnoValue, String>> classicImages = new HashMap<>(Map.of(
            RED, new HashMap<>(Map.ofEntries(
                    entry(ZERO, "RED ZERO IMAGE"),
                    entry(ONE, "RED ONE IMAGE"),
                    entry(TWO, "RED TWO IMAGE"),
                    entry(THREE, "RED THREE IMAGE"),
                    entry(FOUR, "RED FOUR IMAGE"),
                    entry(FIVE, "RED FIVE IMAGE"),
                    entry(SIX, "RED SIX IMAGE"),
                    entry(SEVEN, "RED SEVEN IMAGE"),
                    entry(EIGHT, "RED EIGHT IMAGE"),
                    entry(NINE, "RED NINE IMAGE"),
                    entry(SKIP, "RED SKIP IMAGE"),
                    entry(REVERSE, "RED REVERSE IMAGE"),
                    entry(DRAW_TWO, "RED DRAW TWO IMAGE"))),
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
    // @formatter:on
}