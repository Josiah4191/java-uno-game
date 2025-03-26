package model.image.playerimage;

import java.io.Serializable;

public enum PlayerImage implements Serializable {
    P1("Botwave"), P2("Sneakabyte"), P3("ZappyZap"), P4("Hoptronic"),
    P5("Lil Loopy"), P6("Techspert"), P7("Sir Chatterly"), P8("Baron von Ping"),
    P9("Captain Pingblast"), P10("Zorgnobble"), P11("CoolioBot"), P12("BeanieByte"),
    P13("NetNeutralizer"), P14("Meowtrix"), P15("SpookoLantern"), P16("WhiskerZapp"),
    P17("King Glitchor"), P18("Modulus Prime"), P19("Xynklor"), P20("Dr. Buffer");

    private String name;

    PlayerImage(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
