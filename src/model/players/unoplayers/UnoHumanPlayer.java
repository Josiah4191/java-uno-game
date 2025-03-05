package model.players.unoplayers;

import model.cards.uno.UnoCard;
import model.cards.uno.UnoPlayerHandPile;
import model.players.Player;

public class UnoHumanPlayer extends Player<UnoCard> {

    public UnoHumanPlayer(UnoPlayerHandPile playerHand) {
        super(playerHand);
    }

}
