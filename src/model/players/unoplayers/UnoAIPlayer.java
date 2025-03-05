package model.players.unoplayers;

import model.cards.uno.UnoCard;
import model.cards.uno.UnoPlayerHandPile;
import model.players.Player;

public class UnoAIPlayer extends Player<UnoCard> {

    public UnoAIPlayer(UnoPlayerHandPile playerHand) {
        super(playerHand);
    }

}
