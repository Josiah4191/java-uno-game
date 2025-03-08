package games.cardgames.unogame;

import games.Difficulty;
import games.cardgames.CardGameManager;
import games.cardgames.cards.unocards.*;
import games.players.cardplayers.unoplayers.UnoPlayer;

import java.util.List;
import java.util.Random;

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
    private UnoModerator moderator;

    public UnoGameManager(UnoEdition edition, UnoCardTheme theme, Difficulty difficulty) {
        cardImageManager = new UnoCardImageManager(theme);
        gameState = new UnoGameState(edition, difficulty);
        selectFirstPlayer();
        moderator = new UnoModerator(new UnoClassicRules(), gameState);
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

    public void addPlayer(UnoPlayer player) {
        gameState.addPlayer(player);
    }

    public UnoPlayer getPlayer(int playerIndex) {
        return gameState.getPlayer(playerIndex);
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

    public UnoCard playCard(int playerIndex, int index) {
        var machine = gameState.getMachine();
        var player = gameState.getPlayer(playerIndex);
        UnoCard card = player.playCard(index);
        machine.addCardToDiscardPile(card);
        return card;
    }

    private void selectFirstPlayer() {
        Random random = new Random();
        var players = getPlayers();
        UnoPlayer player = players.get(random.nextInt(players.size()));
        int index = players.indexOf(player);
        gameState.setPlayerPosition(index);
    }

    public void reversePlayDirection() {
        PlayDirection direction = gameState.getDirection();
        direction = direction == PlayDirection.FORWARD ? PlayDirection.REVERSE : PlayDirection.FORWARD;
        gameState.setDirection(direction);
    }

    public void nextPlayerPosition() {
        int playerPosition = gameState.getPlayerPosition();
        if (gameState.getDirection() == PlayDirection.FORWARD) {
            playerPosition++;
        } else {
            playerPosition--;
        }
        gameState.setPlayerPosition(playerPosition);
    }

    public void skipNextPlayerPosition() {
        int playerPosition = gameState.getPlayerPosition();
        if (gameState.getDirection() == PlayDirection.FORWARD) {
            playerPosition += 2;
        } else {
            playerPosition -= 2;
        }
        gameState.setPlayerPosition(playerPosition);
    }
}
