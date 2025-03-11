package games.players.cardplayers.unoplayers.unobrains;

import games.Difficulty;

public class UnoBrainFactory {
    /*
    This method creates the appropriate brain object that corresponds to the selected difficulty.
    The gameState object provides the difficulty.
    The method switches on the selected difficulty and creates a brain.
    */
    public static UnoBrain createBrain(Difficulty difficulty) {
        switch (difficulty) {
            case MEDIUM:
                return new UnoBrainMedium();
            case HARD:
                return new UnoBrainHard();
            default:
                return new UnoBrainEasy();
        }
    }
}