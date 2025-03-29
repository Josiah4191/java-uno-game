package multiplayer.client.clientmessage;

public class ChangeNameAction extends GameAction {

    private String name;

    public ChangeNameAction(String name) {
        setType(GameActionType.CHANGE_NAME);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
