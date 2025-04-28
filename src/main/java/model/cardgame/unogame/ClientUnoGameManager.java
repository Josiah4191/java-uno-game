package model.cardgame.unogame;

import controller.GameAreaListener;
import model.cardgame.card.unocard.*;
import model.image.playerimage.PlayerImage;
import model.player.cardplayer.unoplayer.UnoPlayer;
import model.player.cardplayer.unoplayer.UnoPlayerAI;
import multiplayer.client.clientmessage.*;
import multiplayer.server.servermessage.GameEvent;

import java.util.List;

/*
    This is the client side game manager. It is a lightweight game manager. It has methods for sending JSON messages to the
    server, and it has methods for updating its game state to reflect changes made to the game.

    Instance variables:
        - UnoGameState
            - The UnoGameState holds all the information about the game
        - GameAreaListener
            - The GameAreaListener is a reference to the controller. The GameAreaController listens to certain events that occur
             in this game manager will update the game view when certain actions occur, because this game manager will call the
             updateGameView() method
        - GameActionListener
            - The GameActionListener is a reference to the client. The client object is listens to events that occur in this game manager,
            like when a message needs to be sent to the server (e.g., playing a card, drawing a card), the game manager can use the Client
            method to send a message to the server here.
 */

public class ClientUnoGameManager {

    private UnoGameState gameState = new UnoGameState();
    private GameAreaListener gameAreaListener;
    private GameActionListener gameActionListener;

    // Set the game state
    public void setGameState(UnoGameState gameState) {
        this.gameState = gameState;
    }

    // Get the game state
    public UnoGameState getGameState() {
        return gameState;
    }

    // Set the game area listener
    public void setGameAreaListener(GameAreaListener gameAreaListener) {
        this.gameAreaListener = gameAreaListener;
    }

    // Set the game action listener
    public void setGameActionListener(GameActionListener gameActionListener) {
        this.gameActionListener = gameActionListener;
    }

    // Set the local player
    public void setLocalPlayer(UnoPlayer player) {
        gameState.setLocalPlayer(player);
    }

    // Get the local player
    public UnoPlayer getLocalPlayer() {
        return gameState.getLocalPlayer();
    }

    // Get the player by their index
    public UnoPlayer getPlayer(int playerIndex) {
        return gameState.getPlayer(playerIndex);
    }

    // Send a SetupGameAction message to the server to set the game up
    public void setupGame(UnoEdition edition, UnoCardTheme theme, Difficulty difficulty, int numberOfOpponents) {
        SetupGameAction setupGameAction = new SetupGameAction(edition, theme, difficulty, numberOfOpponents);
        gameActionListener.sendActionMessage(setupGameAction);
    }

    // Send a JoinGameAction message to the server for the local player (client) to join the game
    public void joinGame(String name, PlayerImage playerImage) {
        JoinGameAction joinGameAction = new JoinGameAction(name, playerImage);
        gameActionListener.sendActionMessage(joinGameAction);
    }

    // Send a ChangeNameAction message to the server if the player changes their name
    public void changeName(String newName) {
        ChangeNameAction changeNameAction = new ChangeNameAction(newName);
        gameActionListener.sendActionMessage(changeNameAction);
    }

    // Send a ChangeImageAction message to the server if the player changes their image
    public void changePlayerImage(int playerIndex, PlayerImage playerImage) {
        ChangeImageAction changeImageAction = new ChangeImageAction(playerImage);
        gameActionListener.sendActionMessage(changeImageAction);
    }

    // Send a PlayCardAction message to the server when a player chooses a card to play
    public void playCard(int cardIndex) {
        PlayCardAction playCardAction = new PlayCardAction(cardIndex);
        gameActionListener.sendActionMessage(playCardAction);
    }

    // Send a SayUnoAction message to the server when a player says UNO
    public void sayUno() {
        SayUnoAction sayUnoAction = new SayUnoAction(true);
        gameActionListener.sendActionMessage(sayUnoAction);
    }

    // Send a CallUnoAction message to the server when a player calls UNO on another player
    public void callUno(int playerIndex) {
        CallUnoAction callUnoAction = new CallUnoAction(playerIndex);
        gameActionListener.sendActionMessage(callUnoAction);
    }

    // Send a DrawCardAction message to the server when a player chooses to draw a card
    public void drawCard() {
        DrawCardAction drawCardAction = new DrawCardAction();
        gameActionListener.sendActionMessage(drawCardAction);
    }

    // Gets the number of cards remaining when passed a player index
    public int getNumberOfCardsRemaining(int playerIndex) {
        return gameState.getPlayerIndexToCardNumber().get(playerIndex);
    }

    // Send a PassTurnAction message to the server when the player presses the pass button
    public void passTurn() {
        PassTurnAction passTurnAction = new PassTurnAction();
        gameActionListener.sendActionMessage(passTurnAction);
    }

    // Send a ChangeSuitAction message to the server when the player selects a new suit color after playing a wild card
    public void changeSuitColor(UnoSuit suit) {
        ChangeSuitAction changeSuitAction = new ChangeSuitAction(suit);
        gameActionListener.sendActionMessage(changeSuitAction);
    }

    // This method updates the game area view by using the GameAreaListener (controller). It is used by the Client.
    public void updateGameView(GameEvent event) {
        gameAreaListener.updateGameView(event);
    }

    // This method updates the UNO card theme
    public void updateTheme(UnoCardTheme theme) {
        gameState.setTheme(theme);
    }

