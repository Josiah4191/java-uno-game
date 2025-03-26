package multiplayer.server.unoserver;

import controller.GameAreaListener;
import model.cardgames.cards.unocards.*;
import model.cardgames.unogame.PlayDirection;
import model.cardgames.unogame.UnoGameState;
import model.cardgames.unogame.UnoModerator;
import model.images.playerimages.PlayerImage;
import model.players.cardplayers.unoplayers.UnoHumanPlayer;
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

public class ServerUnoGameManager {

    private UnoGameState gameState = new UnoGameState();
    private GameAreaListener gameAreaListener;

    public UnoGameState getGameState() {
        return gameState;
    }

    public void setGameAreaListener(GameAreaListener gameAreaListener) {
        this.gameAreaListener = gameAreaListener;
    }

    public void createHumanPlayer(String playerName, int playerID, PlayerImage playerImage) {
        // create the player
        UnoHumanPlayer player = new UnoHumanPlayer(playerID);
        player.setName(playerName);
        player.setImage(playerImage);
        // add player to gameState
        gameState.addPlayer(player);
    }

    public void createAIPlayers(int numberOfPlayers) {
        for (int i = 0; i < numberOfPlayers; i++) {
            UnoPlayerAI player = new UnoPlayerAI(gameState);
            gameState.addPlayer(player);
        }
    }

    public void startGame() {


    }

    public void continueTurnCycle() {
        UnoPlayer currentPlayer = gameState.getCurrentPlayer();

        if (currentPlayer.isAI()) {
            aiPlayCard(currentPlayer);
        }

    }

    public void dealCards(int numberOfCards, List<UnoPlayer> players) {
        gameState.getCardMachine().dealCards(numberOfCards, players);
    }

    public void setGameState(UnoGameState gameState) {
        this.gameState = gameState;
    }

    public boolean playerDrawCardFromDrawPile(int playerIndex) {
        UnoPlayer player = gameState.getPlayer(playerIndex);
        UnoCard card = gameState.getCardMachine().drawCardFromDrawPile();
        UnoModerator moderator = gameState.getModerator();
        boolean validCard = moderator.validateCard(gameState, card);

        addCardToPlayer(playerIndex, card);
        player.setLastDrawCard(card);

        return validCard;
    }


    public boolean humanPlayCard(int playerIndex, int cardIndex) {
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

    public List<UnoPlayer> getAIPlayers() {
        return gameState.getPlayers()
                .stream()
                .filter(UnoPlayer::isAI)
                .toList();
    }

    public List<UnoPlayer> getHumanPlayers() {
        return gameState.getPlayers()
                .stream()
                .filter(player -> !(player.isAI()))
                .toList();
    }

    public void callUno(int playerIndex) {
        boolean checkCallUno = gameState.getModerator().checkCallUno(gameState, playerIndex);

        if (checkCallUno) {
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
            int playerIndex = gameState.getPlayerIndex(player);
            callUno(playerIndex);
        }
    }

    public void processAICardPlayed(UnoPlayer player, UnoCard card) {
        if (card == null) {
            int playerIndex = gameState.getPlayerIndex(player);
            playerDrawCardFromDrawPile(playerIndex);

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
    /*
        public boolean humanPlayCard(int playerIndex, int cardIndex) {
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
     */
    public void aiPlayCard(UnoPlayer player) {
        ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
        service.scheduleWithFixedDelay(new Runnable() {
            public void run() {
                gameAreaListener.playClick1();
                aiCallUno();
                UnoCard card = player.playCard(0);
                processAICardPlayed(player, card);

                if (checkWinner(player)) {
                    gameAreaListener.announceWinner(player);
                }

                service.shutdown();

                continueTurnCycle();
            }
        }, 3, 5, TimeUnit.SECONDS);
    }

    private void processCardValue(UnoCard card) {
        int nextPlayerIndex = getNextPlayerIndex(1);
        UnoValue value = card.getValue();
        switch (value) {
            case UnoValue.REVERSE:
                reversePlayDirection();
                gameAreaListener.updatePlayDirection();
                break;
            case UnoValue.SKIP:
                skipNextPlayer();
                break;
            case UnoValue.DRAW_TWO:
                applyPenalty(nextPlayerIndex, 2);
                skipNextPlayer();
                break;
            case UnoValue.WILD_DRAW_FOUR:
                applyPenalty(nextPlayerIndex, 4);
                skipNextPlayer();
                break;
            case UnoValue.DRAW_TW0_STACK:
                gameState.addStackPenalty(2);
                break;
            case UnoValue.WILD_DRAW_FOUR_STACK:
                gameState.addStackPenalty(4);
                break;
        }

        if (!(value == UnoValue.DRAW_TWO) && !(value == UnoValue.WILD_DRAW_FOUR) && !(value == UnoValue.SKIP)) {
            moveToNextPlayer();
        }
    }

    public void reversePlayDirection() {
        PlayDirection direction = gameState.getPlayDirection();
        direction = (direction == PlayDirection.FORWARD) ? PlayDirection.REVERSE : PlayDirection.FORWARD;
        gameState.setPlayDirection(direction);
    }

    public int getNextPlayerIndex(int numberToSkipAhead) {
        int currentPlayerIndex = gameState.getCurrentPlayerIndex();
        int numberOfPlayers = gameState.getPlayers().size();

        if (gameState.getPlayDirection().isForward()) {
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
        gameState.addPlayer(gameState.getLocalPlayer());
        createAIPlayers(4);
        gameState.getCardMachine().createMachine(gameState.getEdition());
        UnoCard card = gameState.getCardMachine().drawCardFromDrawPile();
        addCardToDiscardPileAndSetCurrentSuit(card);
        dealCards(7, gameState.getPlayers());
    }

    public void resetGame() {
        /*
        stopAIRunning();
        setGameState(new UnoGameState());
        initialize();
        gameAreaListener.setGameState(gameState);
        gameAreaListener.updateGameAreaView();
         */
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

    public void sayUno(int playerIndex) {
        UnoPlayer player = gameState.getPlayer(playerIndex);
        player.sayUno(true);
    }

    public void passTurn(int playerIndex) {
        UnoPlayer player = gameState.getPlayer(playerIndex);
        player.setPassTurn(true);
    }

}
