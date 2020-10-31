package org.eldi.movietracker;

import org.eldi.movietracker.movie.Movie;
import org.eldi.movietracker.util.JacksonUtil;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class MovieTest {
    private Movie movie;

    @Before
    public void init() throws IOException {
        File json = new File("src/main/resources/gotg2.json");
        movie = JacksonUtil.OBJECT_MAPPER.readValue(json, Movie.class);
    }

    @Test
    public void jsonDeserializationTest() throws IOException {
        System.out.println(movie);
    }

}
