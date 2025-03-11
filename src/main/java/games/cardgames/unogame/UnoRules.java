package games.cardgames.unogame;

/*
Team Members: Steve Wareham, Charles Davidson, Josiah Stoltzfus
Author: Josiah Stoltzfus
Date: 3/7/2025
------------------------------------------------------------------------------
 */

import games.cardgames.cards.unocards.UnoCard;
import games.players.cardplayers.unoplayers.UnoPlayer;

public interface UnoRules {

    boolean validatePlay(UnoCard card, UnoCard lastPlayedCard);

    int getCallUnoPenalty(UnoPlayer player);

    int getCompareCardPenalty(UnoPlayer player, UnoCard lastPlayedCard);

}
