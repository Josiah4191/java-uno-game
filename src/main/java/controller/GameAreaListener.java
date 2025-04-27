package controller;

import multiplayer.server.servermessage.GameEvent;

/*
    This interface is implemented by the GameAreaController so that it can listen to events that happen in the game manager
    and update the game view when the game state is updated.
 */

public interface GameAreaListener {
    void updateGameView(GameEvent event);
}
