package games.cardgames.uno.unoplayers;

import games.cardgames.uno.unocards.UnoCard;
import games.cardgames.uno.unocards.UnoPlayerHandPile;
import games.cardgames.cardplayers.CardPlayer;

public class UnoHumanPlayer extends CardPlayer<UnoCard> {

    public UnoHumanPlayer(UnoPlayerHandPile playerHand) {
        super(playerHand);
    }

}
