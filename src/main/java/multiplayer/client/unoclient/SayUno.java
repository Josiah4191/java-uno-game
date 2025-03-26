package multiplayer.client.unoclient;

public class SayUno extends ClientAction {
    private boolean sayUno;

    public SayUno(boolean sayUno) {
        this.sayUno = sayUno;
        setType(ClientActionType.SAY_UNO);
    }

    public boolean isSayUno() {
        return sayUno;
    }

    public void setSayUno(boolean sayUno) {
        this.sayUno = sayUno;
    }
}
