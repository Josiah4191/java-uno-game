package multiplayer.client.clientmessage;

import model.cardgame.card.unocard.UnoSuit;

/*
    This class is a bean class, or data transfer object, and it is used to send information from client
    to server.

    ChangeSuitAction takes place when a player selects a new suit after playing a Wild card
 */

public class ChangeSuitAction extends GameAction {

    private UnoSuit suit; // store the suit for what the player selected

    public ChangeSuitAction(UnoSuit suit) {
        this.suit = suit;
        setType(GameActionType.CHANGE_SUIT); // the constructor sets the action type
    }

    // get the suit
    public UnoSuit getSuit() {
        return suit;
    }

    // set the suit
    public void setSuit(UnoSuit suit) {
        this.suit = suit;
    }
}
