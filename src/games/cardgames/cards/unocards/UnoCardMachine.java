package games.cardgames.cards.unocards;

import games.players.cardplayers.unoplayers.UnoPlayer;

import java.util.Collections;
import java.util.List;

public class UnoCardMachine {
/*
Last edited: Josiah Stoltzfus
Date: 3/7/2025
    - Initial version - First time editing. Future edits and comments will be noted here. Please
    include your name and date.

Author: Josiah Stoltzfus
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
    The get methods for drawPile, discardPile, and deck are read-only. This way, we can't alter the lists.
    The UnoDrawPile and UnoDiscardPile are responsible for making changes to their lists of cards.
*/

    private UnoDrawPile drawPile = new UnoDrawPile();
    private UnoDiscardPile discardPile = new UnoDiscardPile();
    private UnoDeck deck;

    public UnoCardMachine(UnoDeck deck) {
        this.deck = deck;
        transferDeckToDrawPile();
    }

    public UnoCard drawCardFromDrawPile() {
        if (drawPile.isEmpty()) {
            transferDiscardPileToDrawPile();
            shuffleDrawPile();
        }
        UnoCard card = drawPile.drawCard();
        addCardToDiscardPile(card);
        return drawPile.drawCard();
    }

    public UnoCard getLastPlayedCard() {
        return discardPile.getLastPlayedCard();
    }

    private void shuffleDrawPile() {
        drawPile.shuffle();
    }

    private void transferDeckToDrawPile() {
        deck.transferDeckToDrawPile(drawPile);
    }

    public void addCardToDiscardPile(UnoCard card) {
        discardPile.addCard(card);
    }

    private void transferDiscardPileToDrawPile() {
        discardPile.transferCardsToDrawPile(drawPile);
    }

    public UnoEdition getEdition() {
        return deck.getEdition();
    }

    public List<UnoCard> getDrawPile() {
        return Collections.unmodifiableList(drawPile.getCardPile());
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
