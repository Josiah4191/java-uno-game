package multiplayer.client.unoclient;

import com.google.gson.Gson;

public abstract class ClientAction {
    private ClientActionType type;

    public void setType(ClientActionType type) {
        this.type = type;
    }

    public ClientActionType getType() {
        return type;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
