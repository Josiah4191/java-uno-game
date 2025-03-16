package model.players.cardplayers.unoplayers;

import model.cardgames.cards.unocards.UnoCard;
import model.cardgames.cards.unocards.UnoPlayerHandPile;
import model.cardgames.unogame.UnoGameState;
import model.players.cardplayers.CardPlayer;

import java.util.List;

/*
Team Members: Steve Wareham, Charles Davidson, Josiah Stoltzfus
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

    public List<UnoCard> getPlayableCards(UnoGameState gameState) {
        return getPlayerHand()
                .stream()
                .filter(e -> gameState.getModerator().validateCard(gameState, e))
                .toList();

    }

    public void setSayUno(boolean sayUno) {
        this.sayUno = sayUno;
    }

    public boolean getSayUno() {
        return sayUno;
    }



}
