package business;

import org.junit.Test;
import vo.LogEntity;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Created with IntelliJ IDEA.
 * User: Yuriy Chernyshov
 * Date: 11/24/14
 * Time: 11:21 PM
 */
public class DefaultLogReaderTest {

    private static final String DATE = "Thu Dec 11 17:53:12 PST 2008";
    private static final String USER = "f@facebook.com";
    private static final String SUB_USER = "e@facebook.com";
    private static final String LOG_LINE = DATE + "    " + USER + "    " + SUB_USER;

    @Test
    public void parseUser() {
        assertThat("User should be same as original", DefaultLogReader.extractUser(LOG_LINE), is(USER));
    }

    @Test
    public void parseSubUser() {
        assertThat("Sub-User should be same as original", DefaultLogReader.extractUserReceiver(LOG_LINE), is(SUB_USER));
    }

    @Test
    public void parseDate() {
        assertThat("Date should be same as original", DefaultLogReader.extractDate(LOG_LINE), is(DATE));
    }

    @Test
    public void parseLogLine() {
        final DefaultLogReader reader = new DefaultLogReader(mock(LogReaderListener.class));
        final LogEntity logEntity = reader.parseLogLine(LOG_LINE);

        assertThat("Instance should be created", logEntity, notNullValue());
        assertThat("User should be same as original", logEntity.getUser(), is(USER));
        assertThat("Sub-User should be same as original", logEntity.getUserReceiver(), is(SUB_USER));
        assertThat("User should be same as original", logEntity.getDate(), is(DATE));
    }
}
