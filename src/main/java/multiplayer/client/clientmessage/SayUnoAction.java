package multiplayer.client.clientmessage;

public class SayUnoAction extends GameAction {
    private boolean sayUno;

    public SayUnoAction(boolean sayUno) {
        this.sayUno = sayUno;
        setType(GameActionType.SAY_UNO);
    }

    public boolean isSayUno() {
        return sayUno;
    }

    public void setSayUno(boolean sayUno) {
        this.sayUno = sayUno;
    }
}
