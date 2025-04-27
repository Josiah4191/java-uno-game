package model.cardgame.unogame;

import model.cardgame.card.unocard.UnoCard;

import java.io.Serializable;

/*
Team Members: Steve Wareham, Charles Davidson, Josiah Stoltzfus
Date: 3/7/2025
------------------------------------------------------------------------------

This class is used for validating cards and checking call UNO on other players. The game manager uses the moderator to
validate the plays.

The UnoModerator uses a set of rules to validate the cards. It needs the game state in order to determine which set of
rules is being used. Right now, only classic UNO rules have been defined, but this allows other rules to be implemented
at a later time.

The UnoModerator gets the rules from the UnoGameState and then calls the validateCard and checkCallUno based on the
rules that are set.
 */

public class UnoModerator implements Serializable {

    /*
        The validateCard method needs the gameState to get the rules, and it needs to pass the gameState to the
        validateCard method for the rules to process.

        The rules class also needs to know which card to validate.
     */
    public boolean validateCard(UnoGameState gameState, UnoCard card) {
        return gameState.getRules().validateCard(gameState, card);
    }

    /*
    The checkCallUno method needs the gameState to get the rules, and it needs to pass the gameState to the
    checkCallUno method for the rules to process.

    The rules class also needs to know which player to call UNO on.
 */
    public boolean checkCallUno(UnoGameState gameState, int playerIndex) {
        return gameState.getRules().checkCallUno(gameState, playerIndex);
    }

}
