package multiplayer.server;

import multiplayer.client.unoclient.PlayCard;

public class Test {
    public static void main(String[] args) {

        // create Server

        Server server = new Server();

        PlayCard playCard = new PlayCard(1);

        server.getActionType(playCard.toJson());


    }
}
