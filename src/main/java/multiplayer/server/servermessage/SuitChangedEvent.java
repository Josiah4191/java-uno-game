package multiplayer.server.servermessage;

import model.cardgame.card.unocard.UnoSuit;

public class SuitChangedEvent extends GameEvent {
    UnoSuit suit;

    public SuitChangedEvent(UnoSuit suit) {
        this.suit = suit;
        setType(GameEventType.SUIT_CHANGED);
    }

    public UnoSuit getSuit() {
        return suit;
    }

    public void setSuit(UnoSuit suit) {
        this.suit = suit;
    }
}
