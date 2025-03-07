package games.cardgames.unogame;

import games.Difficulty;
import games.cardgames.CardGameState;
import games.cardgames.cards.unocards.*;
import games.players.cardplayers.unoplayers.UnoPlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*
Last edited: Josiah Stoltzfus
Date: 3/7/2025
    - Initial version - First time editing. Future edits and comments will be noted here. Please
    include your name and date.

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
    private HashMap<String, UnoPlayer> players = new HashMap<>();
    private Difficulty difficulty;

    public UnoGameState(UnoEdition edition, Difficulty difficulty) {
        machine = new UnoCardMachine(new UnoDeck(edition));
        this.difficulty = difficulty;
    }

    public void addPlayer(String name, UnoPlayer player) {
        players.put(name, player);
    }

    public UnoPlayer getPlayer(String name) {
        return players.get(name);
    }

    public List<String> getPlayerNames() {
        return new ArrayList<>(players.keySet());
    }

    public List<UnoPlayer> getPlayers() {
        return new ArrayList<>(players.values());
    }

    public UnoCard getLastPlayedCard() {
        return machine.getLastPlayedCard();
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

    public UnoCard playCard(String name, int index) {
        var player = players.get(name);
        UnoCard card = player.playCard(index);
        machine.addCardToDiscardPile(card);
        return card;
    }
}
