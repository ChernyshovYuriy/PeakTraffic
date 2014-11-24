package vo;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;

/**
 * Created with IntelliJ IDEA.
 * User: Yuriy Chernyshov
 * Date: 11/24/14
 * Time: 10:09 PM
 */
public class LogEntityTest {

    private final static String DATE = "Thu Dec 11 17:53:01 PST 2008";
    private final static String USER = "a@facebook.com";
    private final static String SUB_USER = "b@facebook.com";

    @Test
    public void instanceWithFactoryMethod() {
        final LogEntity logEntity = LogEntity.getInstance(DATE, USER, SUB_USER);

        assertThat("Instance should be created", logEntity, notNullValue());
    }

    @Test
    public void instanceWithFactoryMethodShouldReturnCorrectValuesWithGetters() {
        final LogEntity logEntity = LogEntity.getInstance(DATE, USER, SUB_USER);

        assertThat("Date should be as original", logEntity.getDate(), is(DATE));
        assertThat("User should be as original", logEntity.getUser(), is(USER));
        assertThat("Sub-User should be as original", logEntity.getUserReceiver(), is(SUB_USER));
    }
}
