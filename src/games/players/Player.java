package games.players;

/*
Last edited: Josiah Stoltzfus
Date: 3/7/2025
    - Added name variable with get/set methods
------------------------------------------------------------------------------
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
