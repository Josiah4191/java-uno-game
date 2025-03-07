package games.players.cardplayers.unoplayers;

import games.Difficulty;
import games.cardgames.unogame.UnoGameState;
import games.cardgames.cards.unocards.UnoCard;
import games.cardgames.cards.unocards.UnoPlayerHandPile;
import games.players.cardplayers.unoplayers.unobrains.UnoBrain;
import games.players.cardplayers.unoplayers.unobrains.UnoBrainEasy;
import games.players.cardplayers.unoplayers.unobrains.UnoBrainHard;
import games.players.cardplayers.unoplayers.unobrains.UnoBrainMedium;

public class UnoAIPlayer extends UnoPlayer {

    private UnoBrain brain;

    public UnoAIPlayer(UnoGameState gameState) {
        createBrain(gameState);
    }

    public UnoAIPlayer(UnoPlayerHandPile playerHand, UnoGameState gameState) {
        super(playerHand);
        createBrain(gameState);
    }

    @Override
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

