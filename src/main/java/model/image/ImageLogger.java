package model.image;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/*
    This is a logger class that logs message to a file when there is an error loading an image.

    A Logger object can log messages to a file.
        - Create a Logger object
        - Create a file for where the logs should be saved to
        - Create a FileHandler object which is used for setting the location of the log file and managing
          the logs.
        - Set the FileHandler object to the Logger object
        - Use the Logger object to log messages, which will save the messages to the log file that was set
          in its FileHandler object.
 */

public class ImageLogger {

    // Create a Logger object and name it "ImageLogger"
    public static final Logger IMAGE_LOGGER = Logger.getLogger("ImageLogger");

    /*
        This static initialization block makes sure that the file for the logger exists. If it doesn't,
        then it will create the file for the logs to be saved to.
     */
    static {
        // create file path name as a String
        // this will look like: /Users/stoj/IdeaProjects/java-uno-game/logs
        String dirPath = System.getProperty("user.dir") + File.separator + "logs";
        File file = new File(dirPath); // create File object with the dirPath name

        // check if the file exists
        if (!file.exists()) {
            file.mkdir(); // if it doesn't exist, make the directory using the File object
        }

        // create the actual log file name including the absolute path
        // this will look like: /Users/stoj/IdeaProjects/java-uno-game/logs/game.log
        String logPath = dirPath + File.separator + "game.log";
        try {
            // Create a FileHandler object. This is used by the Logger object
            FileHandler fileHandler = new FileHandler(logPath, 5000000, 1,  true);
            fileHandler.setFormatter(new SimpleFormatter());

            /*
                There was a lock file that was causing errors when the FileHandler object was created. It was automatically
                being called 'game.log.lck'

                To get around this, I check if that file exists in the same location as the game.log file. If it is, then
                I delete it. This prevents issues I was having when setting the FileHandler object to the Logger object with the
                addHandler() method.
             */
            File lckFile = new File(dirPath + File.separator + "game.log.lck");

            if (lckFile.exists()) {
                lckFile.delete();
            }

            // Add the filehandler to the Logger
            IMAGE_LOGGER.addHandler(fileHandler);

        } catch (IOException e) {
            System.out.println("Error creating FileHandler");
        }
    }

    public static Logger getImageLogger() {
        return IMAGE_LOGGER;
    }
}
