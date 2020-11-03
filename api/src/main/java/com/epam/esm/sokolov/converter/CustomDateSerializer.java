package com.epam.esm.sokolov.converter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class CustomDateSerializer extends JsonSerializer<ZonedDateTime> {

    private static final String DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

    @Override
    public void serialize(ZonedDateTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
        String format = dateFormatter.format(value);
        gen.writeString(format);
    }
}
