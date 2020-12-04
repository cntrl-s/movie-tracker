package org.eldi.movietracker.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class FormattedNumberDeserializer extends JsonDeserializer<Long> {
    public Long deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode jsonNode = jsonParser.getCodec().readTree(jsonParser);
        String imdbVotes = jsonNode.asText();
        imdbVotes = imdbVotes.replaceAll(",", "");
        return Long.valueOf(imdbVotes);
    }
}
