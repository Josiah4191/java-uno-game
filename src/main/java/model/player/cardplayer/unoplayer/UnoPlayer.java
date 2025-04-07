package model.player.cardplayer.unoplayer;

import model.cardgame.card.unocard.UnoCard;
import model.cardgame.card.unocard.UnoPlayerHandPile;
import model.cardgame.unogame.UnoGameState;
import model.cardgame.unogame.UnoModerator;
import model.player.cardplayer.CardPlayer;

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

    public UnoPlayer(int playerID) {
        super(new UnoPlayerHandPile(), playerID);
    }

    public void sayUno(boolean sayUno) {
        this.sayUno = sayUno;
    }

    public boolean getSayUno() {
        return sayUno;
    }

    public boolean callUno(UnoGameState gameState, int playerIndex) {
        UnoModerator moderator = gameState.getModerator();
        return moderator.checkCallUno(gameState, playerIndex);
    }

}
