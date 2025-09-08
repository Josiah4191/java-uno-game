package com.josiah.uno.model.player.cardplayer.unoplayer;

/*
    This class extends UnoPlayer. The main change for this class is that setIsAI is automatically
    set to false because this class is used to refer to real players in the game.
 */
public class UnoHumanPlayer extends UnoPlayer {

    public UnoHumanPlayer(int playerID) {
        super(playerID);
        setIsAI(false);
    }

}
