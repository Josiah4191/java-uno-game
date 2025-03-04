package application;

import model.cards.uno.UnoDeck;
import model.cards.uno.UnoEdition;

public class Test {
    public static void main(String[] args) {

        UnoDeck deck = new UnoDeck(UnoEdition.CLASSIC);

        deck.getDeck().forEach(System.out::println);

        System.out.println(deck.getDeck().size());


    }

}
