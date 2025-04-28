package model.player.cardplayer.unoplayer;

import model.cardgame.card.unocard.UnoCard;
import model.cardgame.card.unocard.UnoPlayerHandPile;
import model.cardgame.unogame.UnoGameState;
import model.cardgame.unogame.UnoModerator;
import model.player.cardplayer.CardPlayer;

import java.util.List;

/*
This class extends CardPlayer and represents an UNO player.

For more information about this class, refer to CardPlayer.
*/

public class UnoPlayer extends CardPlayer<UnoPlayerHandPile, UnoCard> {

    // sayUno boolean variable for whether the player said UNO
    private boolean sayUno = false;

    public UnoPlayer(int playerID) {
        super(new UnoPlayerHandPile(), playerID);
    }

    // This method is used for the player to say UNO. If the player says UNO, this will be set to true, otherwise,
    // it will be reset to false.
    public void sayUno(boolean sayUno) {
        this.sayUno = sayUno;
    }

    // This method is used to get the status of whether the player said UNO.
    public boolean getSayUno() {
        return sayUno;
    }

    /*
    // This method is used for the player to call UNO on another player. Not currently being used
    public boolean callUno(UnoGameState gameState, int playerIndex) {
        UnoModerator moderator = gameState.getModerator();
        return moderator.checkCallUno(gameState, playerIndex);
    }
     */

}
