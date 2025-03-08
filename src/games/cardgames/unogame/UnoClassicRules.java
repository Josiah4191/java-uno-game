package games.cardgames.unogame;

/*
Last edited: Josiah Stoltzfus
Date: 3/7/2025
    - Initial version - First time editing. Future edits and comments will be noted here. Please
    include your name and date.

Author: Josiah Stoltzfus
Date: 3/7/2025
------------------------------------------------------------------------------
*/

import games.cardgames.cards.unocards.UnoCard;
import games.cardgames.cards.unocards.UnoSuit;
import games.cardgames.cards.unocards.UnoValue;
import games.players.cardplayers.unoplayers.UnoPlayer;

public class UnoClassicRules implements UnoRules {

    public boolean validatePlay(UnoCard card, UnoCard lastPlayedCard) {
        boolean match = false;

        // If card is wild, match is true
        if (card.getSuit() == UnoSuit.WILD) {
            match = true;
        }
        // If cards same color, match is true
        if (card.getSuit() == lastPlayedCard.getSuit()) {
            match = true;
        }
        // If cards same value, match is true
        if (card.getValue() == lastPlayedCard.getValue()) {
            match = true;
        }

        return match;
    }

    public int getCallUnoPenalty(UnoPlayer player) {
        int cardsRemaining = player.getTotalCardsRemaining();

        if (cardsRemaining > 1) {
            return 2;
        }

        return 0;
    }

    public int getCompareCardPenalty(UnoPlayer player, UnoCard lastPlayedCard) {
        UnoValue value = lastPlayedCard.getValue();

        if (value == UnoValue.DRAW_TWO) {
            return 2;
        } else if (value == UnoValue.WILD_DRAW_FOUR) {
            return 4;
        }

        return 0;
    }

}
