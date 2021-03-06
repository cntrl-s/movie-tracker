package org.eldi.movietracker.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eldi.movietracker.model.Movie;
import org.eldi.movietracker.model.SearchResult;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MovieServiceImpl implements MovieService {

    private ObjectMapper objectMapper;

    public MovieServiceImpl(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public List<SearchResult> search(String apiQueryUrl) {
        List<SearchResult> results = new ArrayList<>();
        try {
            JsonNode jsonResults = objectMapper
                    .readTree(new URL(apiQueryUrl))
                    .get("Search");

            results = objectMapper
                    .convertValue(jsonResults, new TypeReference<List<SearchResult>>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return results;
    }

    public Movie getMovie(String url) {
        Movie movie = new Movie();
        try {
            movie = objectMapper
                    .readValue(new URL(url), Movie.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return movie;
    }
}
