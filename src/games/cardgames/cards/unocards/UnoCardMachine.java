package games.cardgames.cards.unocards;

import games.players.cardplayers.unoplayers.UnoPlayer;

import java.util.Collections;
import java.util.List;

public class UnoCardMachine {
    /*
        1. This class is responsible for controlling the flow of cards between the deck, draw pile, and discard pile.
        2. When we create this UnoCardMachine, we must pass it a UnoDeck object, which holds a deck of Uno cards.
        3. This UnoCardMachine class combines the UnoDrawPile, UnoDiscardPile, and UnoDeck so that they can shift cards between each list of cards.
            - UnoDrawPile class
                - Owns the draw pile list of cards
                - Has methods for managing the draw pile
            - UnoDiscardPile class
                - Owns the discard pile list of cards
                - Has methods for managing the discard pile
            - UnoDeck class
                - Creates the initial deck of Uno cards
                - Owns the deck of Uno cards
         4. This UnoCardMachine class creates the drawPile, discardPile, and is passed a deck. Then we use these methods, which will
            call the methods of UnoDrawPile, UnoDiscardPile, and UnoDeck.

         Note:
             The get methods in this class will get the list of cards in the drawPile, discardPile, and deck, but they are read-only. This way,
             we can't alter the lists. This is for encapsulation. The UnoDrawPile and UnoDiscardPile are responsible for making changes to their
             own lists of cards.

             The other methods are a higher level than UnoDrawPile and UnoDiscardPile, and they should be self-explanatory.
             To see under the hood, refer to Pile, UnoDrawPile, and UnoDiscardPile.
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
