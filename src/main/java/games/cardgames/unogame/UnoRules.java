package games.cardgames.unogame;

/*
Team Members: Steve Wareham, Charles Davidson, Josiah Stoltzfus
Date: 3/7/2025
------------------------------------------------------------------------------
 */

import games.cardgames.cards.unocards.UnoCard;
import games.cardgames.cards.unocards.UnoValue;
import games.players.cardplayers.unoplayers.UnoPlayer;

public interface UnoRules {

    boolean validateCard(UnoGameState gameState, UnoCard card);

    boolean checkCallUno(UnoPlayer player);

    UnoValue evaluateCardValue(UnoCard card);

}
