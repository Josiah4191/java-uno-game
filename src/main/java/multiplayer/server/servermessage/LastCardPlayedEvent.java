package multiplayer.server.servermessage;

import model.cardgame.card.unocard.UnoCard;

public class LastCardPlayedEvent extends GameEvent {
    private UnoCard lastPlayedCard;

    public LastCardPlayedEvent(UnoCard lastPlayedCard) {
        this.lastPlayedCard = lastPlayedCard;
        setType(GameEventType.LAST_CARD_PLAYED);
    }

    public UnoCard getLastPlayedCard() {
        return lastPlayedCard;
    }

}
