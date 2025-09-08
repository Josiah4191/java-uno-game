package com.josiah.uno.multiplayer.client.clientmessage;

/*
    This enum defines the action types for the client sending messages to server.
 */
public enum GameActionType {
    PASS_TURN, DRAW_CARD, PLAY_CARD, SAY_UNO, CALL_UNO, CHANGE_NAME, CHANGE_IMAGE, JOIN_GAME, SETUP_GAME, CHANGE_SUIT, DISCONNECT
}
