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

/**
 * {@link business.PeakTrafficCounter} is the main logic holder. It performs peak traffic calculation.
 */
public class PeakTrafficCounter implements LogReaderListener {

    /**
     * This map holds association between concrete user (as users email) and all corresponded emails that are belongs
     * to this user. Corresponded emails are stored in the set to avoid duplicates.
     */
    private final Map<String, TreeSet<String>> dataMap = new TreeMap<String, TreeSet<String>>();

    @Override
    public void processLogRecord(final LogEntity record) {

        // Get set of the emails associated with concrete user
        TreeSet<String> usersSet = dataMap.get(record.getUser());

        // If there is none - create it
        if (usersSet == null) {
            // Apply custom comparator in order to compare users emails as strings
            usersSet = new TreeSet<String>(new EmailComparator());
            usersSet.add(record.getUser());

            dataMap.put(record.getUser(), usersSet);
        }

        // Put to the set next associated email
        usersSet.add(record.getUserReceiver());
    }

    @Override
    public void onProcessComplete() {
        // Set of the final clusters which will be printed
        final Set<String> finalResult = new HashSet<String>();
        // Set of the general data keys (collected at the 'processLogRecord' callback)
        final Set<String> dataMapSet = dataMap.keySet();
        // Single cluster as set of the
        Set<String> cluster;
        // Current user email as array of the chars
        char[] userAsChars;
        // Sub-user (the one who receives data from the user) email as array of the chars
        char[] subUserAsChars;
        // Current user email as string
        String userAsString;
        // Cluster length
        int userSetLength;
        // Result of the Boyer-Moor algorithm
        int result;

        // Iterate over set
        for (String user : dataMapSet) {
            // get current cluster
            cluster = dataMap.get(user);
            // get size of the current cluster
            userSetLength = cluster.size();
            // convert current cluster to string
            userAsString = cluster.toString();
            // convert current cluster to chars array
            userAsChars = userAsString.toCharArray();

            result = -1;

            // Iterate over sub-users
            for (String sub_user : dataMapSet) {
                // Skip if loop meet same user
                if (sub_user.equals(user)) {
                    continue;
                }
                // convert sub- cluster to chars array
                subUserAsChars = dataMap.get(sub_user).toString().toCharArray();

                // Run Boyer-Moor algorithm
                result = BoyerMoor.indexOf(userAsChars, subUserAsChars);
                if (result != -1) {
                    break;
                }
            }

            // If we pass criteria - add cluster to final set
            if (result != -1 && userSetLength >= 3) {
                finalResult.add(userAsString);
            }
        }
        for (String value : finalResult) {
            Logger.printMessage(value);
        }
    }

    /**
     * {@link business.PeakTrafficCounter.EmailComparator} is a helper class which performs email natural compare.
     */
    private class EmailComparator implements Comparator<String> {

        @Override
        public int compare(final String str1, final String str2) {
            return str1.compareTo(str2);
        }
    }
}
