package es.upm.fi.cloud.YellowTaxiTrip.utils;

import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * @author Wenqi Jiang & Zhuo Cheng
 */
public class FileUtils {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(FileUtils.class);

    private FileUtils() {
    }

    /**
     * Check if the file exists
     *
     * @param path the path of the file
     * @return the file exists or not
     */
    public static boolean isExist(String path) {
        File file = new File(path);
        return file.exists();
    }

    /**
     * recreate the file
     *
     * @param path the path of the file
     */
    public static void recreateFile(String path) {
        try {
            File file = new File(path);
            if (file.exists()) {
                file.delete();
            }
            file.getParentFile().mkdirs();
            file.createNewFile();
        } catch (Exception e) {
            LOGGER.error("Failed to create file:{} ", path);
        }
    }


}
