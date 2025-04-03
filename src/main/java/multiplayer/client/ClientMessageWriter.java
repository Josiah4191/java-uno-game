package multiplayer.client;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ClientMessageWriter {

    private BlockingQueue<String> messages = new LinkedBlockingQueue<>(1000);
    private BufferedWriter writer;
    private Thread thread;
    private volatile boolean running = true;

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

        this.thread = new Thread(new Runnable() {
            public void run() {

                while (running) {
                    try {
                        String message = messages.take();

                        if (message.equals("SHUTDOWN")) {
                            System.out.println("From Client Message Writer: Closing thread " + thread.getName());
                            System.out.flush();
                            messages.clear();
                            running = false;
                            break;
                        }

                        sendMessage(message);
                        System.out.println("From Client Message Writer: Client sent message to server: " + message);
                        System.out.flush();

                    } catch (InterruptedException e) {
                        System.out.println("From Client Message Writer: Error shutting down Client Message Writer");
                        System.out.flush();
                        throw new RuntimeException(e);
                    }
                }
            }
        }, "[Client Message Writer]");
        thread.start();
    }

}
