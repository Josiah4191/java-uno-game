package multiplayer.server.servermessage;

import model.cardgame.card.unocard.UnoCard;

public class CardDrawnEvent extends GameEvent {

    private int playerIndex;
    private int currentPlayerIndex;
    private int totalCardsRemaining;
    private UnoCard drawnCard;
    private boolean cardIsPlayable;

    public CardDrawnEvent(int playerIndex, UnoCard cardDrawn, int totalCardsRemaining, int nextPlayerIndex, boolean cardIsPlayable) {
        this.playerIndex = playerIndex;
        this.drawnCard = cardDrawn;
        this.totalCardsRemaining = totalCardsRemaining;
        this.currentPlayerIndex = nextPlayerIndex;
        this.cardIsPlayable = cardIsPlayable;
        setType(GameEventType.CARD_DRAWN);
    }

    public int getPlayerIndex() {
        return playerIndex;
    }

    public void setPlayerIndex(int playerIndex) {
        this.playerIndex = playerIndex;
    }

    public UnoCard getDrawnCard() {
        return drawnCard;
    }

    public void setDrawnCard(UnoCard drawnCard) {
        this.drawnCard = drawnCard;
    }

    public int getTotalCardsRemaining() {
        return totalCardsRemaining;
    }

    public void setTotalCardsRemaining(int totalCardsRemaining) {
        this.totalCardsRemaining = totalCardsRemaining;
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public void setCurrentPlayerIndex(int currentPlayerIndex) {
        this.currentPlayerIndex = currentPlayerIndex;
    }

    public boolean isCardIsPlayable() {
        return cardIsPlayable;
    }

    public void setCardIsPlayable(boolean cardIsPlayable) {
        this.cardIsPlayable = cardIsPlayable;
    }
}
