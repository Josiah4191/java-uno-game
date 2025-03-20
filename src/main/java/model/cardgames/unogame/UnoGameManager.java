package model.cardgames.unogame;

import controller.GameAreaListener;
import model.cardgames.cards.unocards.*;
import model.images.playerimages.PlayerImage;
import model.players.cardplayers.unoplayers.UnoPlayer;
import model.players.cardplayers.unoplayers.UnoPlayerAI;

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
 */

public class UnoGameManager {

    private UnoGameState gameState = new UnoGameState();
    private GameAreaListener gameAreaListener;
    private volatile boolean aiIsRunning;

    public UnoGameState getGameState() {
        return gameState;
    }

    public void setGameAreaListener(GameAreaListener gameAreaListener) {
        this.gameAreaListener = gameAreaListener;
    }

    public void createAIPlayers(int numberOfOpponents) {
        for (int i = 0; i < numberOfOpponents; i++) {
            UnoPlayerAI player = new UnoPlayerAI(gameState);
            gameState.addPlayer(player);
        }
    }

    public void dealCards(int numberOfCards, List<UnoPlayer> players) {
        gameState.getCardMachine().dealCards(numberOfCards, players);
    }

    public void setGameState(UnoGameState gameState) {
        this.gameState = gameState;
    }

    public boolean playerDrawCard(int playerIndex) {
        UnoPlayer player = gameState.getPlayer(playerIndex);
        UnoCard card = gameState.getCardMachine().drawCardFromDrawPile();
        UnoModerator moderator = gameState.getModerator();
        boolean validCard = moderator.validateCard(gameState, card);

        addCardToPlayer(playerIndex, card);
        player.setLastDrawCard(card);

        return validCard;
    }

    public boolean playCard(int playerIndex, int cardIndex) {
        UnoPlayer player = gameState.getPlayer(playerIndex);
        UnoCard card = player.getCard(cardIndex);
        UnoModerator moderator = gameState.getModerator();
        boolean validCard = moderator.validateCard(gameState, card);

        if (validCard) {
            player.playCard(cardIndex);
            addCardToDiscardPileAndSetCurrentSuit(card);
            processCardValue(card);
        }

        return validCard;
    }

    public void addCardToPlayer(int playerIndex, UnoCard card) {
        var player = gameState.getPlayer(playerIndex);
        player.addCard(card);
    }

    public void addCardToDiscardPileAndSetCurrentSuit(UnoCard card) {
        gameState.getCardMachine().addCardToDiscardPile(card);
        gameState.setCurrentSuit(card.getSuit());
    }

    public List<UnoPlayerAI> getAIPlayers() {
        List<UnoPlayerAI> players = gameState.getPlayers()
                .stream()
                .filter(e -> e instanceof UnoPlayerAI)
                .map(e -> (UnoPlayerAI) e)
                .toList();
        return players;
    }

    public List<UnoPlayer> getHumanPlayers() {
        List<UnoPlayer> players = gameState.getPlayers()
                .stream()
                .filter(e -> e.getClass() == UnoPlayer.class)
                .toList();
        return players;
    }

    public void callUno(UnoPlayer player) {
        boolean checkCallUno = gameState.getModerator().checkCallUno(gameState, player);

        if (checkCallUno) {
            int playerIndex = gameState.getPlayers().indexOf(player);
            applyPenalty(playerIndex, 2);
            gameAreaListener.updateGameAreaView();
        }
    }

    public boolean checkWinner(UnoPlayer player) {
        boolean win = false;

        if (player.getPlayerHand().isEmpty()) {
            win = true;
        }

        return win;
    }

    public void aiCallUno() {
        var humanPlayers = getHumanPlayers();
        for (UnoPlayer player : humanPlayers) {
            callUno(player);
        }
    }

    public void aiCheckCardPlayed(UnoPlayer aiPlayer, UnoCard card) {
        if (card == null) {
            int playerIndex = gameState.getPlayerIndex(aiPlayer);
            playerDrawCard(playerIndex);
            aiPlayer.sayUno(false);

            moveToNextPlayer();
            gameAreaListener.updateGameAreaView();
        } else {
            if (card.getSuit() == UnoSuit.WILD) {
                gameState.getCardMachine().addCardToDiscardPile(card);
            } else {
                addCardToDiscardPileAndSetCurrentSuit(card);
            }
            processCardValue(card);
            gameAreaListener.updateGameAreaView();
        }
    }

    public void aiTakeTurn(UnoPlayerAI aiPlayer) {
        aiCallUno();
        UnoCard card = aiPlayer.playCard(0);
        aiCheckCardPlayed(aiPlayer, card);
    }

    public void stopAIRunning() {
        this.aiIsRunning = false;
    }

    public void startAIRunning() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(gameState.getPlayers().size());
        aiIsRunning = true;
        var aiPlayers = getAIPlayers();

        for (UnoPlayerAI aiPlayer : aiPlayers) {

            executor.scheduleAtFixedRate(() -> {
                if (aiIsRunning) {

                    if (aiPlayer.equals(gameState.getCurrentPlayer())) {

                        aiTakeTurn(aiPlayer);

                        if (checkWinner(aiPlayer)) {
                            gameAreaListener.announceWinner(aiPlayer);
                            executor.shutdown();
                        }
                    }

                    if (gameState.getCurrentPlayer().getClass() == UnoPlayer.class) {
                        executor.shutdown();
                    }

                } else {
                    executor.shutdown();
                }
            }, 3, 5, TimeUnit.SECONDS);
        }
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

    public void reversePlayDirection() {
        PlayDirection direction = gameState.getDirection();
        direction = (direction == PlayDirection.FORWARD) ? PlayDirection.REVERSE : PlayDirection.FORWARD;
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
        /*
        // create players
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
        ArrayList<UnoPlayer> players = new ArrayList<>(List.of(player1, player2, player3, player4));

        // set the images for the players
        for (UnoPlayer player : players) {
            Random random = new Random();
            PlayerImage[] images = PlayerImage.values();
            player.setImage(images[random.nextInt(images.length)]);
        }

        // add players to game
        addPlayers(players);

        // deal cards to players
        //dealCards(7, players);

        // draw the first card and add to the discard pile
        //UnoCard card = gameState.getCardMachine().drawCardFromDrawPile();
        addCardToDiscardPileAndSetCurrentSuit(card);
         */
    }

    public void resetGame() {
        stopAIRunning();
        setGameState(new UnoGameState());
        //initialize();
        gameAreaListener.setGameState(gameState);
        gameAreaListener.updateGameAreaView();
    }

    public void swapPlayerPositions(int player1Index, int player2Index) {
        var players = gameState.getPlayers();
        Collections.swap(players, player1Index, player2Index);
    }

    public UnoPlayer getNextPlayer() {
        int nextPlayerPosition = getNextPlayerIndex(1);
        return gameState.getPlayer(nextPlayerPosition);
    }

    private void selectFirstPlayer() {
        Random random = new Random();
        var players = gameState.getPlayers();
        UnoPlayer player = players.get(random.nextInt(players.size()));
        int index = players.indexOf(player);
        gameState.setCurrentPlayerIndex(index);
    }
}
