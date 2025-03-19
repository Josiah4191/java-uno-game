package model.players.cardplayers.unoplayers.unobrains;

import model.cardgames.cards.unocards.UnoCard;
import model.cardgames.cards.unocards.UnoSuit;
import model.cardgames.unogame.UnoGameState;

import java.util.List;
import java.util.Random;

/*
Team Members: Steve Wareham, Charles Davidson, Josiah Stoltzfus
Date: 3/7/2025
------------------------------------------------------------------------------

This class extends UnoBrain. This class will implement its own methods to analyze the information from gameState
in order to generate an integer to represent which card should be played.

The gameState is currently missing Rules for validation, so this class is waiting on implementation. Additional comments
will be added later.
 */

public class UnoBrainEasy extends UnoBrain {

    public UnoCard analyze(UnoGameState gameState, List<UnoCard> playableCards) {
        Random random = new Random();
        UnoCard card = playableCards.get(random.nextInt(playableCards.size()));
        selectSuit(gameState, card, random);

        return card;
    }

    public void selectSuit(UnoGameState gameState, UnoCard card, Random random) {
        List<UnoSuit> suits = List.of(UnoSuit.GREEN, UnoSuit.BLUE, UnoSuit.YELLOW, UnoSuit.RED);
        if (card.getSuit() == UnoSuit.WILD) {
            gameState.setCurrentSuit(suits.get(random.nextInt(suits.size())));
        }
    }
}