package multiplayer.server.servermessage;

/*
    This class is a bean class, or data transfer object, and it is used to send information from server
    to client.

    ImageChangedEvent takes place when the server has changed a player's image.
 */

public class SaidUnoEvent extends GameEvent {

    private int playerIndex;
    public boolean sayUno;

    public SaidUnoEvent(int playerIndex, boolean sayUno) {
        this.playerIndex = playerIndex;
        this.sayUno = sayUno;
        setType(GameEventType.SAID_UNO);
    }

    public int getPlayerIndex() {
        return playerIndex;
    }

    public void setPlayerIndex(int playerIndex) {
        this.playerIndex = playerIndex;
    }

    public boolean getSayUno() {
        return sayUno;
    }

}
