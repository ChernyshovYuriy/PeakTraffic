package business;

import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created with Intellij IDEA.
 * Author: Chernyshov Yuriy - Mobile Development
 * Date: 24.11.14
 * Time: 13:43
 */

/**
 * {@link LogReader} is an interface which allows to perform operations over log file, such that read.
 * It can be extend to any input data format to read, starts from Streams and up to JSON files.
 */
public interface LogReader {

    /**
     * Process provided log file as {@link java.io.InputStreamReader}.
     *
     * @param reader Instance of the {@link java.io.InputStreamReader}.
     * @throws IOException
     */
    public void processLog(final InputStreamReader reader) throws IOException;
}