package games.players.cardplayers.unoplayers;

import games.Difficulty;
import games.cardgames.cards.unocards.UnoEdition;
import games.cardgames.unogame.UnoGameState;

public class Test {
    public static void main(String[] args) {
        UnoPlayer player1 = new UnoPlayer();
        UnoPlayer player2 = new UnoPlayerAI(new UnoGameState(UnoEdition.CLASSIC, Difficulty.EASY));

        System.out.println(player1.getClass());
        System.out.println(player2.getClass());

    }
}
