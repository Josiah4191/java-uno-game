package model.cardgames.unogame;

import model.cardgames.cards.unocards.*;
import model.images.cardimages.UnoCardImageManager;
import model.images.playerimages.PlayerImageManager;
import model.players.cardplayers.unoplayers.UnoPlayer;

import java.util.ArrayList;
import java.util.List;

/*
Team Members: Steve Wareham, Charles Davidson, Josiah Stoltzfus
Date: 3/7/2025
------------------------------------------------------------------------------

This class is responsible for containing all information related to the state of the game.

Things that this class knows about:
    - UnoCardMachine (manages the cards)
        - The draw pile
        - The discard pile
        - The deck
            - UNO edition
    - Players
        - Player hand of cards
    - Selected difficulty
NOTE:
    Information may be added or altered to this class later (e.g., player scores, rules).

What this class contains:
    - Variables:
        - UnoCardMachine:
            - Creates the deck of cards and manages the deck, draw pile, and discard pile.
        - Map of Players:
            - Holds a map of players.
            - The player names are used as the keys.
            - The keys are mapped to the player object.
        - Difficulty:
            - Represents the selected difficulty.
    - Methods:
            - getGameState
            - getImage
            - getTheme
            - setTheme
            - getDifficulty
            - setDifficulty
            - addPlayer
            - getPlayer
            - getPlayers
            - getPlayerNames
            - getLastPlayedCard
            - dealCards
            - drawCardFromDrawPile
            - getEdition
            - getDrawPile
            - getDeck
            - getDiscardPile
            - playCard
        - These are the second-highest level methods in a series of cascaded method calls.
        - Each method should be self-explanatory and performs the action that its name suggests.
        - For implementation details, refer to UnoCardMachine, Pile, UnoDrawPile, and UnoDiscardPile.
 */

public class UnoGameState {

    private UnoRules rules = new UnoClassicRules();
    private List<UnoPlayer> players = new ArrayList<>();
    private UnoModerator moderator = new UnoModerator();
    private PlayDirection direction = PlayDirection.FORWARD;
    private UnoSuit currentSuit;
    private Difficulty difficulty = Difficulty.EASY;
    private UnoCardImageManager cardImageManager = new UnoCardImageManager();
    private PlayerImageManager playerImageManager= new PlayerImageManager();
    private UnoCardMachine machine = new UnoCardMachine();
    private int currentPlayerIndex;
    private UnoPlayer mainPlayer;
    private int stackPenalty = 0;

    public void addPlayer(UnoPlayer player) {
        players.add(player);
    }

    public void addPlayers(List<UnoPlayer> players) {
        this.players.addAll(players);
    }

    public UnoPlayer getPlayer(int playerIndex) {
        return players.get(playerIndex);
    }

    public UnoCardTheme getTheme() {
        return cardImageManager.getTheme();
    }

    public void setTheme(UnoCardTheme theme) {
        cardImageManager.setTheme(theme);
    }

    public List<UnoPlayer> getPlayers() {
        return players;
    }

    public UnoCard getLastPlayedCard() {
        return machine.getLastPlayedCard();
    }

    public void setCurrentSuit(UnoSuit currentSuit) {
        this.currentSuit = currentSuit;
    }

    public UnoSuit getCurrentSuit() {
        return currentSuit;
    }

    public void setDirection(PlayDirection direction) {
        this.direction = direction;
    }

    public PlayDirection getDirection() {
        return direction;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void dealCards(int numberOfCards, List<UnoPlayer> players) {
        machine.dealCards(numberOfCards, players);
    }

    public UnoEdition getEdition() {
        return machine.getEdition();
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

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public void setCurrentPlayerIndex(int currentPlayerIndex) {
        this.currentPlayerIndex = currentPlayerIndex;
    }

    public UnoPlayer getCurrentPlayer() {
        return players.get(currentPlayerIndex);
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

    public PlayerImageManager getPlayerImageManager() {
        return playerImageManager;
    }

    public UnoModerator getModerator() {
        return moderator;
    }

    public UnoPlayer getMainPlayer() {
        return mainPlayer;
    }

    public void setMainPlayer(UnoPlayer mainPlayer) {
        this.mainPlayer = mainPlayer;
    }

    public void addStackPenalty(int cardPenalty) {
        stackPenalty += cardPenalty;
    }

    public int getStackPenalty() {
        return stackPenalty;
    }

}
