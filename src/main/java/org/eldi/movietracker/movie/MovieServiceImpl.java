package org.eldi.movietracker.movie;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import org.eldi.movietracker.util.APIQueries;
import org.eldi.movietracker.util.JacksonUtil;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MovieServiceImpl implements MovieService {

    public List<SearchResult> search(String searchQuery) {
        String apiQueryUrl = APIQueries.getSearchQuery(searchQuery);
        List<SearchResult> results = new ArrayList<>();
        try {
            JsonNode jsonResults = JacksonUtil.OBJECT_MAPPER
                    .readTree(new URL(apiQueryUrl))
                    .get("Search");

            results = JacksonUtil.OBJECT_MAPPER
                    .convertValue(jsonResults, new TypeReference<List<SearchResult>>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return results;
    }
}
