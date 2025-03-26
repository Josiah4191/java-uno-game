package multiplayer.client;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import model.cardgame.unogame.UnoGameState;
import multiplayer.client.unoclient.ClientMessageHandler;
import multiplayer.client.unoclient.ClientUnoGameManager;
import multiplayer.server.unoserver.GameEventType;

import java.io.*;
import java.net.Socket;

public class Client {

    private Socket socket;
    private volatile boolean running;
    private ClientUnoGameManager gameManager = new ClientUnoGameManager();

    public Client(String address, int port) {
        try {
            this.socket = new Socket(address, port);
            ClientMessageHandler clientMessageHandler = new ClientMessageHandler(this, socket);
            clientMessageHandler.listen();
        } catch (IOException e) {
            System.out.println("Error connecting to server");
        }
    }

    public void handleMessage(String message) {
        Gson gson = new Gson();
        JsonObject json = gson.fromJson(message, JsonObject.class);
        String event = json.get("type").getAsString();
        GameEventType eventType = GameEventType.valueOf(event);

        switch (eventType) {
            case GameEventType.UPDATE_VIEW:
                handleUpdateView(message);
                break;
            default:
                break;
        }

    }

    public void handleUpdateView(String message) {
        Gson gson = new Gson();
        UnoGameState gameState = gson.fromJson(message, UnoGameState.class);
        gameManager.setGameState(gameState);
    }

    public void sendMessage(String message) {
        try {
            OutputStream os = socket.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);

            // write method/send message
            bw.write(message);

        } catch (IOException e) {
            System.out.println("Error getting output stream from socket");
        }
    }

    public void setGameManager(ClientUnoGameManager gameManager) {
        this.gameManager = gameManager;
    }






}

/*

    This class listens and receives messages from the server.
    It will create an object that can interpret the messages it receives and update the
    client's game via their game manager.
 */