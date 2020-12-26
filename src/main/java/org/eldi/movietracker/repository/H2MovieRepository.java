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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class H2MovieRepository implements MovieRepository {

    private Connection connection;
    private RatingsRepository ratingsRepository;

    public H2MovieRepository(Connection connection, RatingsRepository ratingsRepository) {
        this.connection = connection;

        this.ratingsRepository = Objects.requireNonNull(ratingsRepository,
                "RatingsRepository must not be null");
    }

    public void save(Movie movie) {
        String sql = SQLUtil.INSERT_MOVIE_QUERY;

        Object[] values = extractValues(movie);

        try (PreparedStatement statement = statementBuilder(connection, sql, true, values)) {

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

    // TODO polymorphic find
    public Optional<Movie> find(int id) {
        Movie movie = null;

        String sql = SQLUtil.FIND_BY_ID_QUERY;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                movie = mapResultSet(resultSet);
            }

        } catch (SQLException e) {
            throw new DAOException("Failed to fetch movie with id " + id, e);
        }

        return Optional.ofNullable(movie);
    }

    public List<Movie> findByTitle(String title) {
        String sql = SQLUtil.FIND_BY_TITLE_QUERY;
        List<Movie> movies = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, "%" + title + "%");

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Movie movie = mapResultSet(resultSet);
                movies.add(movie);
            }
        } catch (SQLException e) {
            throw new DAOException("Failed to find " + title, e);
        }
        return movies;
    }

    public void update(Movie movie) {
        String sql = SQLUtil.UPDATE_MOVIE_QUERY;

        Object[] values = extractValues(movie);

        PreparedStatement statement = statementBuilder(connection, sql, true, values);
        try {
            statement.setObject(values.length + 1, movie.getId());// sql where id = ?

            int i = statement.executeUpdate();
            if (i < 1) {
                throw new DAOException("Failed to update " + movie.getType() + " '" + movie.getTitle() + "'");
            }
            System.out.println("Updated " + movie.getType() + " '" + movie.getTitle() + "'");
        } catch (SQLException e) {
            throw new DAOException("Failed to update " + movie.getType() + " ' " + movie.getTitle() + " '", e);
        }
    }

    public List<Movie> findAllByPage(int pageSize, int currentPage) {
        List<Movie> movies = new ArrayList<>(pageSize);
        int offset = (currentPage - 1) * pageSize;
        String sql = String.format(SQLUtil.FIND_MOVIES_QUERY, pageSize, offset);

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Movie movie = mapResultSet(resultSet);
                movies.add(movie);
            }
        } catch (SQLException e) {
            throw new DAOException("Failed to fetch movies for page - " + currentPage, e);
        }

        return movies;
    }

    public void delete(int id) {
        String sql = SQLUtil.DELETE_MOVIE_QUERY;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            ratingsRepository.delete(id);

            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new DAOException("Failed to delete movie id " + id);
            }

            System.out.println("Deleted movie id - " + id);
        } catch (SQLException e) {
            throw new DAOException("Failed to delete movie id " + id, e);
        }
    }

    public int getRecordsSize() {
        String sql = SQLUtil.GET_ROW_COUNT;
        int rowCount = 0;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                rowCount = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new DAOException("msg", e);
        }
        return rowCount;
    }

    /**
     * @param connection       {@link Connection} to create {@link PreparedStatement}
     * @param sql              SQL Query to execute
     * @param getGeneratedKeys constructs statement to return generated keys if {@code true}
     * @param values           values to be set on the {@link PreparedStatement}
     * @return {@link PreparedStatement} constructed with given values and parameters
     */
    static PreparedStatement statementBuilder(Connection connection, String sql,
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

    /**
     * Extract all the field values associated with the given {@code movie} and
     * convert them to appropriate sql types.
     * @param movie {@link Movie} whose values are to be extracted
     * @return {@link Object} array containing all the extracted values
     */
    Object[] extractValues(Movie movie) {
        return new Object[] {
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
        movie.setMetaScore(rs.getString("metascore"));
        movie.setImdbRating(rs.getBigDecimal("imdb_rating"));
        movie.setImdbVotes(rs.getLong("imdb_votes"));
        movie.setImdbID(rs.getString("imdb_id"));
        movie.setType(Type.valueOf(rs.getString("type")));

        return movie;
    }
}
