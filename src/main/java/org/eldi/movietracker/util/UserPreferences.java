package org.eldi.movietracker.util;

import java.util.prefs.Preferences;

public class UserPreferences {
    private static final String API_KEY = "api_key";
    private static Preferences preferences = Preferences.userNodeForPackage(Preferences.class);

    static String getAPIKey() {
        String userAPIKeyValue = preferences.get(API_KEY, "default");
        return userAPIKeyValue;
    }

    static void setAPIKey(String apiKey) {
        preferences.put(API_KEY, apiKey);
    }

    public static boolean keyIsValid(String apiKey) {
        final int validAPIKeyLength = 8;

        if (apiKey.length() < validAPIKeyLength
                || apiKey.length() > validAPIKeyLength) {
            return false;
        }
        return true;
    }
}
