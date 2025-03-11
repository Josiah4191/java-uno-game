package games.cardgames.cards.unocards;

import java.io.File;

public class Test {
    public static void main(String[] args) {
        UnoCard card1 = new UnoCard(UnoSuit.RED, UnoValue.ONE);

        UnoCardImageManager manager = new UnoCardImageManager(UnoCardTheme.CLASSIC);

        String cardImagePath = manager.getImage(card1);

        System.out.println(cardImagePath);

        File file = new File("/Users/stoj/IdeaProjects/java-uno-game/src/images/cardimages/Red_Draw.png");

        System.out.println("Absolute Path: " + file.getAbsolutePath());
        System.out.println("File Exists: " + file.exists());

       // /Users/stoj/IdeaProjects/java-uno-game/src/images/cardimages


    }
}
