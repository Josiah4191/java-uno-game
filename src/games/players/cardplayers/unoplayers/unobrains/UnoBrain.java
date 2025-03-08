package games.players.cardplayers.unoplayers.unobrains;

import games.players.cardplayers.CardBrain;
import games.cardgames.unogame.UnoGameState;

/*
Team Members: Steve Wareham, Charles Davidson, Josiah Stoltzfus
Author: Josiah Stoltzfus
Date: 3/7/2025
------------------------------------------------------------------------------

This abstract class extends Brain. This abstract class represents a brain that is used to process information
about the UNO game.

The subclasses of this class are meant to represent various levels of intelligence.

The brain needs to be passed a GameState object which contains information about the state of the game. It needs this
information to process and make a decision for which card it should play.

This class has one abstract method that must be implemented by its subclass: analyze()

NOTE:
    - It might be worth refactoring UnoBrain or Brain to be an interface, since they have a single method.
    - However, it currently stores the gameState, so that may not be necessary, or that will need reworked.
    - This can be revisited at a later time.
*/

public abstract class UnoBrain extends CardBrain {

    /*
    The UnoGameState object contains information about the state of the UNO game.
    */
    private UnoGameState gameState;

    public UnoBrain(UnoGameState gameState) {
        this.gameState = gameState;
    }

    /*
    The analyze() method must be subclassed. It analyzes the gameState and returns an integer which represents
    which card the AI chooses to play.
        - Internally, each subclass will implement its own methods for processing the gameState object. Those methods
        will be called by their analyze() method to generate an integer to return.
     */
    public abstract int analyze();
}
