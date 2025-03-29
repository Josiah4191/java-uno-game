package model.cardgame.card.unocard;

import model.player.cardplayer.unoplayer.UnoPlayer;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/*
Team Members: Steve Wareham, Charles Davidson, Josiah Stoltzfus
Date: 3/7/2025
------------------------------------------------------------------------------

This class is responsible for managing the flow of cards between the deck, draw pile, and discard pile.
The constructor is passed an UnoDeck object.

This class combines the UnoDrawPile, UnoDiscardPile, and UnoDeck so that they can shift cards between
each list of cards.

Variables:
    - UnoDrawPile class
        - Owns the draw pile list of cards
        - Has methods for managing the draw pile
    - UnoDiscardPile class
        - Owns the discard pile list of cards
        - Has methods for managing the discard pile
    - UnoDeck class
        - Creates the initial deck of Uno cards
        - Owns the deck of Uno cards
Methods:
    - Contains the third-level of methods in a series of cascaded method calls.
    - Each method should be self-explanatory and performs the action that its name suggests.
    - For implementation details, refer to Pile, UnoDrawPile, and UnoDiscardPile.
NOTE:
    The get methods for drawPile, discardPile, and deck are read-only so lists can't be altered.
    The UnoDrawPile and UnoDiscardPile are responsible for making changes to their list of cards.
*/

public class UnoCardMachine implements Serializable {
    private UnoDrawPile drawPile;
    private UnoDiscardPile discardPile;
    private UnoDeck deck;

    public void createMachine(UnoEdition edition) {
        this.drawPile = new UnoDrawPile();
        this.discardPile = new UnoDiscardPile();
        this.deck = new UnoDeck(edition);
        transferDeckToDrawPile();
    }

    public UnoCard drawCardFromDrawPile() {
        if (drawPile.isEmpty()) {
            transferDiscardPileToDrawPile();
            shuffleDrawPile();
            UnoCard card = drawCardFromDrawPile();
            addCardToDiscardPile(card);
        }
        return drawPile.drawCard();
    }

    private void shuffleDrawPile() {
        drawPile.shuffle();
    }

    private void transferDeckToDrawPile() {
        deck.transferUnoDeckToDrawPile(drawPile);
    }

    public void addCardToDiscardPile(UnoCard card) {
        discardPile.addCard(card);
    }

    private void transferDiscardPileToDrawPile() {
        discardPile.transferDiscardPileToDrawPile(drawPile);
    }

    public List<UnoCard> getDrawPile() {
        return Collections.unmodifiableList(drawPile.getCardPile());
    }

    public void removeCardFromDrawPile(UnoCard card) {
        drawPile.getCardPile().remove(card);
    }

    public List<UnoCard> getDiscardPile() {
        return Collections.unmodifiableList(discardPile.getCardPile());
    }

    public List<UnoCard> getDeck() {
        return Collections.unmodifiableList(deck.getDeck());
    }

    public void dealCards(int numberOfCards, List<UnoPlayer> players) {
        for (var player : players) {
            for (int i = 0; i < numberOfCards; i++) {
                player.addCard(drawPile.drawCard());
            }
        }
    }
}
