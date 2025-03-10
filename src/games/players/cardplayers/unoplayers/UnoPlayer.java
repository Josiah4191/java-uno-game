package games.players.cardplayers.unoplayers;

import games.cardgames.cards.unocards.UnoCard;
import games.cardgames.cards.unocards.UnoPlayerHandPile;
import games.players.cardplayers.CardPlayer;

/*
Team Members: Steve Wareham, Charles Davidson, Josiah Stoltzfus
Author: Josiah Stoltzfus
Date: 3/7/2025
------------------------------------------------------------------------------

This class extends CardPlayer and represents an UNO player.

For more information about this class, refer to CardPlayer.
*/

public class UnoPlayer extends CardPlayer<UnoPlayerHandPile, UnoCard> {

    private boolean sayUno = false;

    /*
    The UnoPlayer constructor assigns its playerHand Pile to a new UnoPlayerHandPile.
    The new UnoPlayerHandPile represents the player's Pile cards.
    The new UnoPlayerHandPile starts empty.
     */
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
