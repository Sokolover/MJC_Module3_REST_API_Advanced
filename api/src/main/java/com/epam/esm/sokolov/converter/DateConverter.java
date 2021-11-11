package com.epam.esm.sokolov.converter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public final class DateConverter {

    private DateConverter() {
    }

    public static LocalDateTime getLocalDate(ZonedDateTime createDate) {
        ZonedDateTime zonedDateTime = createDate.withZoneSameInstant(ZoneOffset.UTC);
        return zonedDateTime.toLocalDateTime();
    }

    public static ZonedDateTime getZonedDateTime(LocalDateTime object, String zone) {
        ZoneId zoneId = ZoneId.of(zone);
        return ZonedDateTime.of(object, ZoneOffset.UTC).withZoneSameInstant(zoneId);
    }
}
