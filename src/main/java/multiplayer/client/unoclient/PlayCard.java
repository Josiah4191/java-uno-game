package multiplayer.client.unoclient;

public class PlayCard extends ClientAction {
    private int cardIndex;

    public PlayCard(int cardIndex) {
        this.cardIndex = cardIndex;
        setType(ClientActionType.PLAY_CARD);
    }

    public int getCardIndex() {
        return cardIndex;
    }

    public void setCardIndex(int cardIndex) {
        this.cardIndex = cardIndex;
    }
}
