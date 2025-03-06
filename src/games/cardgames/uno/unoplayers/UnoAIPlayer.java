package games.cardgames.uno.unoplayers;

import games.Difficulty;
import games.cardgames.uno.UnoGameState;
import games.cardgames.uno.unocards.UnoCard;
import games.cardgames.uno.unocards.UnoPlayerHandPile;
import games.cardgames.uno.unoplayers.unobrains.UnoBrain;
import games.cardgames.uno.unoplayers.unobrains.UnoBrainEasy;
import games.cardgames.uno.unoplayers.unobrains.UnoBrainHard;
import games.cardgames.uno.unoplayers.unobrains.UnoBrainMedium;

public class UnoAIPlayer extends UnoPlayer {

    private UnoBrain brain;

    public UnoAIPlayer(UnoGameState gameState) {
        createBrain(gameState);
    }

    public UnoAIPlayer(UnoPlayerHandPile playerHand, UnoGameState gameState) {
        super(playerHand);
        createBrain(gameState);
    }

    public UnoCard playCard() {
        return playCard(brain.analyze());
    }

    public void createBrain(UnoGameState gameState) {
        switch (gameState.getDifficulty()) {
            case Difficulty.EASY:
                brain = new UnoBrainEasy(gameState);
            case Difficulty.MEDIUM:
                brain = new UnoBrainMedium(gameState);
            case Difficulty.HARD:
                brain = new UnoBrainHard(gameState);
        }
        
    }
}

