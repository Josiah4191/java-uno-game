package model.cardgame.unogame;

import model.cardgame.card.unocard.*;
import model.image.playerimage.PlayerImage;
import model.player.cardplayer.unoplayer.UnoHumanPlayer;
import model.player.cardplayer.unoplayer.UnoPlayer;
import model.player.cardplayer.unoplayer.UnoPlayerAI;
import multiplayer.server.servermessage.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
Team Members: Steve Wareham, Charles Davidson, Josiah Stoltzfus
Date: 3/7/2025
------------------------------------------------------------------------------
 */

public class ServerUnoGameManager {

    private UnoGameState gameState = new UnoGameState();
    private GameEventListener gameEventListener;
    private Thread turnCycleThread;

    public UnoGameState getGameState() {
        return gameState;
    }

    public void setGameState(UnoGameState gameState) {
        this.gameState = gameState;
    }

    public void setGameEventListener(GameEventListener gameEventListener) {
        this.gameEventListener = gameEventListener;
    }

    public void setupGame(UnoEdition edition, UnoCardTheme theme, Difficulty difficulty, int numberOfOpponents, int playerID) {
        // set the game settings
        gameState.setTheme(theme);
        gameState.setEdition(edition);
        gameState.setDifficulty(difficulty);
        gameState.getCardMachine().createMachine(gameState.getEdition());

        // get local players and ai players
        var localPlayers = gameState.getPlayerIdToPlayer().values();
        var aiPlayers = createAIPlayer(numberOfOpponents);

        // add the local players and ai players to the game
        gameState.getPlayers().addAll(localPlayers);
        gameState.getPlayers().addAll(aiPlayers);

        // draw a card from the draw pile and add it to the discard pile
        UnoCard lastPlayedCard = gameState.getCardMachine().drawCardFromDrawPile();
        addCardToDiscardPile(lastPlayedCard);

        // set and get the current suit
        gameState.setCurrentSuit(lastPlayedCard.getSuit());
        UnoSuit currentSuit = gameState.getCurrentSuit();

        // deal 7 cards to each player
        dealCards(1, gameState.getPlayers());

        // get the local player's cards
        UnoPlayer localPlayer = gameState.getPlayerFromPlayerID(playerID);
        List<UnoCard> localPlayerCards = localPlayer.getPlayerHand();

        // get the list of all players
        var players = gameState.getPlayers();

        // start the turn cycle
        continueTurnCycle();

        // send the setup game event to the clients
        SetupGameEvent setupGameEvent = new SetupGameEvent(theme, edition, difficulty, currentSuit, lastPlayedCard, players, localPlayerCards);
        gameEventListener.sendEventMessageToAll(setupGameEvent);
    }

    public void addLocalPlayer(String playerName, int playerID, PlayerImage playerImage) {
        UnoHumanPlayer player = new UnoHumanPlayer(playerID);
        player.setImage(playerImage);
        player.setName(playerName);

        gameState.getPlayerIdToPlayer().put(playerID, player);

        AddLocalPlayerEvent addLocalPlayerEvent = new AddLocalPlayerEvent(player);
        gameEventListener.sendEventMessage(addLocalPlayerEvent, playerID);
    }

    public void printDeckInformation() {
        int deckSize = gameState.getDeck().size();
        int discardPileSize = gameState.getDiscardPile().size();
        int drawPileSize = gameState.getDrawPile().size();
        int totalPileSize = deckSize + discardPileSize + drawPileSize;

        System.out.println();
        System.out.println("Total number of cards in deck: " + deckSize);
        System.out.println("Total number of cards in draw pile: " + drawPileSize);
        System.out.println("Total number of cards in discard pile: " + discardPileSize);

        for (UnoPlayer player : gameState.getPlayers()) {
            int totalCards = player.getPlayerHand().size();
            System.out.println("Player: " + player.getName() + " | " + "Total number of cards: " + totalCards);
            totalPileSize += totalCards;
        }

        System.out.println("Total number of cards: " + totalPileSize);
        System.out.println();
    }

