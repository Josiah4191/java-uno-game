package multiplayer.client;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ClientMessageWriter {

    private BlockingQueue<String> messages = new LinkedBlockingQueue<>(1000);
    private BufferedWriter writer;
    private volatile boolean isRunning = true;

    public ClientMessageWriter(Socket socket) {

        try {
            this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void storeMessage(String message) {
        try {
            messages.put(message);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
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
                        System.out.println("Client sent message to server: " + message);
                        System.out.flush();

                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        thread.start();
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }
}
