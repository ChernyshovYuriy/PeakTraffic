import business.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import util.Logger;
import vo.LogEntity;

import java.io.*;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

/**
 * Created with IntelliJ IDEA.
 * User: Yuriy Chernyshov
 * Date: 11/24/14
 * Time: 10:19 PM
 */
public class MainTest {

    private static final int INPUT_A_LINES_NUMBER = 3;
    private static final int INPUT_LARGE_LINES_NUMBER = 100000;
    private static final String INPUT_A_DATA = "Thu Dec 11 17:53:01 PST 2008";
    private static final String INPUT_A_USER = "a@facebook.com";
    private static final String INPUT_A_SUB_USER = "b@facebook.com";

    private InputStream inputStream;
    private InputStreamReader inputStreamReader;
    private LogReader logReader;
    private LogReaderListener compositeListener;

    @Before
    public void setUp() throws Exception {
        Logger.setIsTestMode(true);
    }

    @After
    public void tearDown() throws Exception {
        Logger.setIsTestMode(false);
    }

    @Test
    public void logReaderListenersMethodCalls() throws IOException {
        compositeListener = mock(LogReaderListener.class);
        logReader = new DefaultLogReader(compositeListener);
        inputStream = this.getClass().getResourceAsStream("/input_all_same.txt");
        inputStreamReader = new InputStreamReader(inputStream);

        logReader.processLog(inputStreamReader);

        final ArgumentCaptor<LogEntity> logEntityArgumentCaptor = ArgumentCaptor.forClass(LogEntity.class);

        verify(compositeListener, times(INPUT_A_LINES_NUMBER)).processLogRecord(logEntityArgumentCaptor.capture());
        verify(compositeListener, times(1)).onProcessComplete();

        final List<LogEntity> capturedValues = logEntityArgumentCaptor.getAllValues();
        assertThat("Should be 3 lines of the logs", capturedValues.size(), is(INPUT_A_LINES_NUMBER));

        for (LogEntity logEntity : capturedValues) {
            assertThat("Date should be same as original", logEntity.getDate(), is(INPUT_A_DATA));
            assertThat("User EMail should be same as original", logEntity.getUser(), is(INPUT_A_USER));
            assertThat("Sub-user EMail should be same as original", logEntity.getUserReceiver(), is(INPUT_A_SUB_USER));
        }
    }

    @Test
    public void readLargeFileSimpleWithoutTimeout() throws IOException {
        compositeListener = mock(LogReaderListener.class);
        logReader = new DefaultLogReader(compositeListener);

        inputStream = this.getClass().getResourceAsStream("/input_large_simple.txt");
        inputStreamReader = new InputStreamReader(inputStream);

        logReader.processLog(inputStreamReader);

        final ArgumentCaptor<LogEntity> logEntityArgumentCaptor = ArgumentCaptor.forClass(LogEntity.class);

        verify(compositeListener, times(INPUT_LARGE_LINES_NUMBER)).processLogRecord(logEntityArgumentCaptor.capture());
        verify(compositeListener, times(1)).onProcessComplete();
    }

    @Test
    public void readLargeFileComplexWithoutTimeout() throws IOException {
        compositeListener = mock(LogReaderListener.class);
        logReader = new DefaultLogReader(compositeListener);

        inputStream = this.getClass().getResourceAsStream("/input_large.txt");
        inputStreamReader = new InputStreamReader(inputStream);

        logReader.processLog(inputStreamReader);

        final ArgumentCaptor<LogEntity> logEntityArgumentCaptor = ArgumentCaptor.forClass(LogEntity.class);

        verify(compositeListener, times(INPUT_LARGE_LINES_NUMBER)).processLogRecord(logEntityArgumentCaptor.capture());
        verify(compositeListener, times(1)).onProcessComplete();
    }

    @Test
    public void inputTxtTwoClustersShouldPrintCorrectResult() throws IOException {

        final String expected = "a@facebook.com, b@facebook.com, c@facebook.com\n" +
                "d@facebook.com, e@facebook.com, f@facebook.com\n";
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        inputStream = this.getClass().getResourceAsStream("/input.txt");
        // Read a log file, transform it to the input stream reader
        inputStreamReader = new InputStreamReader(inputStream);

        // Implement log reader listener in order to do peak traffic logic
        final LogReaderListener peakTrafficCounter = new PeakTrafficCounter();

        // Composite pattern allows to combine several implementations of the same interface in order to
        // perform many operations over single entity
        compositeListener = new CompositeCounter(peakTrafficCounter);

        // Instantiate implementation of the log reader interface.
        logReader = new DefaultLogReader(compositeListener);

        // Finally - process log file
        logReader.processLog(inputStreamReader);

        assertThat(outContent.toString(), is(expected));
    }

    @Test
    public void inputTxtLessThenMinClusterUserCountShouldPrintCorrectResult() throws IOException {

        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        inputStream = this.getClass().getResourceAsStream("/input_less_then_min.txt");
        // Read a log file, transform it to the input stream reader
        inputStreamReader = new InputStreamReader(inputStream);

        // Implement log reader listener in order to do peak traffic logic
        final LogReaderListener peakTrafficCounter = new PeakTrafficCounter();

        // Composite pattern allows to combine several implementations of the same interface in order to
        // perform many operations over single entity
        compositeListener = new CompositeCounter(peakTrafficCounter);

        // Instantiate implementation of the log reader interface.
        logReader = new DefaultLogReader(compositeListener);

        // Finally - process log file
        logReader.processLog(inputStreamReader);

        assertThat(outContent.toString(), is(""));
    }
}
