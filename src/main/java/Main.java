import business.*;
import util.Logger;

import java.io.*;

/**
 * Created with Intellij IDEA.
 * Author: Chernyshov Yuriy - Mobile Development
 * Date: 24.11.14
 * Time: 12:51
 */
public class Main {

    public static void main(final String[] args) {
        if (args == null) {
            exitApp();
            return;
        }
        if (args.length == 0) {
            exitApp();
            return;
        }
        // Extract file path
        final String filePath = args[0];
        Logger.printMessage("File path:" + filePath);

        doProcess(filePath);
    }

    /**
     * Process file by the provided path.
     *
     * @param filePath Path to the file
     */
    private static void doProcess(final String filePath) {
        // Input stream that we need to pass to the log reader
        InputStream inputStream = null;
        // Log file itself
        final File file = new File(filePath);

        try {
            // Stream a log file
            inputStream = new FileInputStream(file);

            // Read a log file, transform it to the input stream reader
            final InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

            // Implement log reader listener in order to do peak traffic logic
            final LogReaderListener peakTrafficCounter = new PeakTrafficCounter();

            // Composite pattern allows to combine several implementations of the same interface in order to
            // perform many operations over single entity
            final LogReaderListener compositeListener = new CompositeCounter(peakTrafficCounter);

            // Instantiate implementation of the log reader interface.
            final LogReader logReader = new DefaultLogReader(compositeListener);

            // Finally - process log file
            logReader.processLog(inputStreamReader);

        } catch (IOException e) {
            Logger.printError("Can not processLog Log: " + e.getMessage());
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    Logger.printError(e.toString());
                }
            }
        }
    }

    /**
     * Exit application.
     */
    private static void exitApp() {
        // TODO : Probably it is necessary to pass here exit code, but this is not an aim of this project.

        Logger.printMessage("No input arguments detected. Exit.");
        System.exit(0);
    }
}