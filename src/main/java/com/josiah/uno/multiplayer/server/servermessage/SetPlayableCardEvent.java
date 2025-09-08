package com.josiah.uno.multiplayer.server.servermessage;

import com.josiah.uno.model.cardgame.card.unocard.UnoCard;
import java.util.List;

/*
    This class is a bean class, or data transfer object, and it is used to send information from server
    to client.

    SetPlayableCardEvent takes place when the server game manager updates the list of playable cards
 */

public class SetPlayableCardEvent extends GameEvent {
    List<UnoCard> playableCards; // store the updated list of playable cards

    public SetPlayableCardEvent(List<UnoCard> playableCards) {
        this.playableCards = playableCards;
        setType(GameEventType.SET_PLAYABLE_CARDS); // the constructor sets the event type
    }

    // get the list of playable cards
    public List<UnoCard> getPlayableCards() {
        return playableCards;
    }

    // set the list of playable cards
    public void setPlayableCards(List<UnoCard> playableCards) {
        this.playableCards = playableCards;
    }
}
