package model.player.cardplayer.unoplayer.unobrain;

import model.cardgame.card.unocard.UnoCard;
import model.cardgame.unogame.UnoGameState;

import java.util.List;

/*
This class extends UnoBrain. This class will implement its own methods to analyze the information from gameState
in order to generate an integer to represent which card should be played.

The gameState is currently missing Rules for validation, so this class is waiting on implementation.
 */

public class UnoBrainMedium extends UnoBrain {

    public UnoCard analyze(UnoGameState gameState, List<UnoCard> playableCards) {
        return null;
    }
}