package multiplayer.client;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import model.cardgame.card.unocard.*;
import model.cardgame.unogame.*;
import model.image.playerimage.PlayerImage;
import model.player.cardplayer.unoplayer.UnoPlayer;
import multiplayer.server.Server;
import multiplayer.server.servermessage.*;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class Client {

    private Server server;
    private Socket socket;
    private ClientMessageReader clientMessageReader;
    private ClientMessageWriter clientMessageWriter;
    private ClientUnoGameManager gameManager;
    private volatile boolean isRunning;

    public void connectToHost(String address, int port) {
        try {
            this.socket = new Socket(address, port);

            clientMessageWriter = new ClientMessageWriter(socket);
            clientMessageWriter.startWriting();

            clientMessageReader = new ClientMessageReader(this, socket);
            clientMessageReader.startReading();

        } catch (IOException e) {
            System.out.println("Error connecting to server");
        }
    }

    public GameEventType getEventType(String message) {
        Gson gson = new Gson();
        JsonObject json = gson.fromJson(message, JsonObject.class);
        String type = json.get("type").getAsString();
        return GameEventType.valueOf(type);
    }

    public synchronized void handleMessage(String message) {
        GameEventType type = getEventType(message);

            switch (type) {
                case GameEventType.SETUP_GAME:
                    handleSetupGame(message);
                    break;
                case GameEventType.ADD_LOCAL_PLAYER:
                    handleAddLocalPlayer(message);
                    break;
                case GameEventType.NAME_CHANGED:
                    handleNameChanged(message);
                    break;
                case GameEventType.IMAGE_CHANGED:
                    handleImageChanged(message);
                    break;
                case GameEventType.TURN_PASSED:
                    handleTurnPassed(message);
                    break;
                case GameEventType.SAID_UNO:
                    handleSaidUno(message);
                    break;
                case GameEventType.APPLY_PENALTY:
                    handleApplyPenalty(message);
                    break;
                case GameEventType.CARD_DRAWN:
                    handleCardDrawn(message);
                    break;
                case GameEventType.CARD_PLAYED:
                    handleCardPlayed(message);
                    break;
                case GameEventType.LAST_CARD_PLAYED:
                    handleLastCardPlayed(message);
                    break;
                case GameEventType.ANNOUNCE_WINNER:
                    handleAnnounceWinner(message);
                    break;
                default:
                    System.out.println("Unknown Game Event Type");
                    break;
            }
    }

    public void handleSetupGame(String message) {
        Gson gson = new Gson();
        SetupGameEvent setupGame = gson.fromJson(message, SetupGameEvent.class);

        UnoSuit currentSuit = setupGame.getCurrentSuit();
        Difficulty difficulty = setupGame.getDifficulty();
        UnoEdition edition = setupGame.getEdition();
        UnoCardTheme theme = setupGame.getTheme();
        List<UnoPlayer> players = setupGame.getPlayers();
        List<UnoCard> localPlayerCards = setupGame.getLocalPlayerCards();
        UnoCard lastPlayedCard = setupGame.getLastPlayedCard();

        gameManager.updateDifficulty(difficulty);
        gameManager.updateCurrentSuit(currentSuit);
        gameManager.updateEdition(edition);
        gameManager.updateTheme(theme);
        gameManager.updatePlayerListAdd(players);
        gameManager.updateLastPlayedCard(lastPlayedCard);
        gameManager.updateLocalPlayerHandAddCard(localPlayerCards);

        System.out.println();
        System.out.println("Setup Game Event Occurred");
        System.out.println();

        /*
        System.out.println("List of players inside the handle setup method for the client after unpacking: ");
        players.forEach((player) -> {
            System.out.println("Player is AI: " + player.isAI());
            System.out.println("Player index: " + players.indexOf(player));
            System.out.println("Player name: " + player.getName());
            System.out.println("Player image: " + player.getImage());
            System.out.println("Player ID: " + player.getPlayerID());
            System.out.println();
        });
         */
    }

    public void handleAddLocalPlayer(String message) {
        Gson gson = new Gson();
        AddLocalPlayerEvent localPlayerEvent = gson.fromJson(message, AddLocalPlayerEvent.class);
        UnoPlayer localPlayer = localPlayerEvent.getLocalPlayer();

        gameManager.setLocalPlayer(localPlayer);

        System.out.println();
        System.out.println("Add Local Player Event Occurred");
        System.out.println();
    }

    public void handleNameChanged(String message) {
        Gson gson = new Gson();
        NameChangedEvent nameChangedEvent = gson.fromJson(message, NameChangedEvent.class);
        int playerIndex = nameChangedEvent.getPlayerIndex();
        String name = nameChangedEvent.getName();

        gameManager.updatePlayerName(playerIndex, name);
    }

    public void handleImageChanged(String message) {
        Gson gson = new Gson();
        ImageChangedEvent imageChangedEvent = gson.fromJson(message, ImageChangedEvent.class);
        int playerIndex = imageChangedEvent.getPlayerIndex();
        PlayerImage image = imageChangedEvent.getImage();

        gameManager.updatePlayerImage(playerIndex, image);
    }

    public void handleTurnPassed(String message) {
        Gson gson = new Gson();
        TurnPassedEvent turnPassedEvent = gson.fromJson(message, TurnPassedEvent.class);
        int currentPlayerIndex = turnPassedEvent.getCurrentPlayerIndex();

        gameManager.updateCurrentPlayerIndex(currentPlayerIndex);
    }

    public void handleSaidUno(String message) {
        Gson gson = new Gson();
        SaidUnoEvent saidUnoEvent = gson.fromJson(message, SaidUnoEvent.class);
        int playerIndex = saidUnoEvent.getPlayerIndex();
        boolean sayUno = saidUnoEvent.isSayUno();

        gameManager.updateSayUno(playerIndex, sayUno);
    }

    public void handleApplyPenalty(String message) {
        Gson gson = new Gson();
        ApplyPenaltyEvent applyPenaltyEvent = gson.fromJson(message, ApplyPenaltyEvent.class);
        int playerIndex = applyPenaltyEvent.getPlayerIndex();
        int totalNumberOfCards = applyPenaltyEvent.getTotalCardsRemaining();
        List<UnoCard> cardsDrawn = applyPenaltyEvent.getCardsDrawn();

        UnoPlayer localPlayer = gameManager.getLocalPlayer();
        if (gameManager.getPlayer(playerIndex).equals(localPlayer)) {
            gameManager.updateLocalPlayerHandAddCard(cardsDrawn);
        }

        gameManager.updatePlayerCardNumberMap(playerIndex, totalNumberOfCards);

        System.out.println();
        System.out.println("Apply Penalty Event Occurred");
        System.out.println();
    }

    public void handleCardDrawn(String message) {
        Gson gson = new Gson();
        CardDrawnEvent cardDrawnEvent = gson.fromJson(message, CardDrawnEvent.class);
        int playerIndex = cardDrawnEvent.getPlayerIndex();
        int currentPlayerIndex = cardDrawnEvent.getCurrentPlayerIndex();
        int totalNumberOfCards = cardDrawnEvent.getTotalCardsRemaining();
        UnoCard drawnCard = cardDrawnEvent.getDrawnCard();

        UnoPlayer localPlayer = gameManager.getLocalPlayer();
        if (gameManager.getPlayer(playerIndex).equals(localPlayer)) {
            gameManager.updateLocalPlayerHandAddCard(List.of(drawnCard));
            gameManager.updateLocalPlayerLastDrawnCard(playerIndex, drawnCard);
        }

        gameManager.updatePlayerCardNumberMap(playerIndex, totalNumberOfCards);
        gameManager.updateCurrentPlayerIndex(currentPlayerIndex);
        gameManager.updateGameView(cardDrawnEvent);

        System.out.println();
        System.out.println("Card Drawn Event Occurred");
        System.out.println();
    }

    public void handleCardPlayed(String message) {
        Gson gson = new Gson();
        CardPlayedEvent cardPlayedEvent = gson.fromJson(message, CardPlayedEvent.class);
        int playerIndex = cardPlayedEvent.getPlayerIndex();
        int currentPlayerIndex = cardPlayedEvent.getCurrentPlayerIndex();
        int cardIndex = cardPlayedEvent.getCardIndex();
        int totalCardsRemaining = cardPlayedEvent.getTotalCardsRemaining();
        UnoCard lastPlayedCard = cardPlayedEvent.getLastPlayedCard();
        UnoSuit currentSuit = cardPlayedEvent.getCurrentSuit();
        PlayDirection playDirection = cardPlayedEvent.getPlayDirection();

        UnoPlayer localPlayer = gameManager.getLocalPlayer();
        if (gameManager.getPlayer(playerIndex).equals(localPlayer)) {
            gameManager.updateLocalPlayerHandRemoveCard(cardIndex);
        }

        gameManager.updatePlayerCardNumberMap(playerIndex, totalCardsRemaining);
        gameManager.updateCurrentPlayerIndex(currentPlayerIndex);
        gameManager.updateLastPlayedCard(lastPlayedCard);
        gameManager.updateCurrentSuit(currentSuit);
        gameManager.updatePlayDirection(playDirection);
        gameManager.updateGameView(cardPlayedEvent);

        System.out.println();
        System.out.println("Card Played Event Occurred");
        System.out.println();


    }

    public void handleLastCardPlayed(String message) {
        Gson gson = new Gson();
        LastCardPlayedEvent lastCardPlayedEvent = gson.fromJson(message, LastCardPlayedEvent.class);
        UnoCard card = lastCardPlayedEvent.getLastPlayedCard();

        gameManager.updateLastPlayedCard(card);
    }

    public void handleAnnounceWinner(String message) {
        Gson gson = new Gson();
        AnnounceWinnerEvent announceWinnerEvent = gson.fromJson(message, AnnounceWinnerEvent.class);
        int playerIndex = announceWinnerEvent.getPlayerIndex();

        gameManager.announceWinner(playerIndex);
    }

    public void sendMessage(String message) {
        clientMessageWriter.storeMessage(message);
    }

    public void createServer() {
        ServerUnoGameManager serverUnoGameManager = new ServerUnoGameManager();
        UnoGameState gameState = serverUnoGameManager.getGameState();

        this.server = new Server();
        server.setGameManager(serverUnoGameManager);
        server.setGameState(gameState);
        server.start();

        serverUnoGameManager.setAiActionListener(server);
        serverUnoGameManager.setGameEventListener(server);

        connectToHost("localhost", server.getPort());
    }

    public void setGameManager(ClientUnoGameManager gameManager) {
        this.gameManager = gameManager;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }
}