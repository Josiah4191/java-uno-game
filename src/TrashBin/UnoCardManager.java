package TrashBin;


import games.cardgames.cards.Pile;
import games.cardgames.cards.unocards.UnoCard;

import java.util.HashMap;

public class UnoCardManager extends CardManager<Pile<UnoCard>, UnoCard> {

    // this class keeps track of where cards are at and makes sure duplicates aren't allowed in other piles
    // this class also makes sure that cards are removed from piles

    private HashMap<UnoCard, Pile<UnoCard>> cardLocations = new HashMap<>();

    /*
    card1 : Pile
    card2 : Pile
    card3 : Pile
     */

    // check if the move is legal
    public boolean validate(UnoCard card, Pile<UnoCard> destinationPile) {
        var cards = destinationPile.getCardPile();
        var currentPileLocation = cardLocations.get(card);

        /*

        Get the current pile that the card is in.

        If the card already exists in another pile: then what? we would have to remove it from that pile

        If card1 exists in a players hand, and we try to add it to the discard pile, then we have to remove the card
        from the players hand. Then we update where the card is. GameManager adds and removes cards.

        If card1 exists in the draw pile, and we try to add it to the players hand, then we have to remove the card
        from the draw pile. Then we update where the card is. GameManager adds and removes cards.

        If we try to

        If the card already exists in the destination pile: then return false
         */




        return !cards.contains(card);
    }

}
