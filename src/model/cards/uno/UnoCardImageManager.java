package model.cards.uno;

import java.util.HashMap;
import java.util.Map;

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

    public UnoCardImageManager( ) {
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
    private static Map<UnoSuit, Map<UnoValue, String>> classicImages = new HashMap<>(Map.of(
            RED, new HashMap<UnoValue, String>(Map.ofEntries(
                    Map.entry(ZERO, "RED ZERO IMAGE"),
                    Map.entry(ONE, "RED ONE IMAGE"),
                    Map.entry(TWO, "RED TWO IMAGE"),
                    Map.entry(THREE, "RED THREE IMAGE"),
                    Map.entry(FOUR, "RED FOUR IMAGE"),
                    Map.entry(FIVE, "RED FIVE IMAGE"),
                    Map.entry(SIX, "RED SIX IMAGE"),
                    Map.entry(SEVEN, "RED SEVEN IMAGE"),
                    Map.entry(EIGHT, "RED EIGHT IMAGE"),
                    Map.entry(NINE, "RED NINE IMAGE"),
                    Map.entry(SKIP, "RED SKIP IMAGE"),
                    Map.entry(REVERSE, "RED REVERSE IMAGE"),
                    Map.entry(DRAW_TWO, "RED DRAW TWO IMAGE"))),
            GREEN, new HashMap<UnoValue, String>(Map.ofEntries(
                    Map.entry(ZERO, "GREEN ZERO IMAGE"),
                    Map.entry(ONE, "GREEN ONE IMAGE"),
                    Map.entry(TWO, "GREEN TWO IMAGE"),
                    Map.entry(THREE, "GREEN THREE IMAGE"),
                    Map.entry(FOUR, "GREEN FOUR IMAGE"),
                    Map.entry(FIVE, "GREEN FIVE IMAGE"),
                    Map.entry(SIX, "GREEN SIX IMAGE"),
                    Map.entry(SEVEN, "GREEN SEVEN IMAGE"),
                    Map.entry(EIGHT, "GREEN EIGHT IMAGE"),
                    Map.entry(NINE, "GREEN NINE IMAGE"),
                    Map.entry(SKIP, "GREEN SKIP IMAGE"),
                    Map.entry(REVERSE, "GREEN REVERSE IMAGE"),
                    Map.entry(DRAW_TWO, "GREEN DRAW TWO IMAGE"))),
            BLUE, new HashMap<UnoValue, String>(Map.ofEntries(
                    Map.entry(ZERO, "BLUE ZERO IMAGE"),
                    Map.entry(ONE, "BLUE ONE IMAGE"),
                    Map.entry(TWO, "BLUE TWO IMAGE"),
                    Map.entry(THREE, "BLUE THREE IMAGE"),
                    Map.entry(FOUR, "BLUE FOUR IMAGE"),
                    Map.entry(FIVE, "BLUE FIVE IMAGE"),
                    Map.entry(SIX, "BLUE SIX IMAGE"),
                    Map.entry(SEVEN, "BLUE SEVEN IMAGE"),
                    Map.entry(EIGHT, "BLUE EIGHT IMAGE"),
                    Map.entry(NINE, "BLUE NINE IMAGE"),
                    Map.entry(SKIP, "BLUE SKIP IMAGE"),
                    Map.entry(REVERSE, "BLUE REVERSE IMAGE"),
                    Map.entry(DRAW_TWO, "BLUE DRAW TWO IMAGE"))),
            YELLOW, new HashMap<UnoValue, String>(Map.ofEntries(
                    Map.entry(ZERO, "YELLOW ZERO IMAGE"),
                    Map.entry(ONE, "YELLOW ONE IMAGE"),
                    Map.entry(TWO, "YELLOW TWO IMAGE"),
                    Map.entry(THREE, "YELLOW THREE IMAGE"),
                    Map.entry(FOUR, "YELLOW FOUR IMAGE"),
                    Map.entry(FIVE, "YELLOW FIVE IMAGE"),
                    Map.entry(SIX, "YELLOW SIX IMAGE"),
                    Map.entry(SEVEN, "YELLOW SEVEN IMAGE"),
                    Map.entry(EIGHT, "YELLOW EIGHT IMAGE"),
                    Map.entry(NINE, "YELLOW NINE IMAGE"),
                    Map.entry(SKIP, "YELLOW SKIP IMAGE"),
                    Map.entry(REVERSE, "YELLOW REVERSE IMAGE"),
                    Map.entry(DRAW_TWO, "YELLOW DRAW TWO IMAGE"))),
            UnoSuit.WILD, new HashMap<UnoValue, String>(Map.ofEntries(
                    Map.entry(UnoValue.WILD, "WILD IMAGE"),
                    Map.entry(UnoValue.WILD_DRAW_FOUR, "WILD DRAW FOUR IMAGE")))
    ));




}
