package model.cardgame.unogame;

import model.cardgame.card.unocard.*;
import model.image.cardimage.UnoCardImageManager;
import model.image.playerimage.PlayerImageManager;
import model.player.cardplayer.unoplayer.UnoPlayer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
This class is used to hold all the information about the current state of the game. It's only responsible for
storing the data, and this data is passed around throughout the program where it's needed. It can also be used
for saving and loading the game.

There are only get and set methods for the variables that are stored. The names of the variables should be
self-explanatory for what they represent.

The game state is serializable, so it can be saved to a file as an object. The objects it stores are also
serializable, unless marked as transient.
 */

public class UnoGameState implements Serializable {

    private List<UnoPlayer> players = new ArrayList<>();
    private Map<Integer, UnoPlayer> playerIdToPlayer = new HashMap<>();
    private Map<Integer, Integer> playerIndexToCardNumber = new HashMap<>();
    private UnoPlayer localPlayer;
    private UnoModerator moderator = new UnoModerator();
    private UnoRules rules = new UnoClassicRules();
    private PlayDirection playDirection = PlayDirection.FORWARD;
    private Difficulty difficulty;
    private UnoSuit currentSuit;
    private UnoEdition edition;
    private UnoCardMachine cardMachine = new UnoCardMachine();
    private transient UnoCardImageManager cardImageManager = new UnoCardImageManager();
    private transient PlayerImageManager playerImageManager = new PlayerImageManager();
    private List<UnoCard> playableCards = new ArrayList<>();
    private UnoCard lastPlayedCard;
    private int currentPlayerIndex;
    private int nextPlayerIndex;

    public void setStackPenalty(int stackPenalty) {
        this.stackPenalty = stackPenalty;
    }

    private int stackPenalty = 0;

    public int getNextPlayerIndex() {
        return nextPlayerIndex;
    }

    public void setNextPlayerIndex(int nextPlayerIndex) {
        this.nextPlayerIndex = nextPlayerIndex;
    }

    public UnoPlayer getPlayer(int playerIndex) {
        return players.get(playerIndex);
    }

    public int getPlayerIndex(UnoPlayer player) {
        return players.indexOf(player);
    }

    public UnoPlayer getPlayerFromPlayerID(int playerID) {
        return playerIdToPlayer.get(playerID);
    }

    public List<UnoPlayer> getPlayers() {
        return players;
    }

    public void setPlayableCards(List<UnoCard> playableCards) {
        this.playableCards = playableCards;
    }

    public List<UnoCard> getPlayableCards() {
        return playableCards;
    }

    public void addPlayers(List<UnoPlayer> players) {
        for (var player : players) {
            addPlayer(player);
        }
    }

    public void addPlayer(UnoPlayer player) {
        players.add(player);
        if (!player.isAI()) {
            playerIdToPlayer.put(player.getPlayerID(), player);
        }
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

    public UnoPlayer getLocalPlayer() {
        return localPlayer;
    }

    public int getLocalPlayerIndex() {
        return players.indexOf(localPlayer);
    }

    public void setLocalPlayer(UnoPlayer localPlayer) {
        this.localPlayer = localPlayer;
    }

    public UnoCardTheme getTheme() {
        return cardImageManager.getTheme();
    }

    public void setTheme(UnoCardTheme theme) {
        cardImageManager.setTheme(theme);
    }

    public UnoSuit getCurrentSuit() {
        return currentSuit;
    }

    public void setCurrentSuit(UnoSuit currentSuit) {
        this.currentSuit = currentSuit;
    }

    public PlayDirection getPlayDirection() {
        return playDirection;
    }

    public void setPlayDirection(PlayDirection playDirection) {
        this.playDirection = playDirection;
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
        return cardMachine.getDrawPile();
    }

    public List<UnoCard> getDiscardPile() {
        return cardMachine.getDiscardPile();
    }

    public List<UnoCard> getDeck() {
        return cardMachine.getDeck();
    }

    public UnoCardMachine getCardMachine() {
        return cardMachine;
    }

    public void setCardMachine(UnoCardMachine machine) {
        this.cardMachine = machine;
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

    public void setPlayers(List<UnoPlayer> players) {
        this.players = players;
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

    public UnoCard getLastPlayedCard() {
        return lastPlayedCard;
    }

    public void setLastPlayedCard(UnoCard lastPlayedCard) {
        this.lastPlayedCard = lastPlayedCard;
    }

    public Map<Integer, Integer> getPlayerIndexToCardNumber() {
        return playerIndexToCardNumber;
    }

    public void setPlayerIndexToCardNumber(Map<Integer, Integer> playerIndexToCardNumber) {
        this.playerIndexToCardNumber = playerIndexToCardNumber;
    }

    public Map<Integer, UnoPlayer> getPlayerIdToPlayer() {
        return playerIdToPlayer;
    }

    public void setPlayerIdToPlayer(Map<Integer, UnoPlayer> playerIdToPlayer) {
        this.playerIdToPlayer = playerIdToPlayer;
    }
}
