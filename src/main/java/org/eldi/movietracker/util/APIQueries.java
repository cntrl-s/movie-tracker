package org.eldi.movietracker.util;

public class APIQueries {
    private static final String BASE_URL = "https://www.omdbapi.com/";
    private static final String API_PARAMETER = "&apikey=";

    public static String getSearchQuery(String searchQuery) {
        String formattedSearchQuery = formatQuery(searchQuery);
        return BASE_URL + "?s=" + formattedSearchQuery + getApiParameterWithKey();
    }

    public static String getImdbSearchQuery(String imdbID) {
        return BASE_URL + "?i=" + imdbID + getApiParameterWithKey();
    }

    // todo delete?
    public static String getTitleSearchQuery(String title) {
        // format query
        String formattedSearchQuery = "";
        return BASE_URL + "?t=" + formattedSearchQuery;
    }

    private static String getApiParameterWithKey() {
        return API_PARAMETER + UserPreferences.getAPIKey();
    }

    private static String formatQuery(String query) {
        return query.replaceAll(" ", "+");
    }
}
