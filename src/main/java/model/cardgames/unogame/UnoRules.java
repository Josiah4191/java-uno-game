package model.cardgames.unogame;

/*
Team Members: Steve Wareham, Charles Davidson, Josiah Stoltzfus
Date: 3/7/2025
------------------------------------------------------------------------------
 */

import model.cardgames.cards.unocards.UnoCard;
import model.cardgames.cards.unocards.UnoValue;
import model.players.cardplayers.unoplayers.UnoPlayer;

import java.io.Serializable;

public interface UnoRules {

    boolean validateCard(UnoGameState gameState, UnoCard card);

    boolean checkCallUno(UnoPlayer player);

}
