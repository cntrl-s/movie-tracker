package org.eldi.movietracker.util;

import org.eldi.movietracker.exception.InvalidAPIKeyException;

import java.util.prefs.Preferences;

public class UserPreferences {
    private static final String API_KEY = "api_key";
    private static Preferences preferences = Preferences.userNodeForPackage(Preferences.class);

    /**
     *
     * @return API key if saved or "default"
     */
    public String getAPIKey() {
        String userAPIKeyValue = preferences.get(API_KEY, "default");
        return userAPIKeyValue;
    }

    public void setAPIKey(String apiKey) throws InvalidAPIKeyException {
        if (keyIsValid(apiKey)) {
            preferences.put(API_KEY, apiKey);
        } else {
            throw new InvalidAPIKeyException("API Key is not valid");
        }
    }

    private boolean keyIsValid(String apiKey) {
        final int validAPIKeyLength = 8;

        if (apiKey.length() < validAPIKeyLength
                || apiKey.length() > validAPIKeyLength) {
            return false;
        }
        return true;
    }
}
