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
        Logger.printMessage("line:" + logLine);
        final String date = "";
        final String user = "";
        final String userReceiver = "";

        // TODO : Parse data here

        return LogEntity.getInstance(date, user, userReceiver);
    }
}