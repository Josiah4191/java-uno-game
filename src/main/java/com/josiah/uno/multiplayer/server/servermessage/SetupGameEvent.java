package com.josiah.uno.multiplayer.server.servermessage;

import com.josiah.uno.model.cardgame.card.unocard.UnoCard;
import com.josiah.uno.model.cardgame.card.unocard.UnoCardTheme;
import com.josiah.uno.model.cardgame.card.unocard.UnoEdition;
import com.josiah.uno.model.cardgame.card.unocard.UnoSuit;
import com.josiah.uno.model.cardgame.unogame.Difficulty;
import com.josiah.uno.model.player.cardplayer.unoplayer.UnoPlayer;

import java.util.List;

/*
    This class is a bean class, or data transfer object, and it is used to send information from server
    to client.

    SetupGameEvent takes place when the server has received a SetupGameAction from the client, and the server uses the
    information from that message to start the game.
 */

public class SetupGameEvent extends GameEvent {

    private Difficulty difficulty; // store the game difficulty
    private UnoCardTheme theme; // store the game theme
    private UnoEdition edition; // store the edition
    private UnoSuit currentSuit; // store the current suit
    private UnoCard lastPlayedCard; // store the last played card
    private List<UnoPlayer> players; // store the list of players
    private List<UnoCard> localPlayerCards; // store the list of cards for the local player

    public SetupGameEvent(UnoCardTheme theme, UnoEdition edition, Difficulty difficulty, UnoSuit currentSuit, UnoCard lastPlayedCard, List<UnoPlayer> players, List<UnoCard> localPlayerCards) {
        this.theme = theme;
        this.edition = edition;
        this.difficulty = difficulty;
        this.currentSuit = currentSuit;
        this.lastPlayedCard = lastPlayedCard;
        this.players = players;
        this.localPlayerCards = localPlayerCards;
        setType(GameEventType.SETUP_GAME); // the constructor sets the event type
    }

    // get the theme
    public UnoCardTheme getTheme() {
        return theme;
    }

    // get the edition
    public UnoEdition getEdition() {
        return edition;
    }

    // set the edition
    public void setEdition(UnoEdition edition) {
        this.edition = edition;
    }

    // get the current suit
    public UnoSuit getCurrentSuit() {
        return currentSuit;
    }

    // get the last played card
    public UnoCard getLastPlayedCard() {
        return lastPlayedCard;
    }

    // get the list of players
    public List<UnoPlayer> getPlayers() {
        return players;
    }

    // get the difficulty
    public Difficulty getDifficulty() {
        return difficulty;
    }

    // set the difficulty
    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    // get the list of UnoCards for the local player
    public List<UnoCard> getLocalPlayerCards() {
        return localPlayerCards;
    }

}
