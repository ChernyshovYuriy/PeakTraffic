package business;

import util.Logger;
import vo.LogEntity;

import java.util.*;

/**
 * Created with Intellij IDEA.
 * Author: Chernyshov Yuriy - Mobile Development
 * Date: 24.11.14
 * Time: 14:13
 */
public class PeakTrafficCounter implements LogReaderListener {

    /**
     * This map holds association between concrete user (as users email) and all corresponded emails that are belongs
     * to this user. Corresponded emails are stored in the set to avoid duplicates.
     */
    private final Map<String, Set<String>> dataMap = new TreeMap<String, Set<String>>();

    @Override
    public void processLogRecord(final LogEntity record) {

        // Get set of the emails associated with concrete user
        Set<String> usersSet = dataMap.get(record.getUser());

        // If there is none - create it
        if (usersSet == null) {
            usersSet = new TreeSet<String>();

            dataMap.put(record.getUser(), usersSet);
        }

        // Put to the set next associated email
        usersSet.add(record.getUserReceiver());
    }

    @Override
    public void onProcessComplete() {
        for (String user : dataMap.keySet()) {
            Logger.printMessage("User:" + user + " Data:" + dataMap.get(user));
        }
    }
}