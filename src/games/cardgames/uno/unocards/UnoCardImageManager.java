package games.cardgames.uno.unocards;

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
        Right now, getImage() is returning a String for each file. That's because I don't have images yet. To actually implement the images, I
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

    // Returns the Image. Accepts a UnoCard card object for the parameter
    public String getImage(UnoCard card) {
        switch (theme) { // Switch on the current theme
            case UnoCardTheme.CLASSIC: // If it's classic theme
                return UnoCardClassicImages.IMAGES.get(card.getSuit()).get(card.getValue()); // Get the card suit key from the map of images, and then get the card value key from the inner map
            default:
                return "";
        }
    }

    // This method sets the theme of the Images to be returned.
    public void setTheme(UnoCardTheme theme) {
        theme = theme;
    }

}