package model.player.cardplayer.unoplayer.unobrain;

import model.cardgame.card.unocard.UnoCard;
import model.cardgame.card.unocard.UnoSuit;
import model.cardgame.unogame.UnoGameState;

import java.util.List;
import java.util.Random;

/*
This class extends UnoBrain.

This class implements the analyze() method to analyze the information from gameState
in order to generate an integer to determine which card should be played.
 */

public class UnoBrainEasy extends UnoBrain {

    // The analyze method receives UnoGameState object and a list of playable cards
    public UnoCard analyze(UnoGameState gameState, List<UnoCard> playableCards) {
        // Create Random object
        Random random = new Random();
        // Use the random object to select a random item from the list of playable cards
        UnoCard card = playableCards.get(random.nextInt(playableCards.size()));
        // Call the select suit method to set a random suit if the card is a Wild card
        selectSuit(gameState, card, random);
        // Return the UnoCard object that is selected
        return card;
    }

    // This method is used to select a random new Suit if the card that is selected is a Wild card
    public void selectSuit(UnoGameState gameState, UnoCard card, Random random) {
        // Create a list of suits that can be changed if a Wild card is played (green blue, yellow, red)
        List<UnoSuit> suits = List.of(UnoSuit.GREEN, UnoSuit.BLUE, UnoSuit.YELLOW, UnoSuit.RED);
        // Check if the card is a Wild card.
        if (card.getSuit() == UnoSuit.WILD) {
            // If the card is a wild card, set the current suit to a randomly selected item in the suits list.
            gameState.setCurrentSuit(suits.get(random.nextInt(suits.size())));
        }
    }
}