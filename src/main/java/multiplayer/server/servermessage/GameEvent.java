package multiplayer.server.servermessage;

import com.google.gson.Gson;

/*
    This is an abstract base class that represents an event that is sent from server to client. Each GameEvent
    subclass should set their type in the constructor.

    This class also has a method that converts itself into JSON to send the data as a JSON String.
 */

public class GameEvent {

    private GameEventType type;

    // get the type of event
    public GameEventType getType() {
        return type;
    }

    // set the type of event
    public void setType(GameEventType type) {
        this.type = type;
    }

    // this method returns a JSON String for this object
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
