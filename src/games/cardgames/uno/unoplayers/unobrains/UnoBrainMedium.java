package games.cardgames.uno.unoplayers.unobrains;

import games.cardgames.cardplayers.CardBrain;
import games.cardgames.uno.UnoGameState;

public class UnoBrainMedium  extends UnoBrain {

    public UnoBrainMedium(UnoGameState gameState) {
        super(gameState);
    }

    public int analyze() {
        return 0;
    }
}

    /*
        needs to know how many players - complete
        needs to know how many cards each player has - complete
        needs to know its own hand of cards - complete
        needs to know the rules
        needs to know the difficulty level
        needs to know what the current card is - complete
     */