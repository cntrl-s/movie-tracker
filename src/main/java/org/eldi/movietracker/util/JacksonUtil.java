package org.eldi.movietracker.util;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

public class JacksonUtil {
    // TODO singleton & factory ?
    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS)
            .setPropertyNamingStrategy(PropertyNamingStrategy.UPPER_CAMEL_CASE);
}
