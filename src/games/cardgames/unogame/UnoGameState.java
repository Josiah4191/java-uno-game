package games.cardgames.unogame;

import games.Difficulty;
import games.cardgames.CardGameState;
import games.cardgames.cards.unocards.UnoCard;
import games.cardgames.cards.unocards.UnoCardMachine;
import games.cardgames.cards.unocards.UnoDeck;
import games.cardgames.cards.unocards.UnoEdition;
import games.players.cardplayers.unoplayers.UnoPlayer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
Team Members: Steve Wareham, Charles Davidson, Josiah Stoltzfus
Author: Josiah Stoltzfus
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

public class UnoGameState extends CardGameState {

    private UnoCardMachine machine;
    private List<UnoPlayer> players = new ArrayList<>();
    private UnoModerator moderator;
    private Difficulty difficulty;
    private PlayDirection direction = PlayDirection.FORWARD;
    private int playerPosition;

    public UnoGameState(UnoEdition edition, Difficulty difficulty) {
        machine = new UnoCardMachine(edition);
        this.difficulty = difficulty;
    }

    public void addPlayer(UnoPlayer player) {
        players.add(player);
    }

    public UnoPlayer getPlayer(int playerIndex) {
        return players.get(playerIndex);
    }

    public List<UnoPlayer> getPlayers() {
        return Collections.unmodifiableList(players);
    }

    public UnoCard getLastPlayedCard() {
        return machine.getLastPlayedCard();
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

    public UnoCard drawCardFromDrawPile() {
        return machine.drawCardFromDrawPile();
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

    public UnoCardMachine getMachine() {
        return machine;
    }

    public int getPlayerPosition() {
        return playerPosition;
    }

    public void setPlayerPosition(int playerPosition) {
        this.playerPosition = playerPosition;
    }

}
