package multiplayer.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class ServerMessageReader {

    private Server server;
    private BufferedReader reader;
    private int playerID;
    private volatile boolean isRunning = true;

    public ServerMessageReader(Server server, Socket socket) {
        this.server = server;

        try {
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void listen() {

        Thread thread = new Thread(new Runnable() {
            public void run() {

                while (isRunning) {
                    try {

                        // listen to the messages coming in from the client
                        String message = reader.readLine();
                        // passes the message off to the server and calls the servers method to process the message
                        server.handleMessage(message, playerID);
                        System.out.println("Server received message from client: " + message);
                        System.out.flush();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        thread.start();
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public int getPlayerID() {
        return playerID;
    }

    public void setRunning(boolean status) {
        this.isRunning = status;
    }

    public boolean isRunning() {
        return isRunning;
    }

}
