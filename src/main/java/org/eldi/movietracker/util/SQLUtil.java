package org.eldi.movietracker.util;

public class SQLUtil {

    private static final String MOVIE_TABLE_NAME = "movies";
    private static final String RATING_TABLE_NAME = "ratings";

    public static final String CREATE_MOVIE_TABLE = "CREATE TABLE IF NOT EXISTS " + MOVIE_TABLE_NAME + " ("
            + " id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,"
            + " title VARCHAR(512),"
            + " year VARCHAR(32),"
            + " rated VARCHAR(10),"
            + " released DATE,"
            + " runtime VARCHAR(20),"
            + " genre VARCHAR(200),"
            + " director VARCHAR(200),"
            + " writer VARCHAR(512),"
            + " actors VARCHAR(200),"
            + " plot VARCHAR(512),"
            + " language VARCHAR(50),"
            + " country VARCHAR(100),"
            + " awards VARCHAR(200),"
            + " poster VARCHAR(256),"
            // TODO handle n/a
            + " metascore TINYINT,"
            + " imdb_rating DECIMAL,"
            + " imdb_votes BIGINT,"
            + " imdb_id VARCHAR(12),"
            + " type VARCHAR(32),"
            + " response VARCHAR(20)"
            + ");";

    public static final String INSERT_MOVIE_QUERY = "INSERT INTO " + MOVIE_TABLE_NAME + " (" +
            "title," +
            "year," +
            "rated," +
            "released," +
            "runtime," +
            "genre," +
            "director," +
            "writer," +
            "actors," +
            "plot," +
            "language," +
            "country," +
            "awards," +
            "poster," +
            "metascore," +
            "imdb_rating," +
            "imdb_votes," +
            "imdb_id," +
            "type," +
            "response" +
            ") " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

    public static final String FIND_MOVIES_QUERY = String.format("SELECT * FROM %s;", MOVIE_TABLE_NAME);

    public static String getFindByTitleQuery() {
        return String.format("SELECT * FROM %s WHERE title = ?", MOVIE_TABLE_NAME);
    }

    public static final String CREATE_RATING_TABLE = "CREATE TABLE IF NOT EXISTS " + RATING_TABLE_NAME + " ("
            + " id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,"
            + " source VARCHAR(32),"
            + " value VARCHAR(32),"
            + " movie_id INT NOT NULL,"
            + " FOREIGN KEY (movie_id) REFERENCES " + MOVIE_TABLE_NAME + "(id)"
            + ");";

    public static final String INSERT_RATING_QUERY = "INSERT INTO " + RATING_TABLE_NAME + " ("
            + " source,"
            + " value,"
            + " movie_id"
            + ")"
            + "VALUES (?, ?, ?);";
}
