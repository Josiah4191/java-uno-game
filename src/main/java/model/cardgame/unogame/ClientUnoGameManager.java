package model.cardgame.unogame;

import model.cardgame.card.unocard.*;
import model.image.playerimage.PlayerImage;
import model.player.cardplayer.unoplayer.UnoPlayer;
import model.player.cardplayer.unoplayer.UnoPlayerAI;
import multiplayer.client.clientmessage.*;

import java.util.List;

public class ClientUnoGameManager {

    private UnoGameState gameState = new UnoGameState();

    public void setGameState(UnoGameState gameState) {
        this.gameState = gameState;
    }

    public UnoGameState getGameState() {
        return gameState;
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

    public GameAction setupGame(UnoEdition edition, UnoCardTheme theme, Difficulty difficulty, int numberOfOpponents) {
        return new SetupGameAction(edition, theme, difficulty, numberOfOpponents);
    }

    public GameAction joinGame(String name, PlayerImage playerImage) {
        return new JoinGameAction(name, playerImage);
    }

    public GameAction changeName(String newName) {
        return new ChangeNameAction(newName);
    }

    public GameAction changePlayerImage(int playerIndex, PlayerImage playerImage) {
        return new ChangeImageAction(playerImage);
    }

    public GameAction playCard(int cardIndex) {
        return new PlayCardAction(cardIndex);
    }

    public GameAction sayUno() {
        return new SayUnoAction(true);
    }

    public GameAction callUno(int playerIndex) {
        return new CallUnoAction(playerIndex);
    }

    public GameAction drawCard() {
        return new DrawCardAction();
    }

    public GameAction passTurn() {
        return new PassTurnAction(true);
    }

    public void updateTheme(UnoCardTheme theme) {
        gameState.setTheme(theme);
    }

    public void updatePlayerCardNumberMap(int playerIndex, int cardNumber) {
        var map = gameState.getPlayerIndexToCardNumber();
        map.put(playerIndex, cardNumber);
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

    public void updateLocalPlayerLastDrawnCard(int playerIndex, UnoCard drawnCard) {
        UnoPlayer player = gameState.getPlayer(playerIndex);
        player.setLastDrawCard(drawnCard);
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
        gameState.getPlayer(playerIndex).sayUno(sayUno);
    }

    public void updateCurrentSuit(UnoSuit currentSuit) {
        gameState.setCurrentSuit(currentSuit);
    }

    public void announceWinner(int playerIndex) {

    }

    public void updateDifficulty(Difficulty difficulty) {
        gameState.setDifficulty(difficulty);
    }

    public void updateStackPenalty(int stackPenalty) {
        gameState.setStackPenalty(stackPenalty);
    }


}
