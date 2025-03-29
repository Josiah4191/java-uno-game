package multiplayer.server;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ServerMessageWriter {

    private Socket socket;
    private int playerID;
    private volatile boolean isRunning = true;
    private BlockingQueue<String> messages = new LinkedBlockingQueue<>(1000);
    private BufferedWriter writer;

    public ServerMessageWriter(Socket socket) {
        this.socket = socket;

        try {
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void storeMessage(String message) {
        try {
            messages.put(message);
        } catch (InterruptedException e) {
            System.out.println("Failed to store message in queue");
        }
    }

    public void sendMessage(String message) {
        try {
            writer.write(message);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            System.out.println("Error getting output stream from socket");
        }
    }

    public void startWriting() {
            Thread thread = new Thread(new Runnable() {
                public void run() {

                    while (isRunning) {
                        try {
                            String message = messages.take();
                            sendMessage(message);
                            System.out.println("Server sent message to client: " + message);
                            System.out.flush();
                        } catch (InterruptedException e) {
                            System.out.println("Failed to retrieve message from queue");
                        }
                    }
                }
            });
            thread.start();
    }

    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    public Socket getSocket() {
        return socket;
    }
}
