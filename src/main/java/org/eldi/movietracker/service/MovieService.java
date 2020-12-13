package org.eldi.movietracker.service;

import org.eldi.movietracker.model.Movie;
import org.eldi.movietracker.model.SearchResult;

import java.util.List;

public interface MovieService {

    /**
     *
     * @param apiQueryUrl formatted url including api key.
     * @return Search results as {@link SearchResult}.
     */
    List<SearchResult> search(String apiQueryUrl);

    /**
     *
     * @param url api url with imdb id of the movie to fetch.
     * @return {@link Movie} associated with the specified imdb id.
     */
    Movie getMovie(String url);
}
