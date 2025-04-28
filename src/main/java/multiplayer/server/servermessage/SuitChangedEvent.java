package multiplayer.server.servermessage;

import model.cardgame.card.unocard.UnoSuit;

/*
    This class is a bean class, or data transfer object, and it is used to send information from server
    to client.

    SuitChangedEvent takes place when the server game manager updates the current suit
 */

public class SuitChangedEvent extends GameEvent {
    UnoSuit suit; // store the updated current suit

    public SuitChangedEvent(UnoSuit suit) {
        this.suit = suit;
        setType(GameEventType.SUIT_CHANGED); // the constructor sets the event type
    }

    // get the suit
    public UnoSuit getSuit() {
        return suit;
    }

    // set the suit
    public void setSuit(UnoSuit suit) {
        this.suit = suit;
    }
}
