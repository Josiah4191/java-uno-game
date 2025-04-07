package model.player.cardplayer.unoplayer;

import model.cardgame.card.unocard.UnoCard;
import model.cardgame.unogame.UnoGameState;
import model.image.playerimage.PlayerImage;
import model.player.cardplayer.unoplayer.unobrain.UnoBrain;
import model.player.cardplayer.unoplayer.unobrain.UnoBrainFactory;

import java.util.Random;

/*
Team Members: Steve Wareham, Charles Davidson, Josiah Stoltzfus
Date: 3/7/2025
------------------------------------------------------------------------------

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
        super(ID);
        ID++;
        this.gameState = gameState;
        setIsAI(true);
        createBrain();
        setImage();
    }

    /*
    The playCard() method returns an UnoCard.
    The playCard(int index) method is defined in CardPlayer.
    The brain analyze() method returns an integer to select which card to return.
     */

    public UnoCard selectCard() {
        var playableCards = gameState.getPlayableCards();
        if (!playableCards.isEmpty()) {
            return brain.analyze(gameState, playableCards);
        } else {
            return null;
        }
    }

    public void setImage() {
        PlayerImage[] images = PlayerImage.values();
        PlayerImage image = images[new Random().nextInt(images.length)];
        super.setImage(image);
        setName("[B]" + getName());
    }

    /*
     It might make sense to define this method in game manager, so that it could work for any player's
     hand of cards.
     OR we need to define it inside the moderator class.
     We can pass the method a player's name, and then check their cards against the last played card.
     Then we can return the list of cards that are playable.
     If the player tries to play a card not in the list of playable cards, then we prevent it.
    */

    public void createBrain() {
        this.brain = UnoBrainFactory.createBrain(gameState.getDifficulty());
    }
}