    public void playerDrawCardFromDrawPile(int playerIndex) {
        UnoPlayer player = gameState.getPlayer(playerIndex);
        UnoCard card = gameState.getCardMachine().drawCardFromDrawPile();
        boolean cardIsPlayable = gameState.getModerator().validateCard(gameState, card);
        int currentPlayerIndex = gameState.getCurrentPlayerIndex();

        addCardToPlayer(playerIndex, card);
        player.setLastDrawCard(card);

        if (cardIsPlayable) {
            gameState.setPlayableCards(List.of(card));
        } else {
            gameState.setPlayableCards(List.of());
        }

        if (player.isAI()) {
            currentPlayerIndex = moveToNextPlayer(1);
        }

        int totalCardsRemaining = player.getTotalCardsRemaining();

        var playableCards = gameState.getPlayableCards();
        if (!(player.isAI())) {
            gameEventListener.sendEventMessage(new SetPlayableCardEvent(playableCards), player.getPlayerID());
        }

        CardDrawnEvent cardDrawnEvent = new CardDrawnEvent(playerIndex, card, totalCardsRemaining, currentPlayerIndex);
        gameEventListener.sendEventMessageToAll(cardDrawnEvent);
        sayUno(playerIndex, false);
    }

    public void callUno(int playerIndex) {
        boolean checkCallUno = gameState.getModerator().checkCallUno(gameState, playerIndex);

        if (checkCallUno) {
            applyPenalty(playerIndex, 2);
            sayUno(playerIndex, false);
        } else {
            gameEventListener.sendEventMessageToAll(new NoOpEvent(NoOpEventType.INVALID_CALL_UNO));
        }
    }

    public void playCard(int playerIndex, int cardIndex) {
        UnoPlayer player = gameState.getPlayer(playerIndex);
        if (!(playerIndex == gameState.getCurrentPlayerIndex())) {
            gameEventListener.sendEventMessage(new NoOpEvent(NoOpEventType.INVALID_TURN), player.getPlayerID());
            return;
        }

        UnoCard card = player.getCard(cardIndex);
        var playableCards = gameState.getPlayableCards();

        if (playableCards.contains(card)) {
            UnoCard lastPlayedCard = player.playCard(cardIndex);
            gameState.setLastPlayedCard(lastPlayedCard);
            int currentPlayerIndex = processCardPlayed(card);
            UnoSuit currentSuit = gameState.getCurrentSuit();
            int totalCardsRemaining = player.getTotalCardsRemaining();
            PlayDirection playDirection = gameState.getPlayDirection();

            CardPlayedEvent cardPlayedEvent = new CardPlayedEvent(playerIndex, cardIndex, currentPlayerIndex, totalCardsRemaining, lastPlayedCard, currentSuit, playDirection);
            gameEventListener.sendEventMessageToAll(cardPlayedEvent);
        } else {
            gameEventListener.sendEventMessageToAll(new NoOpEvent(NoOpEventType.INVALID_CARD));
        }

        if (!(player.isAI())) {
            if (!(card.getSuit() == UnoSuit.WILD)) {
                continueTurnCycle();
            }
        }

        if (checkWinner(player)) {
            gameEventListener.sendEventMessageToAll(new AnnounceWinnerEvent(playerIndex));
        }
    }

    public void aiPlayCard(UnoPlayerAI player) {
        aiCallUno();
        UnoCard card = player.selectCard();
        int cardIndex = player.getPlayerHand().indexOf(card);
        int playerIndex = gameState.getPlayerIndex(player);

        if (card == null) {
            playerDrawCardFromDrawPile(playerIndex);
        } else {
            playCard(playerIndex, cardIndex);
        }
    }

