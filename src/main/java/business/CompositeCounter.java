package business;

import vo.LogEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created with Intellij IDEA.
 * Author: Chernyshov Yuriy - Mobile Development
 * Date: 24.11.14
 * Time: 14:02
 */
public class CompositeCounter implements LogReaderListener {

    /**
     * Collection of the lor reader listeners.
     * Each listener may have it's own subscriber in order to perform many operations over single log line.
     */
    final List<LogReaderListener> logReaderListeners = new ArrayList<LogReaderListener>();

    /**
     * Constructor.
     *
     * @param listeners Collection of the listeners.
     */
    public CompositeCounter(final LogReaderListener... listeners) {
        Collections.addAll(this.logReaderListeners, listeners);
    }

    @Override
    public void processLogRecord(final LogEntity record) {
        for (LogReaderListener listener : logReaderListeners) {
            listener.processLogRecord(record);
        }
    }

    @Override
    public void onProcessComplete() {
        for (LogReaderListener listener : logReaderListeners) {
            listener.onProcessComplete();
        }
    }
}