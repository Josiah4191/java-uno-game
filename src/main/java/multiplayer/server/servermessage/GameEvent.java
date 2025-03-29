package multiplayer.server.servermessage;

import com.google.gson.Gson;

public class GameEvent {

    private GameEventType type;

    public GameEventType getType() {
        return type;
    }

    public void setType(GameEventType type) {
        this.type = type;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
