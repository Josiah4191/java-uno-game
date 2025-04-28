package model.player.cardplayer.unoplayer;

import model.cardgame.card.unocard.UnoCard;
import model.cardgame.unogame.UnoGameState;
import model.image.playerimage.PlayerImage;
import model.player.cardplayer.unoplayer.unobrain.UnoBrain;
import model.player.cardplayer.unoplayer.unobrain.UnoBrainFactory;

import java.util.Random;

/*
This class extends UnoPlayer. This class represents a computer player in an UNO game.
*/

public class UnoPlayerAI extends UnoPlayer {
    /*
    The UnoBrain object processes the gameState information and makes a decision for which card to play
     */
    private UnoBrain brain;
    private transient UnoGameState gameState;
    private static int ID = -1;

    /*
    The UnoPlayerAI constructor receives an UnoGameState object.
    The UnoGameState object owns the information about the state of the game.
    An AI player has an UnoBrain object.
    The createBrain method is called.
    The AI player will need information about the game to process and make a decision.
     */
    public UnoPlayerAI(UnoGameState gameState) {
        super(ID); // call super constructor and set the ID of the player
        ID++;
        this.gameState = gameState; // set the game state
        setIsAI(true); // set isAI to true
        createBrain(); // create brain
        setImage(); // set the image will randomly select an image from the list of images from the PlayerImage enum type
    }

    // This method is used for the AI player to select a card from the playable cards
    public UnoCard selectCard() {
        var playableCards = gameState.getPlayableCards(); // get playableCards from gameState
        if (!playableCards.isEmpty()) { // check if the playableCards list is empty
            return brain.analyze(gameState, playableCards); // if it isn't empty, use brain to select a card
        } else {
            // if there are no playableCards in the list of playable cards, return null
            // this is checked later in the program. if this method returns null, the player will draw a card
            return null;
        }
    }

    // This method sets the image for the AI player. It selects a random image from the PlayerImage enum types
    public void setImage() {
        PlayerImage[] images = PlayerImage.values(); // Get an array of all the values from PlayerImage enum
        PlayerImage image = images[new Random().nextInt(images.length)]; // Select a random image from the images array
        super.setImage(image); // set the image for the player
        setName("[B]" + getName()); // add [B] to the player to identify them as a bot
    }

    // This method creates the brain based on the difficulty that the user selected. Currently there is only an easy
    // brain that can be selected.
    public void createBrain() {
        this.brain = UnoBrainFactory.createBrain(gameState.getDifficulty());
    }
}


