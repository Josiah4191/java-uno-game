package games.players.cardplayers.unoplayers;

import games.Difficulty;
import games.cardgames.cards.unocards.UnoCard;
import games.cardgames.cards.unocards.UnoPlayerHandPile;
import games.cardgames.unogame.UnoGameState;
import games.players.cardplayers.CardPlayer;
import games.players.cardplayers.unoplayers.unobrains.*;

/*
Team Members: Steve Wareham, Charles Davidson, Josiah Stoltzfus
Author: Josiah Stoltzfus
Date: 3/7/2025
------------------------------------------------------------------------------

This class extends UnoPlayer. This class represents a computer player in an UNO game.
*/

public class UnoPlayerAI extends UnoPlayer {
    /*
    The UnoBrain object processes the gameState information and makes a decision for which card to play
     */
    private UnoBrain brain;
    private UnoGameState gameState;

    /*
    The UnoPlayerAI constructor receives an UnoGameState object.
    The UnoGameState object owns the information about the state of the game.
    An AI player has an UnoBrain object.
    The createBrain method is called.
    The AI player will need information about the game to process and make a decision.
     */
    public UnoPlayerAI(UnoGameState gameState) {
        this.gameState = gameState;
        brain = UnoBrainFactory.createBrain(gameState);
    }
    /*
    The playCard() method returns an UnoCard.
    The playCard(int index) method is defined in CardPlayer.
    The brain analyze() method returns an integer to select which card to return.
     */
    @Override
    public UnoCard playCard(int cardIndex) {
        return playCard(brain.analyze(gameState));
    }

}


