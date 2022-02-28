package com.epam.esm.sokolov.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class CustomDateDeserializer extends JsonDeserializer<ZonedDateTime> {

    private static final String DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSz";

    @Override
    public ZonedDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
        String date = p.getValueAsString();
        return ZonedDateTime.from(dateFormatter.parse(date));
    }
}
