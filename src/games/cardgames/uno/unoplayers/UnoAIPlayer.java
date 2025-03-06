package games.cardgames.uno.unoplayers;

import games.cardgames.cardplayers.CardPlayer;
import games.cardgames.uno.UnoGameState;
import games.cardgames.uno.unocards.UnoCard;
import games.cardgames.uno.unocards.UnoPlayerHandPile;

public class UnoAIPlayer extends CardPlayer<UnoCard> {

    private UnoGameState gameState;

    public UnoAIPlayer(UnoPlayerHandPile playerHand, UnoGameState gameState) {
        super(playerHand);
        this.gameState = gameState;
    }

    // method for the AI to play a card. (no need to pass index because brain will decide)
    public UnoCard playCard() {
        int index = 0;
        return playCard(index);
    }

    // viewPlayersTotalCardsRemaining
    // analyzeGame


}

