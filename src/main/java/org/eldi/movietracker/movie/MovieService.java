package org.eldi.movietracker.movie;

import java.util.List;
import java.util.Optional;

public interface MovieService {
    public Optional<List<Movie>> search(String searchQuery);
}
