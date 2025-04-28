package multiplayer.client.clientmessage;

/*
    This class is a bean class, or data transfer object, and it is used to send information from client
    to server.

    Call UNO is an action event that takes place when a player calls UNO on another player if they have 1 card
    remaining.
 */

public class CallUnoAction extends GameAction {

    int playerIndex; // store the player index for the player who is being called on

    public CallUnoAction(int playerIndex) {
        this.playerIndex = playerIndex;
        setType(GameActionType.CALL_UNO); // the constructor sets the action type
    }

    // get the player index
    public int getPlayerIndex() {
        return playerIndex;
    }

    // set the player index
    public void setPlayerIndex(int playerIndex) {
        this.playerIndex = playerIndex;
    }
}
