package test;

import games.Difficulty;
import games.cardgames.cards.unocards.UnoCardTheme;
import games.cardgames.cards.unocards.UnoEdition;
import games.cardgames.unogame.UnoGameManager;
import games.players.cardplayers.unoplayers.UnoPlayer;
import games.players.cardplayers.unoplayers.UnoPlayerAI;

import java.util.List;

public class TestPlayerManagement {

    public static void main(String[] args) {
        // initialize the game
        UnoGameManager manager = new UnoGameManager(UnoEdition.CLASSIC, Difficulty.EASY);

        // get the players in the game
        var players = manager.getPlayers();
        System.out.println("Number of players: " + players.size());
        System.out.println("List of players: ");
        players.forEach(System.out::println);
        System.out.println();

        // create a single player
        UnoPlayer playerJosiah = new UnoPlayer();
        playerJosiah.setName("Josiah");

        // add the player to the game
        manager.addPlayer(playerJosiah);

        // output the players again
        System.out.println("Number of players: " + players.size());
        System.out.println("List of players: ");
        players.forEach(System.out::println);
        System.out.println();

        // create several AI players
        UnoPlayer playerAI1 = new UnoPlayerAI(manager.getGameState());
        UnoPlayer playerAI2 = new UnoPlayerAI(manager.getGameState());
        UnoPlayer playerAI3 = new UnoPlayerAI(manager.getGameState());
        UnoPlayer playerAI4 = new UnoPlayerAI(manager.getGameState());
        UnoPlayer playerAI5 = new UnoPlayerAI(manager.getGameState());
        UnoPlayer playerAI6 = new UnoPlayerAI(manager.getGameState());
        playerAI1.setName("AI 1");
        playerAI2.setName("AI 2");
        playerAI3.setName("AI 3");
        playerAI4.setName("AI 4");
        playerAI5.setName("AI 5");
        playerAI6.setName("AI 6");

        // add the AI players to the game
        manager.addPlayers(List.of(playerAI1, playerAI2, playerAI3, playerAI4, playerAI5, playerAI6));

        // output the players again
        System.out.println("Number of players: " + players.size());
        System.out.println("List of players: ");
        players.forEach(System.out::println);
        System.out.println();

        // get a player by using its index in the list
        UnoPlayer getPlayerByIndex = manager.getPlayer(0);

        // swap player positions
        manager.swapPlayerPositions(playerJosiah, playerAI3);

        // output the list of players again
        System.out.println("List of players after swapping Player Josiah with Player AI 3: ");
        players.forEach(System.out::println);
        System.out.println();



    }
}
