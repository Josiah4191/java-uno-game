package model.cardgames.unogame;

import controller.GameAreaListener;
import model.cardgames.cards.unocards.UnoCard;
import model.cardgames.cards.unocards.UnoSuit;
import model.cardgames.cards.unocards.UnoValue;
import model.images.playerimages.PlayerImage;
import model.players.cardplayers.unoplayers.UnoPlayer;
import model.players.cardplayers.unoplayers.UnoPlayerAI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/*
Team Members: Steve Wareham, Charles Davidson, Josiah Stoltzfus
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
NOTE:
    Methods and variables may be added or altered at a later date:
        - e.g., saving the game state
        - Future database object for saving and loading the gameState object
 */

public class UnoGameManager {

    private UnoGameState gameState = new UnoGameState();
    private GameAreaListener gameAreaListener;

    public UnoGameState getGameState() {
        return gameState;
    }

    public void setGameAreaListener(GameAreaListener gameAreaListener) {
        this.gameAreaListener = gameAreaListener;
    }

    public void addPlayers(List<UnoPlayer> players) {
        gameState.addPlayers(players);
    }

    public void dealCards(int numberOfCards, List<UnoPlayer> players) {
        gameState.dealCards(numberOfCards, players);
    }

    public boolean playerDrawCard(int playerIndex) {
        UnoCard card = gameState.getCardMachine().drawCardFromDrawPile();
        addCardToPlayer(playerIndex, card);
        UnoPlayer player = gameState.getPlayer(playerIndex);
        player.setLastDrawCard(card);

        boolean playable = gameState.getModerator().validateCard(gameState, card);

        return playable;
    }

    public boolean playCard(int playerIndex, int cardIndex) {
        var player = gameState.getPlayer(playerIndex);
        var moderator = gameState.getModerator();
        UnoCard card = player.getPlayerHand().get(cardIndex);
        boolean valid = moderator.validateCard(gameState, card);

        if (valid) {

            player.playCard(cardIndex);
            addCardToDiscardPileAndSetCurrentSuit(card);

            processCardValue(card);

            return true;
        }
        return false;
    }

    public void addCardToDiscardPileAndSetCurrentSuit(UnoCard card) {
        gameState.getCardMachine().addCardToDiscardPile(card);
        gameState.setCurrentSuit(card.getSuit());
    }

    public void runAITurn() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(gameState.getPlayers().size());

