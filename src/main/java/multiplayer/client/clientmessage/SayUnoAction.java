package multiplayer.client.clientmessage;

/*
    This class is a bean class, or data transfer object, and it is used to send information from client
    to server.

    SayUnoAction takes place when a player says UNO if they have 1 card remaining.
 */

public class SayUnoAction extends GameAction {
    private boolean sayUno; // store the say UNO status of the player

    public SayUnoAction(boolean sayUno) {
        this.sayUno = sayUno;
        setType(GameActionType.SAY_UNO); // the constructor sets the action type
    }

    // get say UNO
    public boolean isSayUno() {
        return sayUno;
    }

    // set say UNO
    public void setSayUno(boolean sayUno) {
        this.sayUno = sayUno;
    }
}
