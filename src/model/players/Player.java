package model.players;

import model.cards.Pile;

import java.util.Collections;
import java.util.List;

public abstract class Player<C> {

    private String name;
    private Pile<C> playerHand;

    public Player(Pile<C> playerHand) {
        this.playerHand = playerHand;
    }

    public void addCard(C card) {
        playerHand.addCard(card);
    }

    public List<C> getPlayerHand() {
        return Collections.unmodifiableList(playerHand.getCardPile());
    }

    public C playCard(int index) {
        return playerHand.drawCard(index);
    }

    public boolean isEmpty() {
        return playerHand.isEmpty();
    }
}
