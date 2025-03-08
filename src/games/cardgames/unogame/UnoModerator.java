package games.cardgames.unogame;

import games.Moderator;
import games.cardgames.cards.unocards.UnoCard;
import games.players.cardplayers.unoplayers.UnoPlayer;

/*
Team Members: Steve Wareham, Charles Davidson, Josiah Stoltzfus
Author: Josiah Stoltzfus
Date: 3/7/2025
------------------------------------------------------------------------------
 */

public class UnoModerator extends Moderator {

    public boolean validatePlay(UnoRules rules, UnoCard card, UnoCard lastPlayedCard) {
        return rules.validatePlay(card, lastPlayedCard);
    }

    public int callUnoPenalty(UnoRules rules, UnoPlayer player) {
        return rules.getCallUnoPenalty(player);
    }

    public int getCardPenalty(UnoRules rules, UnoCard lastPlayedCard, UnoPlayer player) {
        return rules.getCompareCardPenalty(player, lastPlayedCard);
    }
}
