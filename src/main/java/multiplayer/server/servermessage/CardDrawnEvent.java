package multiplayer.server.servermessage;

import model.cardgame.card.unocard.UnoCard;

/*
    This class is a bean class, or data transfer object, and it is used to send information from server
    to client.

    CardDrawnEvent takes place when a card is drawn from the draw pile.
 */

public class CardDrawnEvent extends GameEvent {

    private int playerIndex; // store the player index for the player that drew a card
    private int currentPlayerIndex; // store the current player index
    private int totalCardsRemaining; // store the total number of cards that the player has
    private UnoCard drawnCard; // store the UnoCard object that was drawn

    public CardDrawnEvent(int playerIndex, UnoCard cardDrawn, int totalCardsRemaining, int nextPlayerIndex) {
        this.playerIndex = playerIndex;
        this.drawnCard = cardDrawn;
        this.totalCardsRemaining = totalCardsRemaining;
        this.currentPlayerIndex = nextPlayerIndex;
        setType(GameEventType.CARD_DRAWN); // the constructor sets the event type
    }

    // get the player index
    public int getPlayerIndex() {
        return playerIndex;
    }

    // set the player index
    public void setPlayerIndex(int playerIndex) {
        this.playerIndex = playerIndex;
    }

    // get the drawn card
    public UnoCard getDrawnCard() {
        return drawnCard;
    }

    // get the total cards remaining
    public int getTotalCardsRemaining() {
        return totalCardsRemaining;
    }

    // get the current player index
    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

}
