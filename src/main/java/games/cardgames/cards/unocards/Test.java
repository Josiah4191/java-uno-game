package games.cardgames.cards.unocards;

public class Test {
    public static void main(String[] args) {

        UnoCardImageManager manager = new UnoCardImageManager();

        try {
            manager.loadImages();
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }
}
