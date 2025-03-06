package games.players.cardplayers.unoplayers.unobrains;

import games.players.cardplayers.CardBrain;
import games.cardgames.unogame.UnoGameState;

public abstract class UnoBrain extends CardBrain {

    private UnoGameState gameState;

    public UnoBrain(UnoGameState gameState) {
        this.gameState = gameState;
    }

    public abstract int analyze();
}
