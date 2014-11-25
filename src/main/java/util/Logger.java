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

    @SuppressWarnings("unused")
    private static final String LOG_TAG = "PeakTraffic";

    /**
     * This flag hides all unnecessary logging from std out when perform testing.
     */
    private static boolean isTestMode = false;

    /**
     * Constructor.
     */
    private Logger() { }

    /**
     * @return True if testing is in progress, False - otherwise
     */
    public static boolean isIsTestMode() {
        return isTestMode;
    }

    /**
     * Set True if testing is in progress, False - otherwise
     * @param isTestMode True or False.
     */
    public static void setIsTestMode(boolean isTestMode) {
        Logger.isTestMode = isTestMode;
    }

    /**
     * Print log info message.
     *
     * @param message String message.
     */
    public static void printMessage(final String message) {
        //System.out.println(LOG_TAG + " [I]: " + message);
        System.out.println(message);
    }

    /**
     * Print log error message.
     *
     * @param message String message.
     */
    public static void printError(final String message) {
        //System.out.println(LOG_TAG + " [E]: " + message);
        System.out.println(message);
    }
}