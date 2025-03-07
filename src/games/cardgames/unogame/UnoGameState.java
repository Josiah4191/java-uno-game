package games.cardgames.unogame;

import games.Difficulty;
import games.cardgames.CardGameState;
import games.cardgames.cards.unocards.*;
import games.players.cardplayers.unoplayers.UnoPlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UnoGameState extends CardGameState {

    private UnoCardMachine machine;
    private HashMap<String, UnoPlayer> players = new HashMap<>();
    private Difficulty difficulty;

    public UnoGameState(UnoEdition edition, Difficulty difficulty) {
        machine = new UnoCardMachine(new UnoDeck(edition));
        this.difficulty = difficulty;
    }

    public void addPlayer(String name, UnoPlayer player) {
        players.put(name, player);
    }

    public UnoPlayer getPlayer(String name) {
        return players.get(name);
    }

    public List<String> getPlayerNames() {
        return new ArrayList<>(players.keySet());
    }

    public List<UnoPlayer> getPlayers() {
        return new ArrayList<>(players.values());
    }

    public UnoCard getLastPlayedCard() {
        return machine.getLastPlayedCard();
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void dealCards(int numberOfCards, List<UnoPlayer> players) {
        machine.dealCards(numberOfCards, players);
    }

    public UnoCard drawCardFromDrawPile() {
        return machine.drawCardFromDrawPile();
    }

    public UnoEdition getEdition() {
        return machine.getEdition();
    }

    public List<UnoCard> getDrawPile() {
        return machine.getDrawPile();
    }

    public List<UnoCard> getDiscardPile() {
        return machine.getDiscardPile();
    }

    public List<UnoCard> getDeck() {
        return machine.getDeck();
    }

    public UnoCard playCard(String name, int index) {
        var player = players.get(name);
        UnoCard card = player.playCard(index);
        machine.addCardToDiscardPile(card);
        return card;
    }
}
