package games.cardgames.unogame;

import games.Difficulty;
import games.cardgames.CardGameManager;
import games.cardgames.cards.unocards.*;
import games.players.cardplayers.unoplayers.UnoPlayer;

import java.util.List;

public class UnoGameManager extends CardGameManager {

    private UnoGameState gameState;
    private UnoCardImageManager cardImageManager;

    public UnoGameManager(UnoEdition edition, UnoCardTheme theme, Difficulty difficulty) {
        cardImageManager = new UnoCardImageManager(theme);
        gameState = new UnoGameState(edition, difficulty);
    }

    public UnoGameState getGameState() {
        return gameState;
    }

    public String getImage(UnoCard card) {
        return cardImageManager.getImage(card);
    }

    public UnoCardTheme getTheme() {
        return cardImageManager.getTheme();
    }

    public void setTheme(UnoCardTheme theme) {
        cardImageManager.setTheme(theme);
    }

    public void setDifficulty(Difficulty difficulty) {
        gameState.setDifficulty(difficulty);
    }

    public Difficulty getDifficulty() {
        return gameState.getDifficulty();
    }

    public void addPlayer(String name, UnoPlayer player) {
        gameState.addPlayer(name, player);
    }

    public UnoPlayer getPlayer(String name) {
        return gameState.getPlayer(name);
    }

    public List<String> getPlayerNames() {
        return gameState.getPlayerNames();
    }

    public List<UnoPlayer> getPlayers() {
        return gameState.getPlayers();
    }

    public UnoCard getLastPlayedCard() {
        return gameState.getLastPlayedCard();
    }

    public void shuffleDrawPile() {
        gameState.shuffleDrawPile();
    }

    public void shuffleDeck() {
        gameState.shuffleDeck();
    }

    public void dealCards(int numberOfCards, List<UnoPlayer> players) {
        gameState.dealCards(numberOfCards, players);
    }

    public UnoCard drawCardFromDrawPile() {
        return gameState.drawCardFromDrawPile();
    }

    public void transferDiscardPileToDrawPile() {
        gameState.transferDiscardPileToDrawPile();
    }

    public UnoEdition getEdition() {
        return gameState.getEdition();
    }

    public List<UnoCard> getDrawPile() {
        return gameState.getDrawPile();
    }

    public List<UnoCard> getDiscardPile() {
        return gameState.getDiscardPile();
    }

    public List<UnoCard> getDeck() {
        return gameState.getDeck();
    }

    public UnoCard playCard(String name, int index) {
        return gameState.playCard(name, index);
    }
}
