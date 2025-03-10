package games.players.cardplayers;

import games.cardgames.cards.Pile;
import games.cardgames.cards.unocards.UnoCard;
import games.players.Player;

import java.util.Collections;
import java.util.List;

/*
Team Members: Steve Wareham, Charles Davidson, Josiah Stoltzfus
Author: Josiah Stoltzfus
Date: 3/7/2025
-----------------------------------------------------------------------------
-
This abstract generic class represents a player for card games.
    - Each card game should subclass Pile to have its own type of pile representing a player's cards.
    - Each card game should subclass Card to have its own type of card.

The type of pile of cards and the specific card itself is determined by this class's subclass.
    - e.g., UnoPlayer is a subclass that uses UnoHandPile for the pile, and UnoCard for the card.
 */

public abstract class CardPlayer<P extends Pile<C>, C> extends Player {
    /*
    The playerHand variable holds a player's pile of cards. The player owns and manages their cards.
    - P represents Pile, or any subclass of Pile.
    - C represents Card.
     */
    private P playerHand;

    /*
    The constructor receives a Pile, which represents a set of cards, and then sets it to playerHand.
     */
    public CardPlayer(P playerHand) {
        this.playerHand = playerHand;
    }
    /*
    The addCard(C card) method accepts a Card object.
    The playerHand is a Pile object, which calls its addCard method to add that card to the pile.
     */
    public void addCard(C card) {
        playerHand.addCard(card);
    }

    /*
    The getPlayerHand() method returns a view, or unmodifiable list, of the player's cards.
    The playerHand Pile object has a method getCardPile(), which returns the pile of cards.
    NOTE:
        - This is a public method right now for testing purposes, but it needs to be protected or removed
        so other players or the computer AI cannot access this list.
        - If other players or the AI could see this list, that would be cheating.
        - UNO doesn't allow players to see their opponents cards.
     */
    public List<C> getPlayerHand() {
        return Collections.unmodifiableList(playerHand.getCardPile());
    }

    /*
    The playCard(int index) method returns a Card object. The index parameter is an integer that identifies the
    card in their pile of cards that they want to remove.
    The playerHand Pile object has a method drawCard(int index) that gets a card from its list of cards.
    NOTE:
        This is the method that will be used by human players.
     */
    public C playCard(int cardIndex) {
        return playerHand.drawCard(cardIndex);
    }

    /*
    The getTotalCardsRemaining() method returns an integer that represents the number of cards remaining in a
    player's Pile of cards.
     */
    public int getTotalCardsRemaining() {
        return playerHand.size();
    }

    /*
    The isEmpty() method returns a boolean that indicates if the player's Pile of cards is empty.
     */
    public boolean isEmpty() {
        return playerHand.isEmpty();
    }

    public void swapCardPositions(UnoCard card1, UnoCard card2) {
        var cards = playerHand.getCardPile();
        int card1Index = cards.indexOf(card1);
        int card2Index = cards.indexOf(card2);
        Collections.swap(cards, card1Index, card2Index);
    }

}
