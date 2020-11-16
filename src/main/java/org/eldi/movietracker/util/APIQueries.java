package org.eldi.movietracker.util;

public class APIQueries {
    private static final String BASE_URL = "https://www.omdbapi.com";
    private static final String API_KEY = "&apikey=";

    public static String getSearchQuery(String searchQuery) {
        // format query
        String formattedSearchQuery = formatQuery(searchQuery);
        return BASE_URL + "/?s=" + formattedSearchQuery + API_KEY + UserAPIKeyPersistUtil.getUserAPIKey();
    }

    public static String getImdbSearchQuery(String imdbID) {
        // format query
        String formattedSearchQuery = "";
        return BASE_URL + "/?i=" + formattedSearchQuery;
    }

    public static String getTitleSearchQuery(String title) {
        // format query
        String formattedSearchQuery = "";
        return BASE_URL + "/?t=" + formattedSearchQuery;
    }

    private static String formatQuery(String query) {
        return query.replaceAll(" ", "+");
    }
}
