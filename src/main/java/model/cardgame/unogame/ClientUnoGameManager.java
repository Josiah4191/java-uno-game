package model.cardgame.unogame;

import controller.GameAreaListener;
import model.cardgame.card.unocard.*;
import model.image.playerimage.PlayerImage;
import model.player.cardplayer.unoplayer.UnoPlayer;
import model.player.cardplayer.unoplayer.UnoPlayerAI;
import multiplayer.client.clientmessage.*;
import multiplayer.server.servermessage.GameEvent;

import java.util.List;

public class ClientUnoGameManager {

    private UnoGameState gameState = new UnoGameState();
    private GameAreaListener gameAreaListener;
    private GameActionListener gameActionListener;

    public void setGameState(UnoGameState gameState) {
        this.gameState = gameState;
    }

    public UnoGameState getGameState() {
        return gameState;
    }

    public void setGameAreaListener(GameAreaListener gameAreaListener) {
        this.gameAreaListener = gameAreaListener;
    }

    public void setGameActionListener(GameActionListener gameActionListener) {
        this.gameActionListener = gameActionListener;
    }

    public void setLocalPlayer(UnoPlayer player) {
        gameState.setLocalPlayer(player);
    }

    public UnoPlayer getLocalPlayer() {
        return gameState.getLocalPlayer();
    }

    public UnoPlayer getPlayer(int playerIndex) {
        return gameState.getPlayer(playerIndex);
    }

    public void setupGame(UnoEdition edition, UnoCardTheme theme, Difficulty difficulty, int numberOfOpponents) {
        SetupGameAction setupGameAction = new SetupGameAction(edition, theme, difficulty, numberOfOpponents);
        gameActionListener.sendActionMessage(setupGameAction);
    }

    public void joinGame(String name, PlayerImage playerImage) {
        JoinGameAction joinGameAction = new JoinGameAction(name, playerImage);
        gameActionListener.sendActionMessage(joinGameAction);
    }

    public void changeName(String newName) {
        ChangeNameAction changeNameAction = new ChangeNameAction(newName);
        gameActionListener.sendActionMessage(changeNameAction);
    }

    public void changePlayerImage(int playerIndex, PlayerImage playerImage) {
        ChangeImageAction changeImageAction = new ChangeImageAction(playerImage);
        gameActionListener.sendActionMessage(changeImageAction);
    }

    public void playCard(int cardIndex) {
        PlayCardAction playCardAction = new PlayCardAction(cardIndex);
        gameActionListener.sendActionMessage(playCardAction);
    }

    public void sayUno() {
        SayUnoAction sayUnoAction = new SayUnoAction(true);
        gameActionListener.sendActionMessage(sayUnoAction);
    }

    public void callUno(int playerIndex) {
        CallUnoAction callUnoAction = new CallUnoAction(playerIndex);
        gameActionListener.sendActionMessage(callUnoAction);
    }

    public void drawCard() {
        DrawCardAction drawCardAction = new DrawCardAction();
        gameActionListener.sendActionMessage(drawCardAction);
    }

    public void passTurn() {
        PassTurnAction passTurnAction = new PassTurnAction();
        gameActionListener.sendActionMessage(passTurnAction);
    }

    public void changeSuitColor(UnoSuit suit) {
        ChangeSuitAction changeSuitAction = new ChangeSuitAction(suit);
        gameActionListener.sendActionMessage(changeSuitAction);
    }

    public void updateGameView(GameEvent event) {
        gameAreaListener.updateGameView(event);
    }

    public void updateTheme(UnoCardTheme theme) {
        gameState.setTheme(theme);
    }

    public void updatePlayerCardNumberMap(int playerIndex, int cardNumber) {
        var map = gameState.getPlayerIndexToCardNumber();
        map.put(playerIndex, cardNumber);
    }

    public void updatePlayableCards(List<UnoCard> playableCards) {
        gameState.setPlayableCards(playableCards);
    }

    public void updatePlayerListAdd(List<UnoPlayer> players) {
        for (UnoPlayer player: players) {

            String name = player.getName();
            PlayerImage image = player.getImage();
            int playerID = player.getPlayerID();
            int numberOfCards = player.getTotalCardsRemaining();

            if (player.isAI()) {
                UnoPlayer aiPlayer = new UnoPlayerAI(gameState);
                aiPlayer.setImage(image);
                aiPlayer.setName(name);
                gameState.addPlayer(aiPlayer);
                int playerIndex = gameState.getPlayerIndex(aiPlayer);
                updatePlayerCardNumberMap(playerIndex, numberOfCards);
            } else {
                UnoPlayer localPlayer = new UnoPlayer(playerID);
                localPlayer.setImage(image);
                localPlayer.setName(name);
                gameState.addPlayer(localPlayer);
                int playerIndex = gameState.getPlayerIndex(localPlayer);
                updatePlayerCardNumberMap(playerIndex, numberOfCards);
            }
        }
    }

    public void updateLocalPlayerHandRemoveCard(int cardIndex) {
        gameState.getLocalPlayer().playCard(cardIndex);
    }

    public void updateLocalPlayerHandAddCard(List<UnoCard> cards) {
        for (UnoCard card: cards) {
            gameState.getLocalPlayer().addCard(card);
            updatePlayerCardNumberMap(
                    gameState.getLocalPlayerIndex(),
                    gameState.getLocalPlayer().getTotalCardsRemaining());
        }
    }

    public void updatePlayerName(int playerIndex, String name) {
        UnoPlayer player = gameState.getPlayer(playerIndex);
        player.setName(name);
    }

    public void updatePlayerImage(int playerIndex, PlayerImage playerImage) {
        UnoPlayer player = gameState.getPlayer(playerIndex);
        player.setImage(playerImage);
    }

    public void updateCurrentPlayerIndex(int playerIndex) {
        gameState.setCurrentPlayerIndex(playerIndex);
    }

    public void updateNextPlayerIndex(int nextPlayerIndex) {
        gameState.setNextPlayerIndex(nextPlayerIndex);
    }

    public void updateLastPlayedCard(UnoCard card) {
        gameState.setLastPlayedCard(card);
    }

    public void updatePlayDirection(PlayDirection playDirection) {
        gameState.setPlayDirection(playDirection);
    }

    public void updateEdition(UnoEdition edition) {
        gameState.setEdition(edition);
    }

    public void updateSayUno(int playerIndex, boolean sayUno) {
        UnoPlayer player = gameState.getPlayer(playerIndex);

        if (player.getPlayerID() == getLocalPlayer().getPlayerID()) {
            getLocalPlayer().sayUno(sayUno);
        }
        gameState.getPlayer(playerIndex).sayUno(sayUno);
    }

    public void updateCurrentSuit(UnoSuit currentSuit) {
        gameState.setCurrentSuit(currentSuit);
    }

    public void announceWinner(int playerIndex) {
        System.out.println("Announce Winner Event Received from Client. Winner is: " + gameState.getPlayer(playerIndex));
    }

    public void updateDifficulty(Difficulty difficulty) {
        gameState.setDifficulty(difficulty);
    }

    public void updateStackPenalty(int stackPenalty) {
        gameState.setStackPenalty(stackPenalty);
    }


}
