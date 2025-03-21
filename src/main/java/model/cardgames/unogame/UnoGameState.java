package model.cardgames.unogame;

import model.cardgames.cards.unocards.*;
import model.images.cardimages.UnoCardImageManager;
import model.images.playerimages.PlayerImageManager;
import model.players.cardplayers.unoplayers.UnoPlayer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/*
Team Members: Steve Wareham, Charles Davidson, Josiah Stoltzfus
Date: 3/7/2025
------------------------------------------------------------------------------
 */

public class UnoGameState implements Serializable {

    private UnoRules rules = new UnoClassicRules();
    private UnoPlayer mainPlayer = new UnoPlayer();
    private List<UnoPlayer> players = new ArrayList<>();
    private UnoModerator moderator = new UnoModerator();
    private PlayDirection direction = PlayDirection.FORWARD;
    private Difficulty difficulty = Difficulty.EASY;
    private UnoSuit currentSuit;
    private UnoEdition edition;
    private transient UnoCardImageManager cardImageManager = new UnoCardImageManager();
    private transient PlayerImageManager playerImageManager= new PlayerImageManager();
    private UnoCardMachine machine = new UnoCardMachine();
    private int currentPlayerIndex;
    private int stackPenalty = 0;

    public UnoPlayer getPlayer(int playerIndex) {
        return players.get(playerIndex);
    }

    public int getPlayerIndex(UnoPlayer player) {
        return players.indexOf(player);
    }

    public List<UnoPlayer> getPlayers() {
        return players;
    }

    public void addPlayers(List<UnoPlayer> players) {
        this.players.addAll(players);
    }

    public void addPlayer(UnoPlayer player) {
        players.add(player);
    }

    public UnoPlayer getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public void setCurrentPlayerIndex(int currentPlayerIndex) {
        this.currentPlayerIndex = currentPlayerIndex;
    }

    public UnoPlayer getMainPlayer() {
        return mainPlayer;
    }

    public void setMainPlayer(UnoPlayer mainPlayer) {
        this.mainPlayer = mainPlayer;
    }

    public UnoCardTheme getTheme() {
        return cardImageManager.getTheme();
    }

    public void setTheme(UnoCardTheme theme) {
        cardImageManager.setTheme(theme);
    }

    public UnoCard getLastPlayedCard() {
        return getCardMachine().getLastPlayedCard();
    }

    public UnoSuit getCurrentSuit() {
        return currentSuit;
    }

    public void setCurrentSuit(UnoSuit currentSuit) {
        this.currentSuit = currentSuit;
    }

    public PlayDirection getDirection() {
        return direction;
    }

    public void setDirection(PlayDirection direction) {
        this.direction = direction;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public UnoEdition getEdition() {
        return edition;
    }

    public void setEdition(UnoEdition edition) {
        this.edition = edition;
    }

    public List<UnoCard> getDrawPile() {
        return machine.getDrawPile();
    }

    public List<UnoCard> getDiscardPile() {
        return machine.getDiscardPile();
    }

    public List<UnoCard> getDeck() {
        return machine.getDeck();
    }

    public UnoCardMachine getCardMachine() {
        return machine;
    }

    public void setCardMachine(UnoCardMachine machine) {
        this.machine = machine;
    }

    public UnoRules getRules() {
        return rules;
    }

    public void setRules(UnoRules rules) {
        this.rules = rules;
    }

    public UnoCardImageManager getCardImageManager() {
        return cardImageManager;
    }

    public void setCardImageManager(UnoCardImageManager cardImageManager) {
        this.cardImageManager = cardImageManager;
    }

    public PlayerImageManager getPlayerImageManager() {
        return playerImageManager;
    }

    public void setPlayerImageManager(PlayerImageManager playerImageManager) {
        this.playerImageManager = playerImageManager;
    }

    public UnoModerator getModerator() {
        return moderator;
    }

    public void addStackPenalty(int cardPenalty) {
        stackPenalty += cardPenalty;
    }

    public int getStackPenalty() {
        return stackPenalty;
    }

}