    public void sayUno(int playerIndex, boolean sayUno) {
        UnoPlayer player = gameState.getPlayer(playerIndex);
        player.sayUno(sayUno);
        boolean newSayUno = player.getSayUno();

        SaidUnoEvent saidUnoEvent = new SaidUnoEvent(playerIndex, newSayUno);
        gameEventListener.sendEventMessageToAll(saidUnoEvent);
    }

    public void passTurn() {
        int currentPlayerIndex = moveToNextPlayer(1);
        TurnPassedEvent turnPassedEvent = new TurnPassedEvent(currentPlayerIndex);
        gameEventListener.sendEventMessageToAll(turnPassedEvent);
        continueTurnCycle();
    }

    public void updatePlayerImage(int playerIndex, PlayerImage image) {
        gameState.getPlayer(playerIndex).setImage(image);

        ImageChangedEvent imageChangedEvent = new ImageChangedEvent(playerIndex, image);
        gameEventListener.sendEventMessageToAll(imageChangedEvent);
    }

    public void updatePlayerName(int playerIndex, String name) {
        gameState.getPlayer(playerIndex).setName(name);
        NameChangedEvent nameChangedEvent = new NameChangedEvent(playerIndex, name);
        gameEventListener.sendEventMessageToAll(nameChangedEvent);
    }

    public void applyPenalty(int playerIndex, int cardPenalty) {
        List<UnoCard> cardsDrawn = new ArrayList<>();

        for (int i = 0; i < cardPenalty; i++) {
            UnoCard card = gameState.getCardMachine().drawCardFromDrawPile();
            addCardToPlayer(playerIndex, card);
            cardsDrawn.add(card);
        }

        int totalCardsRemaining = gameState.getPlayer(playerIndex).getPlayerHand().size();

        ApplyPenaltyEvent applyPenaltyEvent = new ApplyPenaltyEvent(playerIndex, cardsDrawn, totalCardsRemaining);
        gameEventListener.sendEventMessageToAll(applyPenaltyEvent);
    }

    public void changeSuit(UnoSuit suit) {
        gameState.setCurrentSuit(suit);
        SuitChangedEvent suitChangedEvent = new SuitChangedEvent(suit);
        gameEventListener.sendEventMessageToAll(suitChangedEvent);

        if (!(suit == UnoSuit.WILD)) {
            continueTurnCycle();
        }
    }

    public void aiCallUno() {
        var humanPlayers = getLocalPlayers();
        for (UnoPlayer player : humanPlayers) {
            int playerIndex = gameState.getPlayerIndex(player);
            // Need to add listener method to send this event to server
            callUno(playerIndex);
        }
    }

    public void updatePlayableCards(UnoPlayer player) {
        List<UnoCard> cards = player.getPlayerHand();
        List<UnoCard> playableCards = new ArrayList<>();

        cards.forEach(card -> {
            boolean valid = gameState.getModerator().validateCard(gameState, card);
            if (valid) {
                playableCards.add(card);
            }
        });

        gameState.setPlayableCards(playableCards);
    }

