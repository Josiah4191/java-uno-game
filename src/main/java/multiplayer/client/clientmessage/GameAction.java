package multiplayer.client.clientmessage;

import com.google.gson.Gson;

public abstract class GameAction {
    private GameActionType type;

    public void setType(GameActionType type) {
        this.type = type;
    }

    public GameActionType getType() {
        return type;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
