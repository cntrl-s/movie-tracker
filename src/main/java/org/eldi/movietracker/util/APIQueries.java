package org.eldi.movietracker.util;

public class APIQueries {
    public static final String BASE_URL = "https://www.omdbapi.com/";

    public static String getSearchQuery(String searchQuery) {
        // format query
        String formattedSearchQuery = "";
        return BASE_URL + "/?s=" + formattedSearchQuery;// + apikey
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
}
