package multiplayer.server;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import model.cardgame.card.unocard.UnoSuit;
import multiplayer.client.clientmessage.*;
import multiplayer.server.servermessage.*;
import model.cardgame.card.unocard.UnoCardTheme;
import model.cardgame.card.unocard.UnoEdition;
import model.cardgame.unogame.Difficulty;
import model.image.playerimage.PlayerImage;
import model.player.cardplayer.unoplayer.UnoPlayer;
import model.cardgame.unogame.ServerUnoGameManager;
import model.cardgame.unogame.UnoGameState;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/*
    Need to create a message sender class that queues messages to the clients
    Need to think about sending updates to all clients as well for information that matters to all clients
 */

public class Server implements AIActionListener, GameEventListener {

    private ServerSocket serverSocket;
    private volatile boolean running = true;
    private Map<Integer, ServerMessageReader> clientHandlerReaders = new HashMap<>();
    private Map<Integer, ServerMessageWriter> clientHandlerWriters = new HashMap<>();
    private int playerID = 1;
    private ServerUnoGameManager gameManager;
    private UnoGameState gameState;
    private int port = 12345;

    public void start() {
        openConnection();
    }

    public int getPort() {
        return port;
    }

    public void setGameManager(ServerUnoGameManager gameManager) {
        this.gameManager = gameManager;
    }

    public void setGameState(UnoGameState gameState) {
        this.gameState = gameState;
    }

    public void openConnection() {

        Thread thread = new Thread(new Runnable() {
            public void run() {
                while (running) {
                    try {
                        serverSocket = new ServerSocket(port);
                        while (running) {
                            Socket socket = serverSocket.accept();

                            System.out.println("Client connected");

                            ServerMessageReader clientHandlerReader = new ServerMessageReader(Server.this, socket);
                            clientHandlerReader.setPlayerID(1);
                            System.out.println("Client handler (reader) created with playerID: " + playerID);

                            ServerMessageWriter clientHandlerWriter = new ServerMessageWriter(socket);
                            clientHandlerWriter.setPlayerID(1);
                            System.out.println("Client handler (writer) created with playerID: " + playerID);

                            clientHandlerWriters.put(playerID, clientHandlerWriter);
                            clientHandlerWriter.startWriting();

                            clientHandlerReaders.put(playerID, clientHandlerReader);
                            clientHandlerReader.listen();

                            playerID++;
                        }
                    } catch (IOException e) {
                        if (!running) {
                            break;
                        }
                        System.out.println("Error opening server connection");
                    }
                }
            }
        });
        thread.start();
    }

    public void closeConnection() {
        running = false;
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (IOException e) {
            System.out.println("Error closing server connection");
        }
    }

    public void sendMessage(String message, int playerID) {
        ServerMessageWriter clientHandlerWriter = clientHandlerWriters.get(playerID);

        if (clientHandlerWriter != null) {
            clientHandlerWriter.storeMessage(message);
        }

    }

    public void sendMessageToAllClients(String message) {
        for (var clientHandlerWriter: clientHandlerWriters.values()) {
            clientHandlerWriter.storeMessage(message);
        }
    }

    public synchronized void handleMessage(String message, int playerID) {
        GameActionType type = getActionType(message);

        switch (type) {
            case GameActionType.SETUP_GAME:
                handleSetupGame(message, playerID);
                break;
            case GameActionType.JOIN_GAME:
                handleJoinGame(message, playerID);
                break;
            case GameActionType.CHANGE_NAME:
                handleChangeName(message, playerID);
                break;
            case GameActionType.CHANGE_IMAGE:
                handleChangeImage(message, playerID);
                break;
            case GameActionType.PASS_TURN:
                handlePassTurn(playerID);
                break;
            case GameActionType.SAY_UNO:
                handleSayUno(message, playerID);
                break;
            case GameActionType.CALL_UNO:
                handleCallUno(message);
                break;
            case GameActionType.PLAY_CARD:
                handlePlayCard(message, playerID);
                break;
            case GameActionType.DRAW_CARD:
                handleDrawCard(message, playerID);
                break;
            case GameActionType.CHANGE_SUIT:
                handleSuitChange(message, playerID);
                break;
            default:
                System.out.println("Server received unknown action type");
        }
    }

