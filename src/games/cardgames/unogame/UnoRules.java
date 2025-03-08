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
import games.players.cardplayers.unoplayers.UnoPlayer;

public interface UnoRules {

    boolean validatePlay(UnoCard card, UnoCard lastPlayedCard);

    int getCallUnoPenalty(UnoPlayer player);

    int getCompareCardPenalty(UnoPlayer player, UnoCard lastPlayedCard);

}