    // This method updates the number of cards that a player has when passed a player index and card number
    public void updatePlayerCardNumberMap(int playerIndex, int cardNumber) {
        var map = gameState.getPlayerIndexToCardNumber();
        map.put(playerIndex, cardNumber);
    }

    // This method updates the list of playable cards for the current player
    public void updatePlayableCards(List<UnoCard> playableCards) {
        gameState.setPlayableCards(playableCards);
    }

    // This method adds all the players to the list of players in the game. It receives a list of UnoPlayer
    /*
        When a list of player objects is passed to this method, new player objects are created and added to the client's
        list of players to match the server's list of players.

        The new player objects that are created for the client side are more lightweight, and the client does not know
        all the cards that each player has. The new player objects will have the same name, image, playerID, and the total
        cards remaining for that player. A map stores the total cards remaining for each player and is mapped to the player's index
        within the list of players. This way, the client can know many cards each player has, but doesn't know what the cards are.
     */
    public void updatePlayerListAdd(List<UnoPlayer> players) {
        // Loop through list of UnoPlayer
        for (UnoPlayer player : players) {

            String name = player.getName(); // get the name of the player
            PlayerImage image = player.getImage(); // get the image of the player
            int playerID = player.getPlayerID(); // get the player ID
            int numberOfCards = player.getTotalCardsRemaining(); // get the number of cards the player has

            if (player.isAI()) { // check if the player is AI
                UnoPlayer aiPlayer = new UnoPlayerAI(gameState); // create an AI player
                aiPlayer.setImage(image); // set the image
                aiPlayer.setName(name); // set the name
                gameState.addPlayer(aiPlayer); // add the AI player to the list of players
                int playerIndex = gameState.getPlayerIndex(aiPlayer); // get the index of the player in the list
                updatePlayerCardNumberMap(playerIndex, numberOfCards); // add the player index and number of cards to the map
            } else {
                UnoPlayer localPlayer = new UnoPlayer(playerID); // create a human player
                localPlayer.setImage(image); // set the image
                localPlayer.setName(name); // set the name
                gameState.addPlayer(localPlayer); // add the player to the list of players
                int playerIndex = gameState.getPlayerIndex(localPlayer); // get the index of the player in the list
                updatePlayerCardNumberMap(playerIndex, numberOfCards); // add the player index and number of cards to the map
            }
        }
    }

    // This method removes the card from the player's hand that corresponds to the given index
    public void updateLocalPlayerHandRemoveCard(int cardIndex) {
        gameState.getLocalPlayer().playCard(cardIndex);
    }

    // This method updates the list of cards in the local player's hand of cards
    public void updateLocalPlayerHandAddCard(List<UnoCard> cards) {
        // Loop through the list of cards
        for (UnoCard card : cards) {
            gameState.getLocalPlayer().addCard(card); // add the card to the player's hand of cards
            // Update the number of cards for the local player index
            updatePlayerCardNumberMap(
                    gameState.getLocalPlayerIndex(),
                    gameState.getLocalPlayer().getTotalCardsRemaining());
        }
    }

    // This method updates a player's name
    public void updatePlayerName(int playerIndex, String name) {
        UnoPlayer player = gameState.getPlayer(playerIndex); // get the player using player index
        player.setName(name); // set the name of the player
    }

    // This method updates a player's image
    public void updatePlayerImage(int playerIndex, PlayerImage playerImage) {
        UnoPlayer player = gameState.getPlayer(playerIndex); // get the player using player index
        player.setImage(playerImage); // set the image of the player
    }

    // This method updates the current player index
    public void updateCurrentPlayerIndex(int playerIndex) {
        gameState.setCurrentPlayerIndex(playerIndex);
    }

    // This method updates the next player index (not currently being used)
    public void updateNextPlayerIndex(int nextPlayerIndex) {
        gameState.setNextPlayerIndex(nextPlayerIndex);
    }

    // This method updates the last played card
    public void updateLastPlayedCard(UnoCard card) {
        gameState.setLastPlayedCard(card);
    }

    // This method updates the play direction
    public void updatePlayDirection(PlayDirection playDirection) {
        gameState.setPlayDirection(playDirection);
    }

    // This method updates the game edition
    public void updateEdition(UnoEdition edition) {
        gameState.setEdition(edition);
    }

    // This method updates the status of sayUno for the specified player
    public void updateSayUno(int playerIndex, boolean sayUno) {
        UnoPlayer player = gameState.getPlayer(playerIndex); // get the player using player index

        // Check if the player is the local player
        if (player.getPlayerID() == getLocalPlayer().getPlayerID()) {
            getLocalPlayer().sayUno(sayUno); // set the sayUno status on the local player
        }
        gameState.getPlayer(playerIndex).sayUno(sayUno); // set the sayUno status on the player
    }

    // This method updates the current suit
    public void updateCurrentSuit(UnoSuit currentSuit) {
        gameState.setCurrentSuit(currentSuit);
    }

    // This method announces the winner. Right now, it is used only being used for testing.
    public void announceWinner(int playerIndex) {
        System.out.println("Announce Winner Event Received from Client. Winner is: " + gameState.getPlayer(playerIndex));
    }

    // This method updates the difficulty
    public void updateDifficulty(Difficulty difficulty) {
        gameState.setDifficulty(difficulty);
    }

    // This method updates the stack penalty
    public void updateStackPenalty(int stackPenalty) {
        gameState.setStackPenalty(stackPenalty);
    }


}
