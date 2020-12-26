package org.eldi.movietracker.repository;

import org.eldi.movietracker.exception.DAOException;
import org.eldi.movietracker.model.Rating;
import org.eldi.movietracker.util.SQLUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class H2RatingsRepository implements RatingsRepository {

    private Connection connection;

    public H2RatingsRepository(Connection connection) {
        this.connection = connection;
    }

    public void save(List<Rating> ratings, int movieId) {

        ratings.forEach(rating -> {
            String sql = SQLUtil.INSERT_RATING_QUERY;

            Object[] values = {
                    rating.getSource(),
                    rating.getValue(),
                    movieId
            };

            try (PreparedStatement statement = H2MovieRepository.statementBuilder(connection, sql, true, values)) {

                // TODO batch update
                int affectedRows = statement.executeUpdate();
                if (affectedRows < 1) {
                    throw new DAOException("Failed to save '" + rating + "' for movie id " + movieId);
                }

                ResultSet keys = statement.getGeneratedKeys();
                if (keys.next()) {
                    rating.setId(keys.getInt(1));
                }
                System.out.println("Rows affected - " + affectedRows + ", for '" + rating + "'");
            } catch (SQLException e) {
                throw new DAOException("Failed to save '" + rating + "' ", e);
            }
        });
    }

    public List<Rating> find(int movieId) {
        String sql = SQLUtil.FIND_RATING_QUERY;
        List<Rating> ratings = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, movieId);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Rating rating = new Rating();

                rating.setId(resultSet.getInt("id"));
                rating.setSource((resultSet.getString("source")));
                rating.setValue((resultSet.getString("value")));

                ratings.add(rating);
            }
        } catch (SQLException e) {
            throw new DAOException("Failed to fetch ratings for movie id - " + movieId, e);
        }
        return ratings;
    }

    public void delete(int movieId) {
        String sql = SQLUtil.DELETE_RATING_QUERY;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, movieId);

            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new DAOException("Failed to delete ratings for movie id " + movieId);
            }

            System.out.println("Rows affected - " + affectedRows + ", deleted ratings for movie id - " + movieId);
        } catch (SQLException e) {
            throw new DAOException("Failed to delete ratings for movie id " + movieId, e);
        }
    }
}
