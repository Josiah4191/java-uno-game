package games.cardgames.uno.unocards;

import games.cardgames.cards.Pile;

/*
    1. This UnoDiscardPile class extends the Pile class and is responsible for managing the discard pile.
    2. This class owns the discard pile.
    3. The transferCardsToDrawPile receives an UnoDrawPile and transfers the cards from the discard pile
       to the draw pile.
    4. The addCard method receives an UnoCard and adds it to this discard pile.
    5. The getLastPlayedCard() method will return the last card in the pile, which represents the top card on the discard pile
        - The players in the game will need this card so they can play the game
 */

public class UnoDiscardPile extends Pile<UnoCard> {

    public UnoCard getLastPlayedCard() {
        int index = cardPile.size() - 1;
        return cardPile.get(index);
    }

    protected void transferCardsToDrawPile(UnoDrawPile drawPile) {
        drawPile.fill(cardPile);
        cardPile.clear();
    }
}
