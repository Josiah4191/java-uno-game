package com.josiah.uno.multiplayer.client.clientmessage;

import com.josiah.uno.model.cardgame.card.unocard.UnoCardTheme;
import com.josiah.uno.model.cardgame.card.unocard.UnoEdition;
import com.josiah.uno.model.cardgame.unogame.Difficulty;

/*
    This class is a bean class, or data transfer object, and it is used to send information from client
    to server.

    SetupGameAction takes place when a player selects all the required settings for starting a new game.
 */


public class SetupGameAction extends GameAction {
    private UnoEdition edition; // store the user's selected edition
    private UnoCardTheme theme; // store the user's selected theme
    private Difficulty difficulty; // store the user's selected difficulty
    private int numberOfOpponents; // store the user's selected number of players


    public SetupGameAction(UnoEdition edition, UnoCardTheme theme, Difficulty difficulty, int numberOfOpponents) {
        this.edition = edition;
        this.theme = theme;
        this.difficulty = difficulty;
        this.numberOfOpponents = numberOfOpponents;
        setType(GameActionType.SETUP_GAME); // the constructor sets the action type
    }

    // get the edition
    public UnoEdition getEdition() {
        return edition;
    }

    // set the edition
    public void setEdition(UnoEdition edition) {
        this.edition = edition;
    }

    // get the theme
    public UnoCardTheme getTheme() {
        return theme;
    }

    // set the theme
    public void setTheme(UnoCardTheme theme) {
        this.theme = theme;
    }

    // get the difficulty
    public Difficulty getDifficulty() {
        return difficulty;
    }

    // set the difficulty
    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    // get the number of opponents
    public int getNumberOfOpponents() {
        return numberOfOpponents;
    }

    // set the number of opponents
    public void setNumberOfOpponents(int numberOfOpponents) {
        this.numberOfOpponents = numberOfOpponents;
    }
}
