package multiplayer.server.servermessage;

import model.cardgame.card.unocard.UnoCard;

import java.util.List;

public class SetPlayableCardEvent extends GameEvent {
    List<UnoCard> playableCards;

    public SetPlayableCardEvent(List<UnoCard> playableCards) {
        this.playableCards = playableCards;
        setType(GameEventType.SET_PLAYABLE_CARDS);
    }

    public List<UnoCard> getPlayableCards() {
        return playableCards;
    }

    public void setPlayableCards(List<UnoCard> playableCards) {
        this.playableCards = playableCards;
    }
}
