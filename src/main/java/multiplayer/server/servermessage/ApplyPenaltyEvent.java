package multiplayer.server.servermessage;

import model.cardgame.card.unocard.UnoCard;

import java.util.List;

public class ApplyPenaltyEvent extends GameEvent {

    private int playerIndex;
    private int totalCardsRemaining;
    private List<UnoCard> cardsDrawn;

    public ApplyPenaltyEvent(int playerIndex, List<UnoCard> cardsDrawn, int totalCardsRemaining) {
        this.playerIndex = playerIndex;
        this.totalCardsRemaining = totalCardsRemaining;
        this.cardsDrawn = cardsDrawn;
        setType(GameEventType.APPLY_PENALTY);
    }

    public int getPlayerIndex() {
        return playerIndex;
    }

    public void setPlayerIndex(int playerIndex) {
        this.playerIndex = playerIndex;
    }

    public int getTotalCardsRemaining() {
        return totalCardsRemaining;
    }

    public List<UnoCard> getCardsDrawn() {
        return cardsDrawn;
    }

}
