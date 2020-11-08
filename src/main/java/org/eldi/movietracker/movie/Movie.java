package org.eldi.movietracker.movie;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.eldi.movietracker.util.FormattedNumberDeserializer;

import java.util.Date;
import java.util.List;
import java.util.StringJoiner;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Movie {
    private String title;

    private int year;

    private String rated;

    @JsonFormat(pattern = "dd MMM yyyy")
    private Date released;

    private String runtime;

    private String genre;

    private String director;

    private String writer;

    private String actors;

    private String plot;

    private String language;

    private String country;

    private String awards;

    private String poster;

    private List<Rating> ratings;

    @JsonProperty(value = "Metascore")
    private int metaScore;

    @JsonProperty(value = "imdbRating")
    private double imdbRating;

    @JsonDeserialize(using = FormattedNumberDeserializer.class)
    @JsonProperty(value = "imdbVotes")
    private long imdbVotes;

    @JsonProperty(value = "imdbID")
    private String imdbID;

    private Type type;

    private boolean response;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getRated() {
        return rated;
    }

    public void setRated(String rated) {
        this.rated = rated;
    }

    public Date getReleased() {
        return released;
    }

    public void setReleased(Date released) {
        this.released = released;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAwards() {
        return awards;
    }

    public void setAwards(String awards) {
        this.awards = awards;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }

    public int getMetaScore() {
        return metaScore;
    }

    public void setMetaScore(int metaScore) {
        this.metaScore = metaScore;
    }

    public double getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(double imdbRating) {
        this.imdbRating = imdbRating;
    }

    public long getImdbVotes() {
        return imdbVotes;
    }

    public void setImdbVotes(long imdbVotes) {
        this.imdbVotes = imdbVotes;
    }

    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public boolean getResponse() {
        return response;
    }

    public void setResponse(boolean response) {
        this.response = response;
    }

    public String toString() {
        return new StringJoiner(", ", Movie.class.getSimpleName() + "[", "]")
                .add("title='" + title + "'")
                .add("year='" + year + "'")
                .add("rated='" + rated + "'")
                .add("released='" + released + "'")
                .add("runtime=" + runtime)
                .add("genre='" + genre + "'")
                .add("director='" + director + "'")
                .add("writer='" + writer + "'")
                .add("actors='" + actors + "'")
                .add("plot='" + plot + "'")
                .add("language='" + language + "'")
                .add("country='" + country + "'")
                .add("awards='" + awards + "'")
                .add("posterUrl=" + poster)
                .add("ratings=" + ratings)
                .add("metaScore=" + metaScore)
                .add("imdbRating=" + imdbRating)
                .add("imdbVotes='" + imdbVotes + "'")
                .add("imdbID='" + imdbID + "'")
                .add("type=" + type)
                .toString();
    }
}
