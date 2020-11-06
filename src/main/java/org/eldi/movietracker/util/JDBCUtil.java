package org.eldi.movietracker.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCUtil {

    public enum Connector {
        INSTANCE;

        private String url = "jdbc:h2:./.movietracker";
        private String testUrl = "jdbc:h2:mem:";
        private String user = "sa";
        private String password = "";

        private Connection connection;

        public Connection getConnection() {
            if (connection == null) {
                try {
                    connection = DriverManager.getConnection(testUrl, user, password);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return connection;
        }
    }

    public static void createTableIfNotExists() {
        try {
            Connection connection = Connector.INSTANCE.getConnection();
            Statement statement = connection.createStatement();
            statement.execute(SQLUtil.CREATE_SQL_TABLE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
