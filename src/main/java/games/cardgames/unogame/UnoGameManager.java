package games.cardgames.unogame;

import games.Difficulty;
import games.cardgames.CardGameManager;
import games.cardgames.cards.unocards.UnoCard;
import games.cardgames.cards.unocards.UnoCardImageManager;
import games.cardgames.cards.unocards.UnoCardTheme;
import games.cardgames.cards.unocards.UnoEdition;
import games.players.cardplayers.unoplayers.UnoPlayer;
import games.players.cardplayers.unoplayers.UnoPlayerAI;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/*
Team Members: Steve Wareham, Charles Davidson, Josiah Stoltzfus
Author: Josiah Stoltzfus
Date: 3/7/2025
------------------------------------------------------------------------------

This class is the controller that manages the entire UNO game. This class will be used by the MainController, which
bridges the gap between the UI and the game logic.

What this class contains:
    - Variables:
        - UnoGameState:
            - Holds all information about the current state of the game.
NOTE:
    Methods and variables may be added or altered at a later date:
        - e.g., saving the game state
        - Future database object for saving and loading the gameState object
 */

public class UnoGameManager extends CardGameManager {

    private UnoGameState gameState;

    public UnoGameManager(UnoEdition edition, Difficulty difficulty) {
        gameState = new UnoGameState(edition, difficulty);
    }

    public UnoGameState getGameState() {
        return gameState;
    }

    public void addPlayer(UnoPlayer player) {
        gameState.addPlayer(player);
    }

    public void addPlayers(List<UnoPlayer> players) {
        gameState.addPlayers(players);
    }

    public void dealCards(int numberOfCards, List<UnoPlayer> players) {
        gameState.dealCards(numberOfCards, players);
    }

    public UnoCard drawCardFromDrawPile() {
        var machine = gameState.getMachine();
        UnoCard card = gameState.drawCardFromDrawPile();
        machine.addCardToDiscardPile(card);
        return card;
    }

    /*
    This method will need validation added.
    */
    public UnoCard playCard(UnoPlayer player, UnoCard card) {
        var playerCards = player.getPlayerHand();
        int cardIndex = playerCards.indexOf(card);
        player.playCard(cardIndex);
        addCardToDiscardPile(card);
        return card;
    }

    public void addCardToDiscardPile(UnoCard card) {
        var machine = gameState.getMachine();
        machine.addCardToDiscardPile(card);
    }


    public void addCardToPlayer(UnoPlayer player, UnoCard card) {
        player.addCard(card);
    }

    private void selectFirstPlayer() {
        Random random = new Random();
        var players = gameState.getPlayers();
        UnoPlayer player = players.get(random.nextInt(players.size()));
        int index = players.indexOf(player);
        gameState.setCurrentPlayerIndex(index);
    }

    public void reversePlayDirection() {
        PlayDirection direction = gameState.getDirection();
        direction = direction == PlayDirection.FORWARD ? PlayDirection.REVERSE : PlayDirection.FORWARD;
        gameState.setDirection(direction);
    }

    public int getNextPlayerIndex(int numberToSkipAhead) {
        int currentPlayerIndex = gameState.getCurrentPlayerIndex();
        int numberOfPlayers = gameState.getPlayers().size();

        if (gameState.getDirection().isForward()) {
            currentPlayerIndex += numberToSkipAhead;
        } else {
            currentPlayerIndex -= numberToSkipAhead;
        }

        if (currentPlayerIndex >= 0) {
            currentPlayerIndex = currentPlayerIndex % numberOfPlayers;
        } else {
            currentPlayerIndex = (currentPlayerIndex + numberOfPlayers) % numberOfPlayers;
        }

        return currentPlayerIndex;
    }

    public void moveToNextPlayer() {
        int nextPlayerIndex = getNextPlayerIndex(1);
        gameState.setCurrentPlayerIndex(nextPlayerIndex);
    }

    public void skipNextPlayer() {
        int nextPlayerIndex = getNextPlayerIndex(2);
        gameState.setCurrentPlayerIndex(nextPlayerIndex);
    }

    /*
        Need to see if this belongs here, or belongs in GameState.
        We also need to see if we should be passing indexes to getPlayer instead of a name.
    */
    public UnoPlayer getCurrentPlayer() {
        int playerPosition = gameState.getCurrentPlayerIndex();
        return gameState.getPlayer(playerPosition);
    }

    public UnoPlayer getNextPlayer() {
        int nextPlayerPosition = getNextPlayerIndex(1);
        return gameState.getPlayer(nextPlayerPosition);
    }

    public void applyPenalty(UnoPlayer player, int cardPenalty) {
        for (int i = 0; i < cardPenalty; i++) {
            addCardToPlayer(player, drawCardFromDrawPile());
        }
    }

    public void swapPlayerPositions(UnoPlayer player1, UnoPlayer player2) {
        var players = gameState.getPlayers();
        int player1Position = players.indexOf(player1);
        int player2Position = players.indexOf(player2);
        Collections.swap(players, player1Position, player2Position);
    }

    public void initialize() {
        // create 4 players
        UnoPlayer player1 = new UnoPlayer();
        UnoPlayer player2 = new UnoPlayerAI(getGameState());
        UnoPlayer player3 = new UnoPlayerAI(getGameState());
        UnoPlayer player4 = new UnoPlayerAI(getGameState());
        player1.setName("Josiah");
        player2.setName("Player 2");
        player3.setName("Player 3");
        player4.setName("Player 4");

        // set the main player
        gameState.setMainPlayer(player1);

        // create a list of players
        var players = new ArrayList<>(List.of(player1, player2, player3, player4));

        // add players to game
        addPlayers(players);

        // deal cards to players
        dealCards(7, players);

        // select first card
        UnoCard card = drawCardFromDrawPile();
        addCardToDiscardPile(card);
    }
}

/*
    In a new game, we need to keep track of:
        - points for each player
        - total number of rounds?
        - current round?
        - time limit?

        When the game starts, what do we need?:
            - we need players
            - we need to know how many players there are for dealing cards
            - we need to deal cards to the players
            - we need to set the draw pile
            - we need to set the discard pile
            - we need to select who the first player is
            - if there is an action card that is drawn first, the first player must
            play that action card

            todo: (this is temporary for testing the code)
            - create AI players in start method to play against each other
            - for now, create popup windows that display information about what's happening in the game
                - later, we will update the game view to reflect changes
                - e.g., after drawing a card, popup window says "RED Action card drawn"
                        after playing a card, popup window says "Opponent draws 2 cards. Opponent total cards: 5"
 */
