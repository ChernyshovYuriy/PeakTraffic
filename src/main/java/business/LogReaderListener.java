package business; /**
 * Created with Intellij IDEA.
 * Author: Chernyshov Yuriy - Mobile Development
 * Date: 24.11.14
 * Time: 13:19
 */

import vo.LogEntity;

/**
 * {@link LogReaderListener} is an interface which allows to perform callback monitoring of the actions executed over
 * log file.
 */
public interface LogReaderListener {

    /**
     * This method is used when processing over log file is necessary to perform.
     *
     * @param record Instance of the {@link vo.LogEntity}.
     */
    public void processLogRecord(LogEntity record);

    /**
     * This method is used when processing over log file is completed.
     */
    public void onProcessComplete();
}
