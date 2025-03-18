package model.images.cardimages;

/*
Team Members: Steve Wareham, Charles Davidson, Josiah Stoltzfus
Date: 3/7/2025
------------------------------------------------------------------------------

This class is responsible for getting Uno card images.
The getImage method receives a Card object and returns the file path to that card's image.
The theme determines the set of images that the getImage will return from.
Additional themes for separate sets of Uno cards can easily be added.
The default theme is classic, but can be changed.

NOTE:
    The classic images are stored in a map in the UnoCardClassicImages class.
    getImage() is returning a descriptive String since there are no images yet.
 */

import model.cardgames.cards.unocards.UnoCard;
import model.cardgames.cards.unocards.UnoCardTheme;
import model.cardgames.cards.unocards.UnoSuit;
import model.cardgames.cards.unocards.UnoValue;
import model.images.ImageLogger;
import javafx.scene.image.Image;

import java.io.Serializable;
import java.net.URL;

public class UnoCardImageManager implements Serializable {

    static {
        ImageLogger.getImageLogger().info("[Card Image Log]: Loading images");
        UnoCardClassicImages.loadImages();
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
        UnoSuit suit = card.getSuit();
        UnoValue value = card.getValue();

        var innerMap = outerMap.get(suit);
        URL imageURL = innerMap.get(value);
        return new Image(imageURL.toString());
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