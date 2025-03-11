package games.cardgames.cards.unocards;

/*
Team Members: Steve Wareham, Charles Davidson, Josiah Stoltzfus
Author: Josiah Stoltzfus
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

import javafx.scene.image.Image;

import java.net.URL;
import java.util.Objects;

public class UnoCardImageManager {

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

    public void loadImages() {
        // get map of images
        try {
            var map = UnoCardClassicImages.CARD_IMAGES;
            System.out.println("Images loaded successfully.");
        } catch (ExceptionInInitializerError e)  {
            System.out.println("Images failed to load.");
        }
    }


    public Image getClassicImage(UnoCard card) {
        // Get card suit key from map of images, and then get card value key from inner map.
        var imageMap = UnoCardClassicImages.CARD_IMAGES;
        UnoSuit suit = card.getSuit();
        UnoValue value = card.getValue();

        var innerMap = imageMap.get(suit);
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