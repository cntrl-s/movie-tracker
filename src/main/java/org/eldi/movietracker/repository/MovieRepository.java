package org.eldi.movietracker.repository;

import org.eldi.movietracker.model.Movie;

import java.util.List;
import java.util.Optional;

public interface MovieRepository {

    void save(Movie movie);

    Optional<Movie> find(int id);

    /**
     * @param title name of the movie to search.
     * @return {@link List} of {@link Movie} that match the title
     */
    List<Movie> findByTitle(String title);

    void update(Movie movie);

    /**
     *
     * @param pageSize number of records to return
     * @param currentPage current page number
     * @return {@link List} of {@link Movie} with {@code pageSize} number of values
     */
    List<Movie> findAllByPage(int pageSize, int currentPage);

    void delete(int id);

    int getRecordsSize();
}