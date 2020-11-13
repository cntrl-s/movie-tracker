package org.eldi.movietracker.movie;

import java.util.List;

public interface MovieService {
    public List<SearchResult> search(String searchQuery);
}
