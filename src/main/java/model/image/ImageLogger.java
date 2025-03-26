package model.image;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class ImageLogger {

    public static final Logger IMAGE_LOGGER = Logger.getLogger("ImageLogger");

    static {
        String dirPath = System.getProperty("user.dir") + File.separator + "logs";
        File file = new File(dirPath);

        if (!file.exists()) {
            file.mkdir();
        }

        String logPath = dirPath + File.separator + "game.log";
        try {
            FileHandler fileHandler = new FileHandler(logPath, 5000000, 1,  true);
            fileHandler.setFormatter(new SimpleFormatter());

            File lckFile = new File(dirPath + File.separator + "game.log.lck");

            if (lckFile.exists()) {
                lckFile.delete();
            }

            IMAGE_LOGGER.addHandler(fileHandler);

        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static Logger getImageLogger() {
        return IMAGE_LOGGER;
    }
}
