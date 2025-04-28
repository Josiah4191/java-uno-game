package multiplayer.client.clientmessage;

import com.google.gson.Gson;


/*
    This is an abstract base class that represents a type of action to be sent from client to server. Each GameAction
    subclass should set their type in the constructor.

    This class also has a method that converts itself into JSON to send the data as a JSON String.

    ChangeNameAction takes place when a player wants to change their name
 */

public abstract class GameAction {
    private GameActionType type;

    // sets the type of action
    public void setType(GameActionType type) {
        this.type = type;
    }

    // gets the type of action
    public GameActionType getType() {
        return type;
    }

    // this method returns a JSON String for this object
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
