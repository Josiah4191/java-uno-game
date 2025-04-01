package multiplayer.server.servermessage;

public class NoOpEvent extends GameEvent {

    private String description;

    public NoOpEvent(String description) {
        this.description = description;
        setType(GameEventType.NO_OP);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
