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

/*
We need a method that we can pass a card through after it has been played or drawn.

This is the series of events for playing or drawing a card:
    - First, the card passes through validation method.
    - If the card is valid, it moves through another method (yet to  be created) to examine the card type.
        - Perhaps this method should return the VALUE of the card played.
    - We use this method inside game manager, and the game manager will take action according to the value of the card
 */
