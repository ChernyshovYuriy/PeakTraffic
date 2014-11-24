package util;

/**
 * Created with Intellij IDEA.
 * Author: Chernyshov Yuriy - Mobile Development
 * Date: 24.11.14
 * Time: 12:55
 */

/**
 * This is a wrapper class for the system standard output in order to provide flexible messages printing.
 */
public class Logger {

    private static final String LOG_TAG = "PeakTraffic";

    /**
     * Constructor.
     */
    private Logger() { }

    /**
     * Print log info message.
     *
     * @param message String message.
     */
    public static void printMessage(final String message) {
        System.out.println(LOG_TAG + " [I]: " + message);
    }

    /**
     * Print log error message.
     *
     * @param message String message.
     */
    public static void printError(final String message) {
        System.out.println(LOG_TAG + " [E]: " + message);
    }
}