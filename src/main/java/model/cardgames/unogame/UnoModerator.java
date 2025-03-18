package model.cardgames.unogame;

import model.cardgames.cards.unocards.UnoCard;
import model.players.cardplayers.unoplayers.UnoPlayer;

/*
Team Members: Steve Wareham, Charles Davidson, Josiah Stoltzfus
Date: 3/7/2025
------------------------------------------------------------------------------
 */

public class UnoModerator {

    public boolean validateCard(UnoGameState gameState, UnoCard card) {
        return gameState.getRules().validateCard(gameState, card);
    }

    public boolean checkCallUno(UnoGameState gameState, UnoPlayer player) {
        return gameState.getRules().checkCallUno(player);
    }

}

/*
We need a method that we can pass a card through after it has been played or drawn.

This is the series of events for playing or drawing a card:
    - First, the card passes through validation method.
    - If the card is valid, it moves through another method (yet to  be created) to examine the card type.
        - Perhaps this method should return the VALUE of the card played.
    - We use this method inside game manager, and the game manager will take action according to the value of the card
 */
