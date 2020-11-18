package org.eldi.movietracker.movie;

import java.util.StringJoiner;

public class Rating {
    private int id;

    private String source;

    private String value;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String toString() {
        return new StringJoiner(", ", Rating.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("source='" + source + "'")
                .add("value='" + value + "'")
                .toString();
    }
}
