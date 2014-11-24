package vo; /**
 * Created with Intellij IDEA.
 * Author: Chernyshov Yuriy - Mobile Development
 * Date: 24.11.14
 * Time: 13:26
 */

/**
 * This is value object of the single log line.
 */
public class LogEntity {

    /**
     * Date of the log record.
     */
    private String date;

    /**
     * User who perform action over network.
     */
    private String user;

    /**
     * User who receives data from the {@link #user}.
     */
    private String userReceiver;

    /**
     * Default empty constructor
     */
    private LogEntity() { }

    /**
     * Constructor.
     *
     * @param date         Date of the log message.
     * @param user         User who perform action over network.
     * @param userReceiver User who receives data from the {@link #user}.
     */
    private LogEntity(final String date, final String user, final String userReceiver) {
        this.date = date;
        this.user = user;
        this.userReceiver = userReceiver;
    }

    /**
     * @return Date of the log record.
     */
    public String getDate() {
        return date;
    }

    /**
     * @return User who perform action over network.
     */
    public String getUser() {
        return user;
    }

    /**
     * @return User who receives data from the {@link #user}.
     */
    public String getUserReceiver() {
        return userReceiver;
    }

    /**
     * Factory method to return instance of the {@link LogEntity}.
     *
     * @param date         Date of the log message.
     * @param user         User who perform action over network.
     * @param userReceiver User who receives data from the {@link #user}.
     * @return Instance of the {@link LogEntity}.
     */
    public static LogEntity getInstance(final String date, final String user, final String userReceiver) {
        return new LogEntity(date, user, userReceiver);
    }
}