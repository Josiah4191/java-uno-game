package multiplayer.server;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import multiplayer.client.unoclient.ClientActionType;
import multiplayer.client.unoclient.CallUno;
import multiplayer.client.unoclient.DrawCard;
import multiplayer.client.unoclient.PlayCard;
import multiplayer.server.unoserver.ServerUnoGameManager;
import model.cardgames.unogame.UnoGameState;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Server {

    private ServerSocket serverSocket;
    private volatile boolean running = true;
    private Map<Integer, ClientHandler> clientHandlers = new HashMap<>();
    private int playerID = 1;
    private ServerUnoGameManager gameManager;
    private UnoGameState gameState;
    private int port = 12345;

    public void run() {
        openConnection();
    }

    public void openConnection() {

        while (running) {
            try {
                serverSocket = new ServerSocket(12345);

                while (running) {
                    Socket socket = serverSocket.accept();
                    ClientHandler client = new ClientHandler(this, socket);
                    client.setID(1);
                    clientHandlers.put(playerID, client);
                    client.listen();
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

    public void readMessage(String greeting) {
        System.out.println(greeting);
    }

    public void handleMessage(String message, int playerID) {
        // checks the message and calls the correct game manager method
        ClientActionType type = getActionType(message);

        switch (type) {
            case ClientActionType.PLAY_CARD:
                handlePlayCard(message, playerID);
                break;
            case ClientActionType.PASS_TURN:
                handlePassTurn(playerID);
                break;
            case ClientActionType.CALL_UNO:
                handleCallUno(message, playerID);
                break;
            case ClientActionType.SAY_UNO:
                handleSayUno(playerID);
                break;
            case ClientActionType.DRAW_CARD:
                handleDrawCard(message, playerID);
                break;
            default:
                System.out.println("Unknown Action Type");
        };
    }

    public ClientActionType getActionType(String message) {
        Gson gson = new Gson();
        JsonObject json = gson.fromJson(message, JsonObject.class);
        String type = json.get("type").getAsString();
        return ClientActionType.valueOf(type);
    }

    public void handlePlayCard(String message, int playerID) {
        Gson gson = new Gson();
        PlayCard playCard = gson.fromJson(message, PlayCard.class);
        int cardIndex = playCard.getCardIndex();
        int playerIndex = gameState.getPlayerIndex(playerID);

        // call game manager method
        gameManager.humanPlayCard(playerIndex, cardIndex);
    }

    public void handleCallUno(String message, int playerID) {
        Gson gson = new Gson();
        CallUno callUno = gson.fromJson(message, CallUno.class);
        int playerIndex = callUno.getPlayerIndex();

        // call game manager method
        gameManager.callUno(playerIndex);

    }

    /*
    this might be handled client side, and handled differently than here. i need to send the card
    that they draw if its not played, but otherwise if it is played, i need to just send the card that was played.
     */
    public void handleDrawCard(String message, int playerID) {
        Gson gson = new Gson();
        DrawCard drawCard = gson.fromJson(message, DrawCard.class);
        int playerIndex = gameState.getPlayerIndex(playerID);

        // call game manager method
        gameManager.playerDrawCardFromDrawPile(playerIndex);

    }

    public void handleSayUno(int playerID) {
        int playerIndex = gameState.getPlayerIndex(playerID);
        // call game manager method
        gameManager.sayUno(playerIndex);
    }

    public void handlePassTurn(int playerID) {
        int playerIndex = gameState.getPlayerIndex(playerID);
        // call game manager method
        gameManager.passTurn(playerIndex);
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

    public int getPort() {
        return port;
    }

    public void setGameManager(ServerUnoGameManager gameManager) {
        this.gameManager = gameManager;
    }

    public void setGameState(UnoGameState gameState) {
        this.gameState = gameState;
    }


}


