package multiplayer.client.unoclient;

public class ChangePlayerName extends ClientAction {

    private String name;

    public ChangePlayerName(String name) {
        setType(ClientActionType.CHANGE_NAME);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
