package multiplayer.server.servermessage;

import model.cardgame.card.unocard.UnoCard;

import java.util.List;

/*
    This class is a bean class, or data transfer object, and it is used to send information from server
    to client.

    ApplyPenaltyEvent takes place when a penalty is applied to a player and that player has cards added to them.
 */

public class ApplyPenaltyEvent extends GameEvent {

    private int playerIndex; // store the player index for the player who drew cards
    private int totalCardsRemaining; // store the total number of cards remaining for that player
    private List<UnoCard> cardsDrawn; // store the UnoCard objects that were drawn

    public ApplyPenaltyEvent(int playerIndex, List<UnoCard> cardsDrawn, int totalCardsRemaining) {
        this.playerIndex = playerIndex;
        this.totalCardsRemaining = totalCardsRemaining;
        this.cardsDrawn = cardsDrawn;
        setType(GameEventType.APPLY_PENALTY); // the constructor sets the event type
    }

    // get the player index
    public int getPlayerIndex() {
        return playerIndex;
    }

    // set the player index
    public void setPlayerIndex(int playerIndex) {
        this.playerIndex = playerIndex;
    }

    // get total cards remaining
    public int getTotalCardsRemaining() {
        return totalCardsRemaining;
    }

    // get the list of UnoCards that were drawn
    public List<UnoCard> getCardsDrawn() {
        return cardsDrawn;
    }

}
