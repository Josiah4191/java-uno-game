package games.players.cardplayers;

import games.cardgames.cards.Pile;
import games.players.Player;

import java.util.Collections;
import java.util.List;

public abstract class CardPlayer<P extends Pile<C>, C> extends Player {

    private P playerHand;

    public CardPlayer(P playerHand) {
        this.playerHand = playerHand;
    }

    public void addCard(C card) {
        playerHand.addCard(card);
    }

    // this is public right now for testing purposes, but it needs to be protected so that other players or computer AI cannot access this list
    // if other players or the AI could see this list, that would be cheating
    public List<C> getPlayerHand() {
        return Collections.unmodifiableList(playerHand.getCardPile());
    }

    public C playCard(int index) {
        return playerHand.drawCard(index);
    }

    public C playCard() {
        if (playerHand.isEmpty()) {
            return null;
        }
        return playerHand.drawCard(0);
    }

    public int getTotalCardsRemaining() {
        return playerHand.size();
    }

    public boolean isEmpty() {
        return playerHand.isEmpty();
    }
}
