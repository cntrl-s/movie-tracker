package org.eldi.movietracker.movie;

import java.util.List;

public interface RatingsRepository {

    void save(List<Rating> ratings, int movieId);

    List<Rating> find(int movieId);

    void delete(int movieId);
}
