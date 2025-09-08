package com.josiah.uno.model.database;

import com.josiah.uno.model.cardgame.unogame.UnoGameState;
import com.josiah.uno.model.image.cardimage.UnoCardImageManager;
import com.josiah.uno.model.image.playerimage.PlayerImageManager;

import java.io.*;

/*
    This class is used for saving and loading the state of the game. This is a simple class that simply saves and loads
    the UnoGameState object to a file. It is not dynamic.
 */
public class UnoDatabase {

    // get the current working directory as a String and create the directory for the UnoGame and SavedGames to be placed
    private static String cwd = System.getProperty("user.home") + File.separator + "Uno_Game" + File.separator + "Saved_Games" + File.separator;
    // create a File object with the directory for where the saved game file is to be saved
    private static File file = new File(cwd);
    // create a File object for the actual save file
    private static File save_file = new File(cwd + "uno_save.uno");

    /*
        This method saves the state of the game. It receives the UnoGameState object so that the gameState object can be
        written to the file.
     */
    public static void saveGame(UnoGameState gameState) {

        // check if file exists
        if (!(file.exists())) {
            file.mkdirs(); // make the directory if it doesn't exist
        }

        try {
            FileOutputStream fos = new FileOutputStream(save_file); // create FileOutputStream
            ObjectOutputStream oos = new ObjectOutputStream(fos); // create ObjectOutputStream
            oos.writeObject(gameState); // write the gameState object to the file
        // handle exceptions
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + save_file.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("Error saving file: " + save_file.getAbsolutePath());
        }
    }

    // This method loads the same file that the game state was saved to.
    public static UnoGameState loadGame() {
        try {
            FileInputStream fis = new FileInputStream(save_file); // create FileInputStream
            ObjectInputStream ois = new ObjectInputStream(fis); // create ObjectInputStream
            UnoGameState gameState = (UnoGameState) ois.readObject(); // get the gameState object by reading from the file
            /*
                The image manager classes need to be set again, because they are not serializable and are transient
                inside the UnoGameState class.
             */
            gameState.setPlayerImageManager(new PlayerImageManager());
            gameState.setCardImageManager(new UnoCardImageManager());
            return gameState;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
