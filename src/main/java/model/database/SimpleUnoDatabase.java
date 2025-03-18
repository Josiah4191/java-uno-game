package model.database;

import model.cardgames.unogame.UnoGameState;

import java.io.*;

public class SimpleUnoDatabase {

    private static String cwd = System.getProperty("user.home") + File.separator + "Uno_Game" + File.separator + "Saved_Games" + File.separator;
    private static File file = new File(cwd);
    private static File save_file = new File(cwd + "uno_save.uno");

    public static void saveGame(UnoGameState gameState) {

        if (!(file.exists())) {
            file.mkdirs();
        }

        try {
            FileOutputStream fos = new FileOutputStream(save_file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(gameState);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static UnoGameState loadGame() {

        UnoGameState gameState = null;

        try {
            FileInputStream fis = new FileInputStream(save_file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            gameState = (UnoGameState) ois.readObject();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return gameState;

    }
}
