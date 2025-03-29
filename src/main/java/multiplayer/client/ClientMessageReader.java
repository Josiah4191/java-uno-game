package multiplayer.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientMessageReader {

    private Client client;
    private BufferedReader reader;
    private volatile boolean isRunning = true;

    public ClientMessageReader(Client client, Socket socket) {
        this.client = client;

        try {
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void startReading() {

        Thread thread = new Thread(new Runnable() {
            public void run() {

                while (isRunning) {
                    try {

                        String message = reader.readLine();
                        client.handleMessage(message);
                        System.out.println("Client received message from server: " + message);
                        System.out.flush();

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        thread.start();
    }
}
