package multiplayer.client.clientmessage;

/*
    This class is a bean class, or data transfer object, and it is used to send information from client
    to server.

    ChangeNameAction takes place when a player wants to change their name
 */

public class ChangeNameAction extends GameAction {

    private String name; // store the name as a String for what the player wants to change to

    public ChangeNameAction(String name) {
        setType(GameActionType.CHANGE_NAME); // the constructor sets the action type
        this.name = name;
    }

    // get the name
    public String getName() {
        return name;
    }

    // set the name
    public void setName(String name) {
        this.name = name;
    }

}
