package org.eldi.movietracker.movie;

import org.eldi.movietracker.util.SQLUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class H2MovieRepository implements MovieRepository {

    private Connection connection;

    public H2MovieRepository (Connection connection) {
        this.connection = connection;
    }

    // TODO insert_rating_query : fetch?
    public void save(Movie movie) {
        try (PreparedStatement ps = connection.prepareStatement(SQLUtil.INSERT_MOVIE_QUERY)) {
            ps.setString(1, movie.getTitle());
            ps.setString(2, movie.getYear());
            ps.setString(3, movie.getRated());
            ps.setDate(4, java.sql.Date.valueOf(movie.getReleased()));
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
            ps.setByte(15, movie.getMetaScore());
            ps.setBigDecimal(16, movie.getImdbRating());
            ps.setLong(17, movie.getImdbVotes());
            ps.setString(18, movie.getImdbID());
            ps.setString(19, movie.getType().name());
            ps.setBoolean(20, movie.getResponse());
            int affectedRows = ps.executeUpdate();
            System.out.println("Rows affected : " + affectedRows);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Optional<Movie> findByTitle(String title) {
        String query = SQLUtil.getFindByTitleQuery();
        Movie movie = new Movie();
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, title);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                movie.setId(rs.getInt("movie_id"));
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
                movie.setMetaScore(rs.getByte("metascore"));
                movie.setImdbRating(rs.getBigDecimal("imdb_rating"));
                movie.setImdbVotes(rs.getLong("imdb_votes"));
                movie.setImdbID(rs.getString("imdb_id"));
                movie.setType(Type.valueOf(rs.getString("type")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.of(movie);
    }

    public void update(String title) {

    }

    public List<Movie> findAll() {
        return null;
    }

    public void delete(String title) {

    }
}
