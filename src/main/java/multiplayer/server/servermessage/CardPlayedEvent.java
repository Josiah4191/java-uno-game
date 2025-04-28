package multiplayer.server.servermessage;

import model.cardgame.card.unocard.UnoCard;
import model.cardgame.card.unocard.UnoSuit;
import model.cardgame.unogame.PlayDirection;

/*
    This class is a bean class, or data transfer object, and it is used to send information from server
    to client.

    CardPlayedEvent takes place when a player plays a card from their hand of cards
 */

public class CardPlayedEvent extends GameEvent {

    private int playerIndex; // store the player index for the player who played a card
    private int cardIndex; // store the card index for the card that the player selected from their list of cards
    private int currentPlayerIndex; // store the updated current player index
    private int totalCardsRemaining; // store the total card remaining for the player
    private UnoCard lastPlayedCard; // store the updated last played card
    private UnoSuit currentSuit; // store the updated current suit
    private PlayDirection playDirection; // store the updated play direction

    public CardPlayedEvent(int playerIndex, int cardIndex, int currentPlayerIndex, int totalCardsRemaining, UnoCard lastPlayedCard, UnoSuit currentSuit, PlayDirection playDirection) {
        this.playerIndex = playerIndex;
        this.cardIndex = cardIndex;
        this.currentPlayerIndex = currentPlayerIndex;
        this.totalCardsRemaining = totalCardsRemaining;
        this.lastPlayedCard = lastPlayedCard;
        this.currentSuit = currentSuit;
        this.playDirection = playDirection;
        setType(GameEventType.CARD_PLAYED); // the constructor sets the event type
    }

    // get the player index
    public int getPlayerIndex() {
        return playerIndex;
    }

    // set the player index
    public void setPlayerIndex(int playerIndex) {
        this.playerIndex = playerIndex;
    }

    // get the card index
    public int getCardIndex() {
        return cardIndex;
    }

    // set the card index
    public void setCardIndex(int cardIndex) {
        this.cardIndex = cardIndex;
    }

    // get the current player index
    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    // set the current player index
    public void setCurrentPlayerIndex(int currentPlayerIndex) {
        this.currentPlayerIndex = currentPlayerIndex;
    }

    // get the total cards remaining
    public int getTotalCardsRemaining() {
        return totalCardsRemaining;
    }

    // set the total cards remaining
    public void setTotalCardsRemaining(int totalCardsRemaining) {
        this.totalCardsRemaining = totalCardsRemaining;
    }

    // get the last played card
    public UnoCard getLastPlayedCard() {
        return lastPlayedCard;
    }

    // set the last played card
    public void setLastPlayedCard(UnoCard lastPlayedCard) {
        this.lastPlayedCard = lastPlayedCard;
    }

    // get the current suit
    public UnoSuit getCurrentSuit() {
        return currentSuit;
    }

    // set the current suit
    public void setCurrentSuit(UnoSuit currentSuit) {
        this.currentSuit = currentSuit;
    }

    // get the play direction
    public PlayDirection getPlayDirection() {
        return playDirection;
    }

    // set the play direction
    public void setPlayDirection(PlayDirection playDirection) {
        this.playDirection = playDirection;
    }
}