    public void continueTurnCycle() {
        UnoPlayer currentPlayer = gameState.getCurrentPlayer();
        updatePlayableCards(currentPlayer);

        if (currentPlayer.isAI()) {
            turnCycleThread = new Thread(new Runnable() {
                public void run() {

                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        System.out.println("From Server Uno GameManager: Closing thread " + turnCycleThread.getName());
                        System.out.flush();
                    }

                    UnoPlayerAI player = (UnoPlayerAI) currentPlayer;

                    // call listener interface method to send to server
                    // called aiCardPlayed(GameEvent event)
                    aiPlayCard(player);
                    continueTurnCycle();
                }
            }, "[Continue Turn Cycle Thread]");
            turnCycleThread.start();
        } else {
            var playableCards = gameState.getPlayableCards();
            gameEventListener.sendEventMessage(new SetPlayableCardEvent(playableCards), currentPlayer.getPlayerID());
        }
    }

    public void cancelTurnCycle() {
        turnCycleThread.interrupt();
    }

    private List<UnoPlayer> createAIPlayer(int numberOfPlayers) {
        List<UnoPlayer> aiPlayers = new ArrayList<>();

        for (int i = 0; i < numberOfPlayers; i++) {
            UnoPlayerAI player = new UnoPlayerAI(gameState);
            gameState.addPlayer(player);
        }

        return aiPlayers;
    }

    private void dealCards(int numberOfCards, List<UnoPlayer> players) {
        gameState.getCardMachine().dealCards(numberOfCards, players);
    }

    private void addCardToPlayer(int playerIndex, UnoCard card) {
        var player = gameState.getPlayer(playerIndex);
        player.addCard(card);
    }

    private void addCardToDiscardPile(UnoCard card) {
        gameState.getCardMachine().addCardToDiscardPile(card);
        gameState.setLastPlayedCard(card);

        if (!(card.getSuit() == UnoSuit.WILD)) {
            gameState.setCurrentSuit(card.getSuit());
        }

    }

    private boolean validateCard(UnoCard card) {
        UnoModerator moderator = gameState.getModerator();
        return moderator.validateCard(gameState, card);
    }

    private boolean checkWinner(UnoPlayer player) {
        boolean win = false;

        if (player.getPlayerHand().isEmpty()) {
            win = true;
        }

        return win;
    }

    private int processCardPlayed(UnoCard card) {
        addCardToDiscardPile(card);

        int nextPlayerIndex = getNextPlayerIndex(1);

        switch (card.getValue()) {
            case UnoValue.REVERSE:
                reversePlayDirection();
                int numberOfPlayers = gameState.getPlayers().size();

                if (numberOfPlayers == 2) {
                    return moveToNextPlayer(2);
                } else {
                    return moveToNextPlayer(1);
                }
            case UnoValue.SKIP:
                return moveToNextPlayer(2);
            case UnoValue.DRAW_TWO:
                applyPenalty(nextPlayerIndex, 2);
                return moveToNextPlayer(2);
            case UnoValue.WILD_DRAW_FOUR:
                applyPenalty(nextPlayerIndex, 4);
                return moveToNextPlayer(2);
            case UnoValue.DRAW_TW0_STACK:
                gameState.addStackPenalty(2);
                return moveToNextPlayer(1);
            case UnoValue.WILD_DRAW_FOUR_STACK:
                gameState.addStackPenalty(4);
                return moveToNextPlayer(1);
            default:
                return moveToNextPlayer(1);
        }
    }

    private List<UnoPlayer> getAIPlayers() {
        return gameState.getPlayers()
                .stream()
                .filter(UnoPlayer::isAI)
                .toList();
    }

    private List<UnoPlayer> getLocalPlayers() {
        return gameState.getPlayers()
                .stream()
                .filter(player -> !(player.isAI()))
                .toList();
    }

    private void reversePlayDirection() {
        PlayDirection direction = gameState.getPlayDirection();
        direction = (direction == PlayDirection.FORWARD) ? PlayDirection.REVERSE : PlayDirection.FORWARD;
        gameState.setPlayDirection(direction);
    }

    private int getNextPlayerIndex(int numberToSkipAhead) {
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

    private int moveToNextPlayer(int numberToSkipAhead) {
        int nextPlayerIndex = getNextPlayerIndex(numberToSkipAhead);
        gameState.setCurrentPlayerIndex(nextPlayerIndex);
        return nextPlayerIndex;
    }

    private UnoPlayer getNextPlayer() {
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

    /*
    public void processAICardPlayed(UnoPlayer player, UnoCard card) {
        int playerIndex = gameState.getPlayerIndex(player);

        if (card == null) {
            playerDrawCardFromDrawPile(playerIndex);

            moveToNextPlayer(1);
        } else {
            processCardPlayed(card);
        }
    }
     */

    /*
    public void swapPlayerPositions(int player1Index, int player2Index) {
        var players = gameState.getPlayers();
        Collections.swap(players, player1Index, player2Index);
    }
     */

}
