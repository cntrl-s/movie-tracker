package org.eldi.movietracker.repository;

import org.eldi.movietracker.model.Movie;

import java.util.List;
import java.util.Optional;

public interface MovieRepository {

    void save(Movie movie);

    Optional<Movie> find(int id);

    /*
     * @param title name of the movie to search.
     * @return {@link Movie} or {@code null }if not found
     */
    Optional<Movie> findByTitle(String title);

    void update(String title);

    List<Movie> findAll();

    void delete(int id);
}