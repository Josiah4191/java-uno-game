package TrashBin;

import games.cardgames.cards.Pile;

public abstract class CardManager<P extends Pile<C>, C> {

    public abstract boolean validate(C card, Pile<C> destinationPile);

}
