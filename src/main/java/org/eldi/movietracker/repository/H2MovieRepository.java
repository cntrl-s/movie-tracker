package org.eldi.movietracker.repository;

import org.eldi.movietracker.exception.DAOException;
import org.eldi.movietracker.model.Movie;
import org.eldi.movietracker.model.Type;
import org.eldi.movietracker.util.SQLUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

public class H2MovieRepository implements MovieRepository {

    private Connection connection;
    private RatingsRepository ratingsRepository;

    public H2MovieRepository(Connection connection, RatingsRepository ratingsRepository) {
        this.connection = connection;
        this.ratingsRepository = ratingsRepository;
    }

    public void save(Movie movie) {
        String sql = SQLUtil.INSERT_MOVIE_QUERY;

        Object[] values = {
                movie.getTitle(),
                movie.getYear(),
                movie.getRated(),
                java.sql.Date.valueOf(movie.getReleased()),
                movie.getRuntime(),
                movie.getGenre(),
                movie.getDirector(),
                movie.getWriter(),
                movie.getActors(),
                movie.getPlot(),
                movie.getLanguage(),
                movie.getCountry(),
                movie.getAwards(),
                movie.getPoster(),
                movie.getMetaScore(),
                movie.getImdbRating(),
                movie.getImdbVotes(),
                movie.getImdbID(),
                movie.getType().name(),
        };

        try (PreparedStatement statement = setStatement(connection, sql, true, values)) {

            int affectedRows = statement.executeUpdate();

            if (affectedRows < 1) {
                throw new DAOException("Failed to save " + movie.getType() + movie.getTitle());
            }

            System.out.println("Rows affected - " + affectedRows + ", for " + movie.getType() + " '" + movie.getTitle() + "'");

            ResultSet keys = statement.getGeneratedKeys();

            if (keys.next()) {
                movie.setId(keys.getInt(1));

                System.out.println("Id for movie '" + movie.getTitle() + "' set as - " + movie.getId());
            } else {
                throw new DAOException("Failed to get generated keys for " + movie.getType() + " " + movie.getTitle());
            }

            ratingsRepository.save(movie.getRatings(), movie.getId());
        } catch (SQLException e) {
            throw new DAOException("Failed to save " + movie.getType() + " " + movie.getTitle(), e);
        }
    }

    public Optional<Movie> findByTitle(String title) {
        String sql = SQLUtil.FIND_BY_TITLE_QUERY;
        Movie movie = null;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, title);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                movie = mapResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException("Failed to find " + title, e);
        }
        return Optional.ofNullable(movie);
    }

    // UPDATE statement
    public void update(String title) {

    }

    public List<Movie> findAll() {
        return null;
    }

    public void delete(int id) {
    }

    // TODO move to new file
    /**
     * @param connection       {@link Connection} to create {@link PreparedStatement}.
     * @param sql              SQL Query to execute.
     * @param getGeneratedKeys constructs statement to return generated keys if {@code true}.
     * @param values           values to be set on the {@link PreparedStatement}.
     * @return {@link PreparedStatement} set with given parameters or empty {@link Optional} instance.
     */
    static PreparedStatement setStatement(Connection connection, String sql,
                                          boolean getGeneratedKeys, Object[] values) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql,
                    getGeneratedKeys ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS);

            for (int i = 0; i < values.length; i++) {
                statement.setObject(i + 1, values[i]);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return statement;
    }

    Movie mapResultSet(ResultSet rs) throws SQLException {
        Movie movie = new Movie();

        movie.setId(rs.getInt("id"));
        movie.setTitle(rs.getString("title"));
        movie.setYear(rs.getString("year"));
        movie.setRated(rs.getString("rated"));
        movie.setReleased(rs.getDate("released").toLocalDate());
        movie.setRuntime(rs.getString("runtime"));
        movie.setGenre(rs.getString("genre"));
        movie.setDirector(rs.getString("director"));
        movie.setWriter(rs.getString("writer"));
        movie.setActors(rs.getString("actors"));
        movie.setPlot(rs.getString("plot"));
        movie.setLanguage(rs.getString("language"));
        movie.setCountry(rs.getString("country"));
        movie.setAwards(rs.getString("awards"));
        movie.setPoster(rs.getString("poster"));
        movie.setRatings(ratingsRepository.find(movie.getId()));
        movie.setMetaScore(rs.getByte("metascore"));
        movie.setImdbRating(rs.getBigDecimal("imdb_rating"));
        movie.setImdbVotes(rs.getLong("imdb_votes"));
        movie.setImdbID(rs.getString("imdb_id"));
        movie.setType(Type.valueOf(rs.getString("type")));

        return movie;
    }
}
