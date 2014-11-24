package business;

import util.Logger;
import vo.LogEntity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created with Intellij IDEA.
 * Author: Chernyshov Yuriy - Mobile Development
 * Date: 24.11.14
 * Time: 13:45
 */
public class DefaultLogReader implements LogReader {

    /**
     * Concrete implementation of the {@link LogReaderListener}.
     */
    private LogReaderListener logReaderListener;

    /**
     * Cats Tail symbol which is used in emails
     */
    private static final String CATS_TAIL = "@";

    /**
     * Constructor.
     *
     * @param logReaderListener Concrete implementation of the {@link LogReaderListener}.
     */
    public DefaultLogReader(final LogReaderListener logReaderListener) {
        this.logReaderListener = logReaderListener;
    }

    @Override
    public void processLog(InputStreamReader reader) throws IOException {
        final BufferedReader bufferedInputStream = new BufferedReader(reader);
        String line;
        while ((line = bufferedInputStream.readLine()) != null) {
            LogEntity logEntity = parseLogLine(line);
            logReaderListener.processLogRecord(logEntity);
        }
        logReaderListener.onProcessComplete();
    }

    public LogEntity parseLogLine(final String logLine) {
        //Logger.printMessage("line:" + logLine);
        final String user = extractUser(logLine);
        final String userReceiver = extractUserReceiver(logLine);
        final String date = extractDate(logLine);
        return LogEntity.getInstance(date, user, userReceiver);
    }

    /**
     * This method extracts first email from the single log line.
     *
     * @param logLine Log line.
     * @return Email of the user.
     */
    protected static String extractUser(final String logLine) {
        // Find first cat tail index
        int firstCatsTailSignIndex = logLine.indexOf(CATS_TAIL);
        // Find first white space form the left of the first cat tail
        int firstEmailStartIndex = logLine.lastIndexOf(" ", firstCatsTailSignIndex) + 1;
        // Find first white space from the right of the first cat tail
        int firstEmailEndIndex = logLine.indexOf(" ", firstEmailStartIndex);
        // Extract email using first and last indexes
        return logLine.substring(firstEmailStartIndex, firstEmailEndIndex);
    }

    /**
     * This method extracts second email from the single log line.
     *
     * @param logLine Log line.
     * @return Email of the user who received data from the main user.
     */
    protected static String extractUserReceiver(final String logLine) {
        // Find last cat tail index
        int firstCatsTailSignIndex = logLine.lastIndexOf(CATS_TAIL);
        // Find first white space form the left of the last cat tail
        int firstEmailStartIndex = logLine.lastIndexOf(" ", firstCatsTailSignIndex) + 1;
        // Extract email using first and last indexes
        return logLine.substring(firstEmailStartIndex, logLine.length());
    }

    /**
     * This method extracts the date from the single log line.
     *
     * @param logLine Log line.
     * @return Date as string.
     */
    protected static String extractDate(final String logLine) {
        // Find first cat tail index
        int firstCatsTailSignIndex = logLine.indexOf(CATS_TAIL);
        // Find first white space form the left of the first cat tail
        int firstEmailStartIndex = logLine.lastIndexOf(" ", firstCatsTailSignIndex) + 1;
        // Extract email using first and last indexes
        return logLine.substring(0, firstEmailStartIndex).trim();
    }
}
