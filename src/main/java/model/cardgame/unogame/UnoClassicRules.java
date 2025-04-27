package model.cardgame.unogame;

/*
This class defines the classic UNO rules. It implements the UnoRules interface, which is used to
define how the cards should be validated and how penalties should be handled when a player calls UNO on another player.
The rules class is used by the moderator.
*/

import model.cardgame.card.unocard.UnoCard;
import model.cardgame.card.unocard.UnoSuit;
import model.cardgame.card.unocard.UnoValue;
import model.player.cardplayer.unoplayer.UnoPlayer;

import java.io.Serializable;

public class UnoClassicRules implements UnoRules, Serializable {

    /*
        This method validates an UnoCard that a player tries to play.
        - It receives the UnoGameState object. It needs the gameState in order to get certain
          information about the game, like lastPlayedCard and currentSuit.
        - It receives the UnoCard object that the player wants to play.
     */
    public boolean validateCard(UnoGameState gameState, UnoCard card) {
        boolean match = false; // create boolean flag and set to false
        var numberOfPlayers = gameState.getPlayers().size(); // get the number of players in the game
        UnoCard lastPlayedCard = gameState.getLastPlayedCard(); // get the last played card (card ontop of discard pile)
        UnoSuit currentSuit = gameState.getCurrentSuit(); // get the current suit (the suit of the last played card)

        // If 2 players remain, SKIP works like REVERSE and REVERSE works like SKIP
        if (numberOfPlayers == 2) {
            // If the value of the card is SKIP the lastPlayedCard value is reverse
            if (card.getValue() == UnoValue.SKIP && lastPlayedCard.getValue() == UnoValue.REVERSE) {
                return true;
            } else if (card.getValue() == UnoValue.REVERSE && lastPlayedCard.getValue() == UnoValue.SKIP) {
                return true;
            }
        }

        // If last played card is wild, match is true
        if (currentSuit == UnoSuit.WILD) {
            match = true;
        }
        // If card is wild, match is true
        if (card.getSuit() == UnoSuit.WILD) {
            match = true;
        }
        // If cards same color, match is true
        if (card.getSuit() == currentSuit) {
            match = true;
        }
        // If cards same value, match is true
        if (card.getValue() == lastPlayedCard.getValue()) {
            match = true;
        }

        return match;
    }

    /*
        This method is called when a player calls UNO on another player.
        - It receives the UnoGameState object. It needs the gameState to get the UnoPlayer object
          from the given player index.
        - It receives an integer for the player index in the list of players to call UNO
        - It returns true if a penalty should be applied to the player with the given player index,
          otherwise it returns false
    */
    public boolean checkCallUno(UnoGameState gameState, int playerIndex) {
        // get the UnoPlayer object using the given player index
        UnoPlayer player = gameState.getPlayer(playerIndex);
        // get the number of cards the player has in their hand
        int cardsRemaining = player.getTotalCardsRemaining();
        // check if the player has said UNO
        boolean sayUno = player.getSayUno();
        /*
            return the result of sayUno and if the player has 1 card remaining.
            - if sayUno is false, and they have 1 card, it will return true (apply penalty)
            - if sayUno is true, and they don't have 1 card, it will return false (no penalty)
            - if sayUno is false, it will return false (no penalty)
         */
        return !sayUno && (cardsRemaining == 1);
    }

}
