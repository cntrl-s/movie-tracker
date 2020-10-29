package org.eldi.movietracker.movie;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.net.URL;

public class Movie {
    private String title;
    private String year;
    private String rated;
    private String released;
    private int runtime;
    private String genre;
    private String director;
    private String writer;
    private String actors;
    private String plot;
    private String language;
    private String country;
    private String awards;
    private URL posterUrl;
    // ratings
    private int metaScore;
    private double imdbRating;
    private long imdbVotes;
    private String imdbID;
    private Type type;
}
