package org.eldi.movietracker.movie;

import org.eldi.movietracker.util.SQLUtil;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class H2MovieRepository implements MovieRepository {

    private Connection connection;

    public H2MovieRepository (Connection connection) {
        this.connection = connection;
    }

    public void save(Movie movie) {
        try (PreparedStatement ps = connection.prepareStatement(SQLUtil.INSERT_VALUES_QUERY)) {
            // id
            ps.setString(1, movie.getTitle());
            ps.setInt(2, movie.getYear());
            ps.setString(3, movie.getRated());
            ps.setDate(4, new java.sql.Date(movie.getReleased().getTime()));
            ps.setString(5, movie.getRuntime());
            ps.setString(6, movie.getGenre());
            ps.setString(7, movie.getDirector());
            ps.setString(8, movie.getWriter());
            ps.setString(9, movie.getActors());
            ps.setString(10, movie.getPlot());
            ps.setString(11, movie.getLanguage());
            ps.setString(12, movie.getCountry());
            ps.setString(13, movie.getAwards());
            ps.setString(14, movie.getPoster());
            Array ratings = connection.createArrayOf("Movie", movie.getRatings().toArray());
            ps.setArray(15, ratings);
            ps.setInt(16, movie.getMetaScore());
            ps.setDouble(17, movie.getImdbRating());
            ps.setLong(18, movie.getImdbVotes());
            ps.setString(19, movie.getImdbID());
            ps.setString(20, movie.getType().name());
            ps.setBoolean(21, movie.getResponse());
            int affectedRows = ps.executeUpdate();
            System.out.println("Rows affected : " + affectedRows);
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
