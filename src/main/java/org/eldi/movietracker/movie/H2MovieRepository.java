package org.eldi.movietracker.movie;

import java.util.List;
import java.util.Optional;

public class H2MovieRepository implements MovieRepository{
    public void save(Movie movie) {

    }

    public Optional<Movie> findByTitle(String title) {
        return Optional.empty();
    }

    public void update(String title) {

    }

    public List<Movie> findAll() {
        return null;
    }

    public void delete(String title) {

    }
}
