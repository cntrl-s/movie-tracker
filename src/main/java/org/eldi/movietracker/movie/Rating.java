package org.eldi.movietracker.movie;

import java.io.Serializable;
import java.util.StringJoiner;

public class Rating implements Serializable {
    private String source;

    private String value;

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
                .add("source='" + source + "'")
                .add("value='" + value + "'")
                .toString();
    }
}
