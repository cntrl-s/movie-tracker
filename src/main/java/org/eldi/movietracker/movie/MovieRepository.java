package org.eldi.movietracker.movie;

import java.util.List;
import java.util.Optional;

public interface MovieRepository {
    void save(Movie movie);
    Optional<Movie> findByTitle(String title);
    void update(String title);
    List<Movie> findAll();
    void delete(String title);
}