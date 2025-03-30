package multiplayer.client.clientmessage;

import model.cardgame.card.unocard.UnoSuit;

public class ChangeSuitAction extends GameAction {

    private UnoSuit suit;

    public ChangeSuitAction(UnoSuit suit) {
        this.suit = suit;
        setType(GameActionType.CHANGE_SUIT);
    }

    public UnoSuit getSuit() {
        return suit;
    }

    public void setSuit(UnoSuit suit) {
        this.suit = suit;
    }
}
