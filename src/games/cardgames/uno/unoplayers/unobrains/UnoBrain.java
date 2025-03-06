package games.cardgames.uno.unoplayers.unobrains;

import games.cardgames.cardplayers.CardBrain;
import games.cardgames.uno.UnoGameState;

public abstract class UnoBrain extends CardBrain {

    private UnoGameState gameState;

    public UnoBrain(UnoGameState gameState) {
        this.gameState = gameState;
    }

    public abstract int analyze();
}
