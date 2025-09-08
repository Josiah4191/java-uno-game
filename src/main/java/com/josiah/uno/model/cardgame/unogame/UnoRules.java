package com.josiah.uno.model.cardgame.unogame;

/*
The UnoRules interface defines validateCard and checkCallUno methods. This is meant to be implemented by an UNO
rules class. Right now, only the classic UNO rules class has been created.
 */

import com.josiah.uno.model.cardgame.card.unocard.UnoCard;

public interface UnoRules {

    boolean validateCard(UnoGameState gameState, UnoCard card);

    boolean checkCallUno(UnoGameState gameState, int playerIndex);

}
