package model.cardgame.card.unocard;

import model.player.cardplayer.unoplayer.UnoPlayer;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/*
This class is responsible for managing the flow of cards between the deck, draw pile, and discard pile.

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

    // Creates an UnoDeck, DrawPile, and DiscardPile based on the given UnoEdition
    public void createMachine(UnoEdition edition) {
        this.drawPile = new UnoDrawPile(); // create draw pile
        this.discardPile = new UnoDiscardPile(); // create discard pile
        this.deck = new UnoDeck(edition); // create deck
        transferDeckToDrawPile(); // transfer the UnoCards from the deck to the draw pile
    }

    // Draws a card from the draw pile
    public UnoCard drawCardFromDrawPile() {
        if (drawPile.isEmpty()) { // check if the draw pile is empty
            transferDiscardPileToDrawPile(); // if draw pile is empty, transfer all the cards from discard to draw pile.
            shuffleDrawPile(); // shuffle the cards
            UnoCard card = drawCardFromDrawPile(); // call this method again after cards were transferred
            addCardToDiscardPile(card); // add the drawn card to the discard pile
        }
        return drawPile.drawCard(); // return an UnoCard from the draw pile
    }

    // Shuffles the draw pile
    private void shuffleDrawPile() {
        drawPile.shuffle();
    }

    // Transfers cards from the deck to the draw pile
    private void transferDeckToDrawPile() {
        deck.transferUnoDeckToDrawPile(drawPile);
    }

    // Adds a card to the discard pile
    public void addCardToDiscardPile(UnoCard card) {
        discardPile.addCard(card);
    }

    // Transfers the cards from the discard pile to the draw pile
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

    // Deals a given number of cards to a list of players by drawing from the draw pile
    public void dealCards(int numberOfCards, List<UnoPlayer> players) {
        for (var player : players) {
            for (int i = 0; i < numberOfCards; i++) {
                player.addCard(drawPile.drawCard());
            }
        }
    }
}
