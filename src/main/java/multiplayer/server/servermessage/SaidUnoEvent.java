package multiplayer.server.servermessage;

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

    public boolean isSayUno() {
        return sayUno;
    }

    public void setSayUno(boolean sayUno) {
        this.sayUno = sayUno;
    }
}
