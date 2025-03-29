package multiplayer.client.clientmessage;

public class PlayCardAction extends GameAction {
    private int cardIndex;

    public PlayCardAction(int cardIndex) {
        this.cardIndex = cardIndex;
        setType(GameActionType.PLAY_CARD);
    }

    public int getCardIndex() {
        return cardIndex;
    }

    public void setCardIndex(int cardIndex) {
        this.cardIndex = cardIndex;
    }
}
