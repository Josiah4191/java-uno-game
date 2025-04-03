package multiplayer.server;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ServerMessageWriter {

    private BlockingQueue<String> messages = new LinkedBlockingQueue<>(1000);
    private BufferedWriter writer;
    private int playerID;
    private volatile boolean running = true;
    private Thread thread;

    public ServerMessageWriter(Socket socket) {
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
            System.out.println("From Server Message Writer: Failed to store message in queue");
            System.out.flush();
        }
    }

    public void sendMessage(String message) {
        try {
            writer.write(message);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            System.out.println("From Server Message Writer: Error getting output stream from socket");
            System.out.flush();
        }
    }

    public void startWriting() {
            this.thread = new Thread(new Runnable() {
                public void run() {

                    while (running) {
                        try {
                            String message = messages.take();

                            if (message.equals("SHUTDOWN")) {
                                System.out.println("From Server Message Writer: Closing thread " + thread.getName());
                                System.out.flush();
                                messages.clear();
                                running = false;
                                break;
                            }

                            sendMessage(message);
                            System.out.println("From Server Message Writer: Server sent message to client: " + message);
                            System.out.flush();
                        } catch (InterruptedException e) {
                            System.out.println("From Server Message Writer: Error shutting down Server Message Writer");
                            System.out.flush();
                        }
                    }
                }
            }, "[Server Message Writer Thread: " + playerID + "]");
            thread.start();
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

}
