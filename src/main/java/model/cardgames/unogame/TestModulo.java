package model.cardgames.unogame;

public class TestModulo {
    public static void main(String[] args) {
        /*
        the current player index is 1

        the number of players is 5

        the direction is forward

        move forward to the next player

        1 + 1 = 2 is the next player index

        2 % 5 = ?

        current player index is 0

        the number of players is 5

        the direction is forward

        move forward to the next player

        (0 - 1) = -2

        (-2 + 5) = 3 % 5 = 3
         */
        System.out.println(2 % 5);

    }
}
