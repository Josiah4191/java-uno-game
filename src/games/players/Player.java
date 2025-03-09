package games.players;

/*
Team Members: Steve Wareham, Charles Davidson, Josiah Stoltzfus
Author: Josiah Stoltzfus
Date: 3/7/2025
------------------------------------------------------------------------------
*/

public abstract class Player {
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
