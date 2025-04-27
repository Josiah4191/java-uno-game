package model.image.cardimage;

/*
This class is responsible for getting Uno card images.
The getImage method receives a Card object and returns an Image object for that card.
The theme determines the set of images that the getImage will return from.
Additional themes for separate sets of Uno cards can easily be added.
The default theme is classic, but can be changed.
 */

import model.cardgame.card.unocard.UnoCard;
import model.cardgame.card.unocard.UnoCardTheme;
import model.cardgame.card.unocard.UnoSuit;
import model.cardgame.card.unocard.UnoValue;
import model.image.ImageLogger;
import javafx.scene.image.Image;

import java.net.URL;

public class UnoCardImageManager {

    // This static initialization block loads the images as soon as the Card Image Manager is created
    static {
        ImageLogger.getImageLogger().info("[Card Image Log]: Loading images"); // log that the images are being loaded
        UnoCardClassicImages.loadImages(); // load the images
    }

    // Variable for the theme.
    private UnoCardTheme theme = UnoCardTheme.CLASSIC;

    // Receives an UnoCard object and gets the file path as a String for that card's image.
    public Image getImage(UnoCard card) {
        // Switch on the selected theme.
        switch (theme) {
            case CLASSIC:
            return getClassicImage(card);
            default:
                return getClassicImage(UnoCardClassicImages.DECK);
        }
    }

    public Image getClassicImage(UnoCard card) {
        // Get card suit key from map of images, and then get card value key from inner map.
        var outerMap = UnoCardClassicImages.getClassicCardImages();
        UnoSuit suit = card.getSuit(); // get the suit
        UnoValue value = card.getValue(); // get the value

        var innerMap = outerMap.get(suit);
        URL imageURL = innerMap.get(value);
        return new Image(imageURL.toString()); // create and return new Image object for the given UnoCard
    }

    // Gets the theme.
    public UnoCardTheme getTheme() {
        return theme;
    }

    // Sets the theme.
    public void setTheme(UnoCardTheme theme) {
        this.theme = theme;
    }

}