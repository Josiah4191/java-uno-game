package com.josiah.uno.multiplayer.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientMessageReader {

    private BufferedReader reader;
    private Client client;
    private Socket socket;
    private Thread thread;
    private volatile boolean running = true;

    public ClientMessageReader(Client client, Socket socket) {
        this.client = client;
        this.socket = socket;

        try {
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void startReading() {

        this.thread = new Thread(new Runnable() {
            public void run() {

                while (running) {
                    try {
                        String message = reader.readLine();
                        client.handleMessage(message);
                        System.out.println("From Client Message Reader: Client received message from server: " + message);
                        System.out.flush();

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }, "[Client Message Reader]");
        thread.start();
    }

    public void shutDown() {
        // set running to false
        System.out.println("From Client Message Reader: Closing thread " + thread.getName());
        System.out.flush();
        running = false;

        try {
            socket.close();
        } catch (IOException e) {
            System.out.println("From Client Message Reader: Error closing socket");
            System.out.flush();
        }
    }
}