        for (UnoPlayer player : gameState.getPlayers()) {

            if (!(player.equals(gameState.getMainPlayer()))) {

                executor.scheduleAtFixedRate(new Runnable() {
                    public void run() {

                        if (player.equals(gameState.getCurrentPlayer())) {
                            System.out.println("Last played card: " + gameState.getLastPlayedCard());
                            UnoCard card = player.playCard(0);

                            for (UnoPlayer otherPlayer : gameState.getPlayers()) {
                                if (!(player.equals(otherPlayer))) {
                                    boolean checkCallUno = checkCallUno(otherPlayer);
                                    if (checkCallUno) {
                                        int otherPlayerIndex = gameState.getPlayers().indexOf(otherPlayer);
                                        applyPenalty(otherPlayerIndex, 2);
                                        System.out.println(player + " has called UNO on " + otherPlayer + ". " + otherPlayer + " didn't say UNO." + otherPlayer + " draws 2 cards.");
                                        gameAreaListener.updateGameAreaView();
                                    }
                                }
                            }

                            if (card == null) {
                                System.out.println(gameState.getCurrentPlayer().getName() + " has no playable cards");

                                int playerIndex = gameState.getPlayers().indexOf(player);
                                playerDrawCard(playerIndex);

                                moveToNextPlayer();
                                gameAreaListener.updateGameAreaView();

                                System.out.println();
                            } else {
                                System.out.println(player + " is playing: " + card);

                                if (card.getSuit() == UnoSuit.WILD) {
                                    gameState.getCardMachine().addCardToDiscardPile(card);
                                } else {
                                    addCardToDiscardPileAndSetCurrentSuit(card);
                                }

                                if (player.getPlayerHand().isEmpty()) {
                                    gameAreaListener.updateGameAreaView();
                                    processCardValue(card);
                                    gameAreaListener.announceWinner(player);
                                    executor.shutdown();
                                    return;
                                }

                                processCardValue(card);

                                gameAreaListener.updateGameAreaView();
                                System.out.println();
                            }

                            if (gameState.getCurrentPlayer().equals(gameState.getMainPlayer())) {
                                executor.shutdown();
                            }
                        }
                    }
                }, 3, 5, TimeUnit.SECONDS);
            }
        }
    }

    public boolean checkCallUno(UnoPlayer player) {
        return gameState.getModerator().checkCallUno(gameState, player);
    }

    private void processCardValue(UnoCard card) {
        int nextPlayerIndex = getNextPlayerIndex(1);
        UnoValue value = card.getValue();
        switch (value) {
            case UnoValue.REVERSE:
                reversePlayDirection();
                break;
            case UnoValue.SKIP:
                skipNextPlayer();
                break;
            case UnoValue.DRAW_TWO:
                applyPenalty(nextPlayerIndex, 2);
                break;
            case UnoValue.WILD_DRAW_FOUR:
                applyPenalty(nextPlayerIndex, 4);
                break;
            case UnoValue.DRAW_TW0_STACK:
                gameState.addStackPenalty(2);
                break;
            case UnoValue.WILD_DRAW_FOUR_STACK:
                gameState.addStackPenalty(4);
                break;
        }

        if (!(value == UnoValue.SKIP)) {
            moveToNextPlayer();
        }
    }

    public void addCardToPlayer(int playerIndex, UnoCard card) {
        var player = gameState.getPlayer(playerIndex);
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

    public void applyPenalty(int playerIndex, int cardPenalty) {
        for (int i = 0; i < cardPenalty; i++) {
            UnoCard card = gameState.getCardMachine().drawCardFromDrawPile();
            addCardToPlayer(playerIndex, card);
        }
    }

    public void initialize() {
        // create 4 players
        UnoPlayer player1 = new UnoPlayer();
        UnoPlayer player2 = new UnoPlayerAI(getGameState());
        UnoPlayer player3 = new UnoPlayerAI(getGameState());
        UnoPlayer player4 = new UnoPlayerAI(getGameState());
        /*
        UnoPlayer player5 = new UnoPlayerAI(getGameState());
        UnoPlayer player6 = new UnoPlayerAI(getGameState());
        UnoPlayer player7 = new UnoPlayerAI(getGameState());
         */
        player1.setName("Josiah");
        player2.setName("Player 2");
        player3.setName("Player 3");
        player4.setName("Player 4");
        /*
        player5.setName("Player 5");
        player6.setName("Player 6");
        player7.setName("Player 7");
         */

        ArrayList<UnoPlayer> players = new ArrayList<>(List.of(player1, player2, player3, player4));
        //players.addAll(player5, player6, player7);

        for (UnoPlayer player : players) {
            Random random = new Random();
            PlayerImage[] images = PlayerImage.values();
            player.setImage(images[random.nextInt(images.length)]);
        }

        // set the main player
        gameState.setMainPlayer(player1);

        // add players to game
        addPlayers(players);

        // deal cards to players
        dealCards(7, players);

        // select first card
        UnoCard card = gameState.getCardMachine().drawCardFromDrawPile();
        addCardToDiscardPileAndSetCurrentSuit(card);
    }

    public void swapPlayerPositions(int player1Index, int player2Index) {
        var players = gameState.getPlayers();
        Collections.swap(players, player1Index, player2Index);
    }

    public UnoPlayer getNextPlayer() {
        int nextPlayerPosition = getNextPlayerIndex(1);
        return gameState.getPlayer(nextPlayerPosition);
    }

    public void resetGame() {
        this.gameState = new UnoGameState();
        initialize();
        gameAreaListener.setGameState(gameState);
        gameAreaListener.updateGameAreaView();
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
