package test;

import games.Difficulty;
import games.cardgames.cards.unocards.UnoCardClassicImages;
import games.cardgames.cards.unocards.UnoCardImageManager;
import games.cardgames.cards.unocards.UnoCardTheme;
import games.cardgames.cards.unocards.UnoEdition;
import games.cardgames.unogame.*;
import games.players.cardplayers.unoplayers.UnoPlayer;
import games.players.cardplayers.unoplayers.UnoPlayerAI;
import javafx.scene.image.Image;

import java.net.URL;
import java.util.List;

public class Test {

    public static void main(String[] args) {
        // create game manager
        UnoGameManager manager = new UnoGameManager(UnoEdition.CLASSIC, Difficulty.EASY);
        UnoGameState gameState = manager.getGameState();

        // get the deck
        var deck = gameState.getDeck();
        System.out.println("Deck:");
        deck.forEach(System.out::println);
        System.out.println();

        // get the draw pile
        var drawPile = gameState.getDrawPile();
        System.out.println("Draw pile: " + drawPile.size() + " cards");
        drawPile.forEach(System.out::println);
        System.out.println();

        // get the discard pile
        var discardPile = gameState.getDiscardPile();
        System.out.println("Discard pile:");
        discardPile.forEach(System.out::println);
        System.out.println();

        // get the rules
        UnoRules rules = gameState.getRules();

        // set the rules
        gameState.setRules(new UnoClassicRules());

        // get the difficulty
        Difficulty difficulty = gameState.getDifficulty();

        // set the difficulty
        gameState.setDifficulty(Difficulty.EASY);

        // get the card theme
        UnoCardTheme theme = gameState.getTheme();
        System.out.print("Theme: ");
        System.out.println(theme);
        System.out.println();

        // set the card theme
        gameState.setTheme(UnoCardTheme.CLASSIC);

        // get the uno game edition
        UnoEdition edition = gameState.getEdition();
        System.out.print("Edition: ");
        System.out.println(edition);
        System.out.println();

        // get the direction of play
        PlayDirection direction = gameState.getDirection();
        System.out.print("Direction: ");
        System.out.println(direction);
        System.out.println();

        // reverse the direction of play
        manager.reversePlayDirection();

        // get the new direction of play
        PlayDirection newDirection = gameState.getDirection();
        System.out.print("New direction after using the reverseDirection method: ");
        System.out.println(newDirection);
        System.out.println();

        // reverse the direction back to forward
        manager.reversePlayDirection();

        // get the direction of play again
        PlayDirection secondReverseDirection = gameState.getDirection();
        System.out.print("Direction after reversing a second time: ");
        System.out.println(secondReverseDirection);
        System.out.println();

        // create a player
        UnoPlayer player1 = new UnoPlayer();
        UnoPlayer player2 = new UnoPlayer();
        UnoPlayer playerAI1 = new UnoPlayerAI(manager.getGameState());
        UnoPlayer playerAI2 = new UnoPlayerAI(manager.getGameState());
        UnoPlayer playerAI3 = new UnoPlayerAI(manager.getGameState());
        UnoPlayer playerAI4 = new UnoPlayerAI(manager.getGameState());
        UnoPlayer playerAI5 = new UnoPlayerAI(manager.getGameState());
        UnoPlayer playerAI6 = new UnoPlayerAI(manager.getGameState());

        // set some names (testing purposes)
        player1.setName("Josiah");
        player2.setName("Aaron");
        playerAI1.setName("AI 1");
        playerAI2.setName("AI 2");
        playerAI3.setName("AI 3");
        playerAI4.setName("AI 4");
        playerAI5.setName("AI 5");
        playerAI6.setName("AI 6");

        // add single player to game ( 1 player)
        manager.addPlayer(player1);

        // add list of players to game ( 7 players)
        manager.addPlayers(List.of(player2, playerAI1, playerAI2, playerAI3, playerAI4, playerAI5, playerAI6));

        // get a single player
        UnoPlayer playerIndex0 = gameState.getPlayer(0);
        System.out.print("Player index 0: ");
        System.out.println(playerIndex0);
        System.out.println();

        // get a list of the players
        var playerList = gameState.getPlayers();
        System.out.println("List of players: ");
        playerList.forEach(System.out::println);
        System.out.println();

        // get the number of players (should be 8 players)
        int numberOfPlayers = gameState.getPlayers().size();
        System.out.print("Number of players: ");
        System.out.println(numberOfPlayers);
        System.out.println();

        // deal cards to players
        manager.dealCards(7, playerList);

        // get the current player
        UnoPlayer currentPlayer = manager.getCurrentPlayer();
        System.out.print("Current player: ");
        System.out.println(currentPlayer);
        System.out.println();

        // get the current player index
        int currentPlayerIndex = gameState.getCurrentPlayerIndex();
        System.out.print("Current player index: ");
        System.out.println(currentPlayerIndex);
        System.out.println();

        // move to the next player
        manager.moveToNextPlayer();

        // get the current player after moving
        UnoPlayer nextCurrentPlayer = manager.getCurrentPlayer();
        System.out.print("Current player after moving to the next player: ");
        System.out.println(nextCurrentPlayer);
        System.out.println();

        // get the player who comes after the current player, but don't move to them yet
        UnoPlayer nextPlayer = manager.getNextPlayer();
        System.out.print("View who the next player is, but don't move yet: ");
        System.out.println(nextPlayer);
        System.out.println();

        // move and skip the next player
        manager.skipNextPlayer();

        // get the current player after skipping
        UnoPlayer nextPlayerAfterSKip = manager.getCurrentPlayer();
        System.out.print("Current player after skipping the next player: ");
        System.out.println(nextPlayerAfterSKip);
        System.out.println();


        // reverse the direction
        manager.reversePlayDirection();

        // get the current player
        UnoPlayer currentPlayer2 = manager.getCurrentPlayer();
        System.out.println("Current player: " + currentPlayer2);
        System.out.println();

        // get the next player moving in reverse
        UnoPlayer nextPlayer2 = manager.getNextPlayer();
        System.out.println("View the next player moving in reverse, but don't move yet: " + nextPlayer2);
        System.out.println();

        // move to the next player in reverse
        manager.moveToNextPlayer();

        // get the new current player after moving
        UnoPlayer playerAfterMovingReverse = manager.getCurrentPlayer();
        System.out.println("Current player after moving in reverse: " + playerAfterMovingReverse);
        System.out.println();

        // view the next player again
        UnoPlayer nextPlayer3 = manager.getNextPlayer();
        System.out.println("View the next player again moving in reverse, but don't move yet: " + nextPlayer3);
        System.out.println();

        // skip the next player
        manager.skipNextPlayer();

        // get the current player after skipping
        UnoPlayer currentPlayerAfterSkipping = manager.getCurrentPlayer();
        System.out.println("Current player after skipping: " + currentPlayerAfterSkipping);
        System.out.println();






































        /*
        ideas: player class
            - give player name field
            - change addPlayer to createPlayer
                - pass the name to constructor for creating player
            - create new method createAIPlayer
                - no parameter constructor that picks name from a list of names
            - instead of a hashmap, just have a list of players.
            - change the get playerNames and getPlayer methods to work with list
                - or still keep hashmap and use player.name as keys
        ideas: UnoPlayer class
            - add a boolean sayUno
            - will need a setSayUno method to reset it to false as well
            - add method callUno and sayUno
            - sayUno method will make boolean sayUno true
            - callUno method
                - this method accepts another player as the parameter
                - this method uses the validator/rules class to check whether the other player:
                    1.) has only 1 card remaining
                    2.) their sayUno variable is false
                        - if both of those things are true, then add cards to their hand of cards
                        - this can also return a boolean for checking whether we should do some kind of
                            visual action in the UI. like return true if we added cards, and then we can
                            output some kind of message in the game. if it is a false accusation, maybe we
                            can let them know it is a false accusation.
                        - another solution is that we can have every player's sayUno status displayed on the screen
                            so everybody can see
                        - if both of them are not true, then don't do anything
         */
    }
}
