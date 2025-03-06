package games.cardgames.uno.unoplayers;

import games.cardgames.uno.unocards.UnoCard;
import games.cardgames.uno.unocards.UnoPlayerHandPile;
import games.cardgames.cardplayers.CardPlayer;

public class UnoPlayer extends CardPlayer<UnoCard> {

    public UnoPlayer() {
        this(new UnoPlayerHandPile());
    }

    public UnoPlayer(UnoPlayerHandPile playerHand) {
        super(playerHand);
    }
}
