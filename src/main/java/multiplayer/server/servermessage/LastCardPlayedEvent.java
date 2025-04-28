package multiplayer.server.servermessage;

import model.cardgame.card.unocard.UnoCard;

/*
    This class is a bean class, or data transfer object, and it is used to send information from server
    to client.

    LastCardPlayedEvent takes place when the server has updated the last card played.
 */

public class LastCardPlayedEvent extends GameEvent {
    private UnoCard lastPlayedCard; // store the UnoCard object for the last played card

    public LastCardPlayedEvent(UnoCard lastPlayedCard) {
        this.lastPlayedCard = lastPlayedCard;
        setType(GameEventType.LAST_CARD_PLAYED); // the constructor sets the event type
    }

    // get the last played card
    public UnoCard getLastPlayedCard() {
        return lastPlayedCard;
    }

}