    public void handleSetupGame(String message, int playerID) {
        Gson gson = new Gson();
        SetupGameAction setupGame = gson.fromJson(message, SetupGameAction.class);
        Difficulty difficulty = setupGame.getDifficulty();
        UnoEdition edition = setupGame.getEdition();
        UnoCardTheme theme = setupGame.getTheme();
        int numberOfOpponents = setupGame.getNumberOfOpponents();

        // call game manager method
        gameManager.setupGame(edition, theme, difficulty, numberOfOpponents, playerID);
    }

    public void handleJoinGame(String message, int playerID) {
        Gson gson = new Gson();
        JoinGameAction joinGame = gson.fromJson(message, JoinGameAction.class);
        String name = joinGame.getName();
        PlayerImage playerImage = joinGame.getPlayerImage();

        // call game manager method
        gameManager.addLocalPlayer(name, playerID, playerImage);
    }

    public void handleChangeName(String message, int playerID) {
        Gson gson = new Gson();
        ChangeNameAction changeNameAction = gson.fromJson(message, ChangeNameAction.class);
        String name = changeNameAction.getName();
        UnoPlayer player = gameState.getPlayerFromPlayerID(playerID);
        int playerIndex = gameState.getPlayerIndex(player);

        // call game manager method
        GameEvent changeNameEvent = gameManager.updatePlayerName(playerIndex, name);

        // send event to client
        String changeNameEventMessage = changeNameEvent.toJson();
        sendMessage(changeNameEventMessage, playerID);
    }

    public void handleChangeImage(String message, int playerID) {
        Gson gson = new Gson();
        ChangeImageAction changeImageAction = gson.fromJson(message, ChangeImageAction.class);
        UnoPlayer player = gameState.getPlayerFromPlayerID(playerID);
        int playerIndex = gameState.getPlayerIndex(player);
        PlayerImage image = changeImageAction.getPlayerImage();

        // call game manager method
        gameManager.updatePlayerImage(playerIndex, image);
    }

    public void handlePassTurn(int playerID) {
        UnoPlayer player = gameState.getPlayerFromPlayerID(playerID);
        int playerIndex = gameState.getPlayerIndex(player);

        // call game manager method
        gameManager.passTurn(playerIndex);
    }

    public void handleSayUno(String message, int playerID) {
        Gson gson = new Gson();
        SayUnoAction sayUnoAction = gson.fromJson(message, SayUnoAction.class);
        boolean sayUno = sayUnoAction.isSayUno();
        UnoPlayer player = gameState.getPlayerFromPlayerID(playerID);
        int playerIndex = gameState.getPlayerIndex(player);

        // call game manager method
        gameManager.sayUno(playerIndex, sayUno);
    }

    public void handleCallUno(String message) {
        Gson gson = new Gson();
        CallUnoAction callUno = gson.fromJson(message, CallUnoAction.class);
        int playerIndex = callUno.getPlayerIndex();

        // call game manager method
        gameManager.callUno(playerIndex);
    }

    public void handlePlayCard(String message, int playerID) {
        Gson gson = new Gson();
        PlayCardAction playCard = gson.fromJson(message, PlayCardAction.class);
        int cardIndex = playCard.getCardIndex();
        UnoPlayer player = gameState.getPlayerFromPlayerID(playerID);
        int playerIndex = gameState.getPlayers().indexOf(player);

        // call game manager method
        gameManager.playCard(playerIndex, cardIndex);
    }

    public void handleDrawCard(String message, int playerID) {
        UnoPlayer player = gameState.getPlayerFromPlayerID(playerID);
        int playerIndex = gameState.getPlayers().indexOf(player);

        // call game manager method
        gameManager.playerDrawCardFromDrawPile(playerIndex);
    }

    public void handleSuitChange(String message, int playerID) {
        Gson gson = new Gson();
        ChangeSuitAction changeSuitAction = gson.fromJson(message, ChangeSuitAction.class);
        UnoSuit suit = changeSuitAction.getSuit();

        // call game manager method
        gameManager.changeSuit(suit);
    }

    public void aiSendEventMessage(GameEvent event) {
        // send event to client
        String message = event.toJson();
        sendMessageToAllClients(message);
    }

    public void sendEventMessage(GameEvent event, int playerID) {
        // send event to client
        String message = event.toJson();
        sendMessage(message, playerID);
    }

    public void sendEventMessageToAll(GameEvent event) {
        String message = event.toJson();
        sendMessageToAllClients(message);
    }

    public GameActionType getActionType(String message) {
        Gson gson = new Gson();
        JsonObject json = gson.fromJson(message, JsonObject.class);
        String type = json.get("type").getAsString();
        return GameActionType.valueOf(type);
    }

}


