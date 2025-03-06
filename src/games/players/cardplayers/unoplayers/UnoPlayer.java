package games.players.cardplayers.unoplayers;

import games.cardgames.cards.unocards.UnoCard;
import games.cardgames.cards.unocards.UnoPlayerHandPile;
import games.players.cardplayers.CardPlayer;

public class UnoPlayer extends CardPlayer<UnoPlayerHandPile, UnoCard> {

    public UnoPlayer() {
        this(new UnoPlayerHandPile());
    }

    public UnoPlayer(UnoPlayerHandPile playerHand) {
        super(playerHand);
    }
}
