package model.cardgame.unogame;

/*
Team Members: Steve Wareham, Charles Davidson, Josiah Stoltzfus
Date: 3/7/2025
------------------------------------------------------------------------------
 */

import model.cardgame.card.unocard.UnoCard;

public interface UnoRules {

    boolean validateCard(UnoGameState gameState, UnoCard card);

    boolean checkCallUno(UnoGameState gameState, int playerIndex);

}
