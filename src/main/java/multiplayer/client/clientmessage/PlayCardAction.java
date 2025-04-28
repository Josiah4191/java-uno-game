package multiplayer.client.clientmessage;

/*
    This class is a bean class, or data transfer object, and it is used to send information from client
    to server.

    PlayCardAction takes place when a player selects a card to play.
 */

public class PlayCardAction extends GameAction {
    private int cardIndex; // store the selected card index for the card the player chooses to play

    public PlayCardAction(int cardIndex) {
        this.cardIndex = cardIndex;
        setType(GameActionType.PLAY_CARD); // the constructor sets the action type
    }

    // get the card index
    public int getCardIndex() {
        return cardIndex;
    }

    // set the card index
    public void setCardIndex(int cardIndex) {
        this.cardIndex = cardIndex;
    }
}
