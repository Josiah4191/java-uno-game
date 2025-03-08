package games.players.cardplayers.unoplayers;

import games.cardgames.cards.unocards.UnoCard;
import games.cardgames.cards.unocards.UnoPlayerHandPile;
import games.players.cardplayers.CardPlayer;

/*
Last edited: Josiah Stoltzfus
Date: 3/7/2025
    - Initial version - First time editing. Future edits and comments will be noted here. Please
    include your name and date.

Author: Josiah Stoltzfus
Date: 3/7/2025
------------------------------------------------------------------------------

This class extends CardPlayer and represents an UNO player.

For more information about this class, refer to CardPlayer.
*/

public class UnoPlayer extends CardPlayer<UnoPlayerHandPile, UnoCard> {
    /*
    The UnoPlayer constructor assigns its playerHand Pile to a new UnoPlayerHandPile.
    The new UnoPlayerHandPile represents the player's Pile cards.
    The new UnoPlayerHandPile starts empty.
     */

    private boolean sayUno = false;

    public UnoPlayer() {
        this(new UnoPlayerHandPile());
    }

    public UnoPlayer(UnoPlayerHandPile playerHand) {
        super(playerHand);
    }

    public void setSayUno(boolean sayUno) {
        this.sayUno = sayUno;
    }

    public boolean getSayUno() {
        return sayUno;
    }


}
