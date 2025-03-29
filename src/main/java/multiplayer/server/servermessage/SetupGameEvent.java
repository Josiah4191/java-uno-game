package multiplayer.server.servermessage;

import model.cardgame.card.unocard.*;
import model.cardgame.unogame.Difficulty;
import model.player.cardplayer.unoplayer.UnoPlayer;

import java.util.List;

public class SetupGameEvent extends GameEvent {

    private Difficulty difficulty;
    private UnoCardTheme theme;
    private UnoEdition edition;
    private UnoSuit currentSuit;
    private UnoCard lastPlayedCard;
    private List<UnoPlayer> players;
    private List<UnoCard> localPlayerCards;

    public SetupGameEvent(UnoCardTheme theme, UnoEdition edition, Difficulty difficulty, UnoSuit currentSuit, UnoCard lastPlayedCard, List<UnoPlayer> players, List<UnoCard> localPlayerCards) {
        this.theme = theme;
        this.edition = edition;
        this.difficulty = difficulty;
        this.currentSuit = currentSuit;
        this.lastPlayedCard = lastPlayedCard;
        this.players = players;
        this.localPlayerCards = localPlayerCards;
        setType(GameEventType.SETUP_GAME);
    }

    public UnoCardTheme getTheme() {
        return theme;
    }

    public void setTheme(UnoCardTheme theme) {
        this.theme = theme;
    }

    public UnoEdition getEdition() {
        return edition;
    }

    public void setEdition(UnoEdition edition) {
        this.edition = edition;
    }

    public UnoSuit getCurrentSuit() {
        return currentSuit;
    }

    public void setCurrentSuit(UnoSuit currentSuit) {
        this.currentSuit = currentSuit;
    }

    public UnoCard getLastPlayedCard() {
        return lastPlayedCard;
    }

    public void setLastPlayedCard(UnoCard lastPlayedCard) {
        this.lastPlayedCard = lastPlayedCard;
    }

    public List<UnoPlayer> getPlayers() {
        return players;
    }

    public void setPlayers(List<UnoPlayer> players) {
        this.players = players;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public List<UnoCard> getLocalPlayerCards() {
        return localPlayerCards;
    }

    public void setLocalPlayerCards(List<UnoCard> localPlayerCards) {
        this.localPlayerCards = localPlayerCards;
    }

}
