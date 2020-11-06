package org.eldi.movietracker.util;

public class SQLUtil {

    private static final String TABLE_NAME = "movies";

    public static final String CREATE_SQL_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME
//            + " (id VARCHAR(15),"
            + " (title VARCHAR(100),"
            + " year VARCHAR(10),"
            + " rated VARCHAR(10),"
            + " released VARCHAR(250),"
            + " runtime VARCHAR(20),"
            + " genre VARCHAR(200),"
            + " director VARCHAR(200),"
            + " writer VARCHAR(500),"
            + " actors VARCHAR(200),"
            + " plot VARCHAR(500),"
            + " language VARCHAR(50),"
            + " country VARCHAR(100),"
            + " awards VARCHAR(200),"
            + " poster VARCHAR(max),"
            + " ratings ARRAY,"
            + " metaScore VARCHAR(10),"
            + " imdbRating VARCHAR(5),"
            + " imdbVotes VARCHAR(16),"
            + " imdbID VARCHAR(12),"
            + " type VARCHAR(32),"
            + " response VARCHAR(20));";

    public static final String FIND_ALL_QUERY = "SELECT * FROM MOVIES;";

    public static final String INSERT_VALUES_QUERY = "INSERT INTO MOVIES" +
            " (" +
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
            "ratings," +
            "metaScore," +
            "imdbRating," +
            "imdbVotes," +
            "imdbID," +
            "type," +
            "response" +
            ") VALUES" +
            " (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
}
