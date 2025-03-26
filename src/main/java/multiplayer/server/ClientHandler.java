package multiplayer.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientHandler {

    private Server server;
    private Socket socket;
    private int playerID;
    private boolean running = true;

    public ClientHandler(Server server, Socket socket) {
        this.server = server;
        this.socket = socket;
    }

    public void listen() {

        while (running) {
            Thread thread = new Thread(new Runnable() {
                public void run() {

                    try {

                        // listen to the messages coming in from the client
                        InputStream is = socket.getInputStream();
                        InputStreamReader isr = new InputStreamReader(is);
                        BufferedReader br = new BufferedReader(isr);
                        String message = br.readLine();

                        // passes the message off to the server and calls the servers method to process the message
                        server.handleMessage(message, playerID);

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });

            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }

    public void setID(int playerID) {
        this.playerID = playerID;
    }

    public void setRunning(boolean status) {
        this.running = status;
    }

    public int getPlayerID() {
        return playerID;
    }

}
