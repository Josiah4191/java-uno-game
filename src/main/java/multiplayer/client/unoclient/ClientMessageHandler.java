package multiplayer.client.unoclient;

import multiplayer.client.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientMessageHandler {

    private Socket socket;
    private Client client;

    public ClientMessageHandler(Client client, Socket socket) {
        this.socket = socket;
    }

    public void listen() {

        try {
            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);

            String message = br.readLine();
            client.handleMessage(message);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
