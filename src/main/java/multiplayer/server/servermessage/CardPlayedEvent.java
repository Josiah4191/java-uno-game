package multiplayer.server.servermessage;

import model.cardgame.card.unocard.UnoCard;
import model.cardgame.card.unocard.UnoSuit;
import model.cardgame.unogame.PlayDirection;

public class CardPlayedEvent extends GameEvent {

    private int playerIndex;
    private int cardIndex;
    private int currentPlayerIndex;
    private int totalCardsRemaining;
    private UnoCard lastPlayedCard;
    private UnoSuit currentSuit;
    private PlayDirection playDirection;

    public CardPlayedEvent(int playerIndex, int cardIndex, int currentPlayerIndex, int totalCardsRemaining, UnoCard lastPlayedCard, UnoSuit currentSuit, PlayDirection playDirection) {
        this.playerIndex = playerIndex;
        this.cardIndex = cardIndex;
        this.currentPlayerIndex = currentPlayerIndex;
        this.totalCardsRemaining = totalCardsRemaining;
        this.lastPlayedCard = lastPlayedCard;
        this.currentSuit = currentSuit;
        this.playDirection = playDirection;
        setType(GameEventType.CARD_PLAYED);
    }

    public int getPlayerIndex() {
        return playerIndex;
    }

    public void setPlayerIndex(int playerIndex) {
        this.playerIndex = playerIndex;
    }

    public int getCardIndex() {
        return cardIndex;
    }

    public void setCardIndex(int cardIndex) {
        this.cardIndex = cardIndex;
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public void setCurrentPlayerIndex(int currentPlayerIndex) {
        this.currentPlayerIndex = currentPlayerIndex;
    }

    public int getTotalCardsRemaining() {
        return totalCardsRemaining;
    }

    public void setTotalCardsRemaining(int totalCardsRemaining) {
        this.totalCardsRemaining = totalCardsRemaining;
    }

    public UnoCard getLastPlayedCard() {
        return lastPlayedCard;
    }

    public void setLastPlayedCard(UnoCard lastPlayedCard) {
        this.lastPlayedCard = lastPlayedCard;
    }

    public UnoSuit getCurrentSuit() {
        return currentSuit;
    }

    public void setCurrentSuit(UnoSuit currentSuit) {
        this.currentSuit = currentSuit;
    }

    public PlayDirection getPlayDirection() {
        return playDirection;
    }

    public void setPlayDirection(PlayDirection playDirection) {
        this.playDirection = playDirection;
    }
}
