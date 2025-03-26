package multiplayer.server;


import multiplayer.client.Client;

public class ServerController {

    private Server server = new Server();

    public void run() {

        Client client = new Client("localhost", server.getPort());

    }






}
