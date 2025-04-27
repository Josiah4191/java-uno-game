package model.cardgame.unogame;

/*
Team Members: Steve Wareham, Charles Davidson, Josiah Stoltzfus
Date: 3/7/2025
------------------------------------------------------------------------------

The UnoRules interface defines validateCard and checkCallUno methods. This is meant to be implemented by an UNO
rules class. Right now, only the classic UNO rules class has been created.
 */

import model.cardgame.card.unocard.UnoCard;

public interface UnoRules {

    boolean validateCard(UnoGameState gameState, UnoCard card);

    boolean checkCallUno(UnoGameState gameState, int playerIndex);

}
