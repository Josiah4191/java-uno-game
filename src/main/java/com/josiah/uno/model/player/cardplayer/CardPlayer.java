package com.josiah.uno.model.player.cardplayer;

import com.josiah.uno.model.cardgame.card.Pile;
import com.josiah.uno.model.player.Player;

import java.util.Collections;
import java.util.List;

/*
This abstract generic class represents a player for card main.java.games.
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
    public CardPlayer(P playerHand, int playerID) {
        super(playerID);
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
     */
    public List<C> getPlayerHand() {
        return Collections.unmodifiableList(playerHand.getCardPile());
    }

    /*
        The playCard(int index) method returns a Card object. The index parameter is an integer that identifies the
        card in their pile of cards that they want to remove.
        The playerHand Pile object has a method drawCard(int index) that gets a card from its list of cards.
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
        This method gets the card at the given card index in the player's hand. It is used to get a card so that it can be
        validated. The play card method for the CardPlayer is used to actually play a card if it is valid. This was needed
        because the playCard will draw the card and remove it from the player's hand, but if the card isn't valid, then that
        shouldn't happen.
     */
    public C getCard(int cardIndex) {
        return getPlayerHand().get(cardIndex);
    }

}
