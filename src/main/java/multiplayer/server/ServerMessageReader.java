package multiplayer.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ServerMessageReader {

    private BufferedReader reader;
    private Server server;
    private int playerID;
    private Thread thread;
    private volatile boolean running = true;

    public ServerMessageReader(Server server, Socket socket) {
        this.server = server;

        try {
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void listen() {

        this.thread = new Thread(new Runnable() {
            public void run() {

                while (running) {
                    try {
                        // listen to the messages coming in from the client
                        String message = reader.readLine();

                        // when the socket is closed. readLine() returns null
                        if (message == null) {
                            System.out.println("From Server Message Reader: Closing thread " + thread.getName());
                            break;
                        }

                        // passes the message off to the server and calls the servers method to process the message
                        server.handleMessage(message, playerID);
                    } catch (IOException e) {
                        System.out.println("From Server Message Reader: Error closing socket");
                        System.out.flush();
                    }
                }
            }
        }, "[Server Message Reader: " + playerID + "]");
        thread.start();
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public void shutDown() {
        running = false;
    }
}
