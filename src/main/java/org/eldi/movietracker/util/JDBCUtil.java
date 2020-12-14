package org.eldi.movietracker.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public enum JDBCUtil {
    INSTANCE;

    private static Connection connection;

    private static String url = "jdbc:h2:./.movietracker";
    private static String testUrl = "jdbc:h2:mem:";
    private static String user = "sa";
    private static String password = "";

    public Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(url, user, password);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    public Connection getTestConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(testUrl, user, password);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    public void createTableIfNotExists() {
        try {
            Connection connection = getConnection();
            Statement createMoviesTableStatement = connection.createStatement();
            Statement createRatingsTableStatement = connection.createStatement();

            createMoviesTableStatement.execute(SQLUtil.CREATE_MOVIE_TABLE);
            createRatingsTableStatement.execute(SQLUtil.CREATE_RATING_TABLE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
