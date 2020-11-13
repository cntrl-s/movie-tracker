package org.eldi.movietracker.movie;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.StringJoiner;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchResult {
    private String title;

    private String year;

    @JsonProperty("imdbID")
    private String imdbID;

    private Type type;

    @JsonProperty("Poster")
    private String posterUrl;

    public SearchResult() {
    }

    public SearchResult(String title, String year, String imdbID, Type type, String posterUrl) {
        this.title = title;
        this.year = year;
        this.imdbID = imdbID;
        this.type = type;
        this.posterUrl = posterUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
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

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String toString() {
        return new StringJoiner(", ", SearchResult.class.getSimpleName() + "[", "]")
                .add("title='" + title + "'")
                .add("year='" + year + "'")
                .add("imdbID='" + imdbID + "'")
                .add("type=" + type)
                .add("posterUrl='" + posterUrl + "'")
                .toString();
    }
}
