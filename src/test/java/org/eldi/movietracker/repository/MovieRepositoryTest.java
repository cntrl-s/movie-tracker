package org.eldi.movietracker.repository;

import org.eldi.movietracker.exception.DAOException;
import org.eldi.movietracker.model.Movie;
import org.eldi.movietracker.util.JDBCUtil;
import org.eldi.movietracker.util.JacksonUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

public class MovieRepositoryTest {
    private Movie movie;
    private MovieRepository repository;
    private RatingsRepository ratingsRepository;
    private static Connection connection;

    @BeforeEach
    public void setup() throws IOException {
        connection = JDBCUtil.INSTANCE.getTestConnection();
        JDBCUtil.INSTANCE.createTableIfNotExists();

        ratingsRepository = new H2RatingsRepository(connection);
        repository = new H2MovieRepository(connection, ratingsRepository);

        File json = new File("src/main/resources/test.json");
        movie = JacksonUtil.OBJECT_MAPPER.readValue(json, Movie.class);
    }

    @Test
    public void shouldFailToCreateWhenGivenNullDependency() {
        RatingsRepository ratingsRepository = null;

        assertThrows(NullPointerException.class, () -> {
            MovieRepository movieRepository = new
                    H2MovieRepository(connection, ratingsRepository);
        });
    }

    @Test
    public void crudTest() {
        Movie expected = movie;
        repository.save(expected);

        assertThrows(DAOException.class,
                () -> repository.save(movie),
                "Must fail when adding duplicate entry");

        List<Movie> movies = repository.findAllByPage(1,1);
        assertEquals(1, movies.size());

        Movie actual = movies.get(0);

        assertEquals(expected.getTitle(), actual.getTitle());
        assertEquals(expected.getYear(), actual.getYear());
        assertEquals(expected.getType(), actual.getType());
        assertEquals(expected.getImdbID(), actual.getImdbID());

        String title = movie.getTitle().substring(2, 5);
        List<Movie> findByTitle = repository.findByTitle(title);
        assertTrue(findByTitle.get(0).getTitle().contains(title));

        repository.delete(actual.getId());
        movies = repository.findAllByPage(1,1);
        assertEquals(0, movies.size(), "Size should be 0 after deleting");
    }
}
