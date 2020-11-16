package org.eldi.movietracker.util;

import java.util.prefs.Preferences;

public class UserAPIKeyPersistUtil {
    private static final String PREFS_API_KEY = "api_key";
    private static Preferences preferences = Preferences.userNodeForPackage(Preferences.class);

    static String getUserAPIKey() {
        String userAPIKeyValue = preferences.get(PREFS_API_KEY, "default");
        return userAPIKeyValue;
    }

    // TODO
    static void askUserForKey() {
    }

    public static boolean keyIsValid(String userAPIKeyValue) {
        final int validAPIKeyLength = 8;

        if (userAPIKeyValue.length() < validAPIKeyLength
                || userAPIKeyValue.length() > validAPIKeyLength
                || userAPIKeyValue.equals("default")) {
            return false;
        }
        return true;
    }
}
