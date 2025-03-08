package games.cardgames.unogame;

import games.Difficulty;
import games.cardgames.CardGameManager;
import games.cardgames.cards.unocards.*;
import games.players.cardplayers.unoplayers.UnoPlayer;

import java.util.List;

/*
Last edited: Josiah Stoltzfus
Date: 3/7/2025
    - Initial version - First time editing. Future edits and comments will be noted here. Please
    include your name and date.

Author: Josiah Stoltzfus
Date: 3/7/2025
------------------------------------------------------------------------------

This class is the controller that manages the entire UNO game. This class will be used by the MainController, which
bridges the gap between the UI and the game logic.

What this class contains:
    - Variables:
        - UnoGameState:
            - Holds all information about the current state of the game.
        - UnoCardImageManager:
            - Responsible for retrieving card images.
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
        - These are top-level methods in a series of cascaded method calls.
        - Each method should be self-explanatory and performs the action that its name suggests.
        - For implementation details, refer to UnoCardMachine, Pile, UnoDrawPile, and UnoDiscardPile.
NOTE:
    Methods and variables may be added or altered at a later date:
        - e.g., saving the game state
        - Future database object for saving and loading the gameState object
 */

public class UnoGameManager extends CardGameManager {

    private UnoGameState gameState;
    private UnoCardImageManager cardImageManager;

    public UnoGameManager(UnoEdition edition, UnoCardTheme theme, Difficulty difficulty) {
        cardImageManager = new UnoCardImageManager(theme);
        gameState = new UnoGameState(edition, difficulty);
    }

    public UnoGameState getGameState() {
        return gameState;
    }

    public String getImage(UnoCard card) {
        return cardImageManager.getImage(card);
    }

    public PlayDirection getDirection() {
        return gameState.getDirection();
    }

    public void setDirection(PlayDirection direction) {
        gameState.setDirection(direction);
    }

    public UnoCardTheme getTheme() {
        return cardImageManager.getTheme();
    }

    public void setTheme(UnoCardTheme theme) {
        cardImageManager.setTheme(theme);
    }

    public void setDifficulty(Difficulty difficulty) {
        gameState.setDifficulty(difficulty);
    }

    public Difficulty getDifficulty() {
        return gameState.getDifficulty();
    }

    public void addPlayer(String name, UnoPlayer player) {
        gameState.addPlayer(name, player);
    }

    public UnoPlayer getPlayer(String name) {
        return gameState.getPlayer(name);
    }

    public List<String> getPlayerNames() {
        return gameState.getPlayerNames();
    }

    public List<UnoPlayer> getPlayers() {
        return gameState.getPlayers();
    }

    public UnoCard getLastPlayedCard() {
        return gameState.getLastPlayedCard();
    }

    public void dealCards(int numberOfCards, List<UnoPlayer> players) {
        gameState.dealCards(numberOfCards, players);
    }

    public UnoCard drawCardFromDrawPile() {
        return gameState.drawCardFromDrawPile();
    }

    public UnoEdition getEdition() {
        return gameState.getEdition();
    }

    public List<UnoCard> getDrawPile() {
        return gameState.getDrawPile();
    }

    public List<UnoCard> getDiscardPile() {
        return gameState.getDiscardPile();
    }

    public List<UnoCard> getDeck() {
        return gameState.getDeck();
    }

    public UnoCard playCard(String name, int index) {
        return gameState.playCard(name, index);
    }

    private void reversePlayDirection() {
        gameState.reversePlayDirection();
    }

    private void nextPlayerPosition() {
        gameState.nextPlayerPosition();
    }

    private void skipNextPlayerPosition() {
        gameState.skipNextPlayerPosition();
    }
}
