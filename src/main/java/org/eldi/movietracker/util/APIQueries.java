package org.eldi.movietracker.util;

public class APIQueries {

    private UserPreferences userPreferences;

    public APIQueries(UserPreferences userPreferences) {
        this.userPreferences = userPreferences;
    }

    private static final String BASE_URL = "https://www.omdbapi.com/";
    private static final String API_PARAMETER = "&apikey=";

    public String getSearchQuery(String searchQuery) {
        String formattedSearchQuery = formatQuery(searchQuery);
        return BASE_URL + "?s=" + formattedSearchQuery + getApiParameterWithKey();
    }

    public String getImdbSearchQuery(String imdbID) {
        return BASE_URL + "?i=" + imdbID + getApiParameterWithKey();
    }

    private String getApiParameterWithKey() {
        return API_PARAMETER + userPreferences.getAPIKey();
    }

    private String formatQuery(String query) {
        return query.replaceAll(" ", "+");
    }
}
