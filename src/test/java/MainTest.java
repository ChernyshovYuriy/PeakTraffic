import business.DefaultLogReader;
import business.LogReader;
import business.LogReaderListener;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import vo.LogEntity;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

    @Test
    public void logReaderListenersMethodCalls() throws IOException {
        final LogReaderListener compositeListener = mock(LogReaderListener.class);
        final LogReader logReader = new DefaultLogReader(compositeListener);

        InputStream inputStream = this.getClass().getResourceAsStream("/input_a.txt");
        final InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

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
    public void processVeryLargeFile() throws IOException {
        final LogReaderListener compositeListener = mock(LogReaderListener.class);
        final LogReader logReader = new DefaultLogReader(compositeListener);

        InputStream inputStream = this.getClass().getResourceAsStream("/input_large.txt");
        final InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

        logReader.processLog(inputStreamReader);

        final ArgumentCaptor<LogEntity> logEntityArgumentCaptor = ArgumentCaptor.forClass(LogEntity.class);

        verify(compositeListener, times(INPUT_LARGE_LINES_NUMBER)).processLogRecord(logEntityArgumentCaptor.capture());
        verify(compositeListener, times(1)).onProcessComplete();
    }
}
