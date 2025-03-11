package games.players.cardplayers.unoplayers.unobrains;

import games.cardgames.cards.unocards.UnoCard;
import games.cardgames.unogame.UnoGameState;

import java.util.List;
import java.util.Random;

/*
Team Members: Steve Wareham, Charles Davidson, Josiah Stoltzfus
Author: Josiah Stoltzfus
Date: 3/7/2025
------------------------------------------------------------------------------

This class extends UnoBrain. This class will implement its own methods to analyze the information from gameState
in order to generate an integer to represent which card should be played.

The gameState is currently missing Rules for validation, so this class is waiting on implementation. Additional comments
will be added later.
 */

public class UnoBrainEasy extends UnoBrain {

    public int analyze(UnoGameState gameState, List<UnoCard> playableCards) {
        Random random = new Random();
        UnoCard card = playableCards.get(random.nextInt(playableCards.size()));
        return playableCards.indexOf(card);
    }
}