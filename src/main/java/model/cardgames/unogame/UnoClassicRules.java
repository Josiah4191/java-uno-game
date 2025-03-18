package model.cardgames.unogame;

/*
Team Members: Steve Wareham, Charles Davidson, Josiah Stoltzfus
Date: 3/7/2025
------------------------------------------------------------------------------
*/

import model.cardgames.cards.unocards.UnoCard;
import model.cardgames.cards.unocards.UnoSuit;
import model.cardgames.cards.unocards.UnoValue;
import model.players.cardplayers.unoplayers.UnoPlayer;

public class UnoClassicRules implements UnoRules {

    public boolean validateCard(UnoGameState gameState, UnoCard card) {
        boolean match = false;
        var numberOfPlayers = gameState.getPlayers().size();
        UnoCard lastPlayedCard = gameState.getLastPlayedCard();
        UnoSuit currentSuit = gameState.getCurrentSuit();

        // If 2 players remain, SKIP works like REVERSE and REVERSE works like SKIP
        if (numberOfPlayers == 2) {
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

    public boolean checkCallUno(UnoPlayer player) {
        int cardsRemaining = player.getTotalCardsRemaining();
        boolean sayUno = player.getSayUno();

        if (!sayUno && cardsRemaining == 1) {
            return true;
        }
        return false;
    }

}
