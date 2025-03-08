package games.cardgames.unogame;

import games.Moderator;
import games.cardgames.cards.unocards.UnoCard;
import games.players.cardplayers.unoplayers.UnoPlayer;

public class UnoModerator extends Moderator {

    private UnoRules rules;
    private UnoGameState gameState;

    public UnoModerator(UnoRules rules, UnoGameState gameState) {
        this.rules = rules;
        this.gameState = gameState;
    }

    public boolean validatePlay(UnoCard card) {
        return rules.validatePlay(card, gameState.getLastPlayedCard());
    }

    public int callUnoPenalty(UnoPlayer player) {
        return rules.getCallUnoPenalty(player);
    }

    public int getCardPenalty(UnoPlayer player) {
        return rules.getCompareCardPenalty(player, gameState.getLastPlayedCard());
    }

    public void applyPenalty(UnoPlayer player, int cardPenalty) {
        for (int i = 0; i < cardPenalty; i++) {
            player.addCard(gameState.drawCardFromDrawPile());
        }
    }
}
